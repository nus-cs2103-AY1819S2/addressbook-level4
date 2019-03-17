package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.ListItem;
import seedu.address.logic.ViewState;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.exceptions.CardNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTopDeck versionedTopDeck;
    private final UserPrefs userPrefs;
    private final FilteredList<Card> filteredCards;
    private final SimpleObjectProperty<Card> selectedCard = new SimpleObjectProperty<>();
    private ViewState viewState = ViewState.CARDS;
    private Deck currentDeck = null; // Deck that is active, null if we are in Decks View

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyTopDeck topDeck, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(topDeck, userPrefs);

        logger.fine("Initializing with address book: " + topDeck + " and user prefs " + userPrefs);

        versionedTopDeck = new VersionedTopDeck(topDeck);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCards = new FilteredList<>(versionedTopDeck.getCardList());
        filteredCards.addListener(this::ensureSelectedItemIsValid);
    }

    public ModelManager() {
        this(new TopDeck(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTopDeckFilePath() {
        return userPrefs.getTopDeckFilePath();
    }

    @Override
    public void setTopDeckFilePath(Path topDeckFilePath) {
        requireNonNull(topDeckFilePath);
        userPrefs.setTopDeckFilePath(topDeckFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setTopDeck(ReadOnlyTopDeck topDeck) {
        versionedTopDeck.resetData(topDeck);
    }

    @Override
    public ReadOnlyTopDeck getTopDeck() {
        return versionedTopDeck;
    }

    @Override
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return versionedTopDeck.hasCard(card);
    }

    @Override
    public void deleteCard(Card target) {
        versionedTopDeck.removeCard(target);
    }

    @Override
    public void addCard(Card card) {
        versionedTopDeck.addCard(card);
        updateFilteredList(PREDICATE_SHOW_ALL_CARDS);
    }

    @Override
    public void setCard(Card target, Card editedCard) {
        requireAllNonNull(target, editedCard);

        versionedTopDeck.setCard(target, editedCard);
    }

    @Override
    public void addDeck(Deck deck) {
        logger.info("Added a new deck to TopDeck.");
        // versionedTopDeck.addDeck(deck);
        // updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
        commitTopDeck();
    }

    @Override
    public boolean hasDeck(Deck deck) {
        requireAllNonNull(deck);
        return versionedTopDeck.hasDeck(deck);
    }

    //=========== Filtered Card List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedTopDeck}
     */
    @Override
    public ObservableList<ListItem> getFilteredList() {
        return (ObservableList<ListItem>) (ObservableList<? extends ListItem>) filteredCards;
    }

    @Override
    public void updateFilteredList(Predicate<? extends ListItem> predicate) {
        requireNonNull(predicate);
        filteredCards.setPredicate((Predicate<Card>) predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoTopDeck() {
        return versionedTopDeck.canUndo();
    }

    @Override
    public boolean canRedoTopDeck() {
        return versionedTopDeck.canRedo();
    }

    @Override
    public void undoTopDeck() {
        versionedTopDeck.undo();
    }

    @Override
    public void redoTopDeck() {
        versionedTopDeck.redo();
    }

    @Override
    public void commitTopDeck() {
        versionedTopDeck.commit();
    }

    //=========== Selected card ===========================================================================

    @Override
    public ReadOnlyProperty<ListItem> selectedItemProperty() {
        return (ReadOnlyProperty<ListItem>) (ReadOnlyProperty<? extends ListItem>) selectedCard;
    }

    @Override
    public ListItem getSelectedItem() {
        return selectedCard.getValue();
    }

    @Override
    public void setSelectedItem(ListItem card) {
        if (card != null && !filteredCards.contains(card)) {
            throw new CardNotFoundException();
        }
        selectedCard.setValue((Card)card);
    }

    /**
     * Ensures {@code selectedCard} is a valid card in {@code filteredCards}.
     */
    private void ensureSelectedItemIsValid(ListChangeListener.Change<? extends Card> change) {
        while (change.next()) {
            if (selectedCard.getValue() == null) {
                // null is always a valid selected card, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedItemReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedCard.getValue());
            if (wasSelectedItemReplaced) {
                // Update selectedCard to its new value.
                int index = change.getRemoved().indexOf(selectedCard.getValue());
                selectedCard.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedItemRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedCard.getValue().equals(removedPerson));
            if (wasSelectedItemRemoved) {
                // Select the card that came before it in the list,
                // or clear the selection if there is no such card.
                selectedCard.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedTopDeck.equals(other.versionedTopDeck)
                && userPrefs.equals(other.userPrefs)
                && filteredCards.equals(other.filteredCards)
                && Objects.equals(selectedCard.get(), other.selectedCard.get());
    }

}
