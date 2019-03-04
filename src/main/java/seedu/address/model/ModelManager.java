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
import seedu.address.model.deck.Card;
import seedu.address.model.deck.exceptions.CardNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTopDeck versionedTopDeck;
    private final UserPrefs userPrefs;
    private final FilteredList<Card> filteredPersons;
    private final SimpleObjectProperty<Card> selectedPerson = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyTopDeck topDeck, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(topDeck, userPrefs);

        logger.fine("Initializing with address book: " + topDeck + " and user prefs " + userPrefs);

        versionedTopDeck = new VersionedTopDeck(topDeck);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<Card>(versionedTopDeck.getCardList());
        filteredPersons.addListener(this::ensureSelectedPersonIsValid);
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
        updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
    }

    @Override
    public void setCard(Card target, Card editedCard) {
        requireAllNonNull(target, editedCard);

        versionedTopDeck.setCard(target, editedCard);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedTopDeck}
     */
    @Override
    public ObservableList<Card> getFilteredCardList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredCardList(Predicate<Card> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
    public ReadOnlyProperty<Card> selectedCardProperty() {
        return selectedPerson;
    }

    @Override
    public Card getSelectedCard() {
        return selectedPerson.getValue();
    }

    @Override
    public void setSelectedCard(Card card) {
        if (card != null && !filteredPersons.contains(card)) {
            throw new CardNotFoundException();
        }
        selectedPerson.setValue(card);
    }

    /**
     * Ensures {@code selectedPerson} is a valid card in {@code filteredPersons}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Card> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected card, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPersonReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPerson.getValue());
            if (wasSelectedPersonReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedPerson.getValue());
                selectedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSameCard(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the card that came before it in the list,
                // or clear the selection if there is no such card.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
                && filteredPersons.equals(other.filteredPersons)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

}
