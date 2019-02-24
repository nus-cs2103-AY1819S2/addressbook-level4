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
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.CardNotFoundException;

/**
 * Represents the in-memory model of the card folder data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedCardFolder versionedCardFolder;
    private final UserPrefs userPrefs;
    private final FilteredList<Card> filteredCards;
    private final SimpleObjectProperty<Card> selectedCard = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given cardFolder and userPrefs.
     */
    public ModelManager(ReadOnlyCardFolder cardFolder, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(cardFolder, userPrefs);

        logger.fine("Initializing with card folder: " + cardFolder + " and user prefs " + userPrefs);

        versionedCardFolder = new VersionedCardFolder(cardFolder);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCards = new FilteredList<>(versionedCardFolder.getCardList());
        filteredCards.addListener(this::ensureSelectedCardIsValid);
    }

    public ModelManager() {
        this(new CardFolder(), new UserPrefs());
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
    public Path getCardFolderFilePath() {
        return userPrefs.getCardFolderFilePath();
    }

    @Override
    public void setCardFolderFilePath(Path cardFolderFilePath) {
        requireNonNull(cardFolderFilePath);
        userPrefs.setCardFolderFilePath(cardFolderFilePath);
    }

    //=========== CardFolder ================================================================================

    @Override
    public void setCardFolder(ReadOnlyCardFolder cardFolder) {
        versionedCardFolder.resetData(cardFolder);
    }

    @Override
    public ReadOnlyCardFolder getCardFolder() {
        return versionedCardFolder;
    }

    @Override
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return versionedCardFolder.hasCard(card);
    }

    @Override
    public void deleteCard(Card target) {
        versionedCardFolder.removeCard(target);
    }

    @Override
    public void addCard(Card card) {
        versionedCardFolder.addCard(card);
        updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
    }

    @Override
    public void setCard(Card target, Card editedCard) {
        requireAllNonNull(target, editedCard);

        versionedCardFolder.setCard(target, editedCard);
    }

    //=========== Filtered Card List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Card} backed by the internal list of
     * {@code versionedCardFolder}
     */
    @Override
    public ObservableList<Card> getFilteredCardList() {
        return filteredCards;
    }

    @Override
    public void updateFilteredCardList(Predicate<Card> predicate) {
        requireNonNull(predicate);
        filteredCards.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoCardFolder() {
        return versionedCardFolder.canUndo();
    }

    @Override
    public boolean canRedoCardFolder() {
        return versionedCardFolder.canRedo();
    }

    @Override
    public void undoCardFolder() {
        versionedCardFolder.undo();
    }

    @Override
    public void redoCardFolder() {
        versionedCardFolder.redo();
    }

    @Override
    public void commitCardFolder() {
        versionedCardFolder.commit();
    }

    //=========== Selected card ===========================================================================

    @Override
    public ReadOnlyProperty<Card> selectedCardProperty() {
        return selectedCard;
    }

    @Override
    public Card getSelectedCard() {
        return selectedCard.getValue();
    }

    @Override
    public void setSelectedCard(Card card) {
        if (card != null && !filteredCards.contains(card)) {
            throw new CardNotFoundException();
        }
        selectedCard.setValue(card);
    }

    /**
     * Ensures {@code selectedCard} is a valid card in {@code filteredCards}.
     */
    private void ensureSelectedCardIsValid(ListChangeListener.Change<? extends Card> change) {
        while (change.next()) {
            if (selectedCard.getValue() == null) {
                // null is always a valid selected card, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedCardReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedCard.getValue());
            if (wasSelectedCardReplaced) {
                // Update selectedCard to its new value.
                int index = change.getRemoved().indexOf(selectedCard.getValue());
                selectedCard.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedCardRemoved = change.getRemoved().stream()
                    .anyMatch(removedCard -> selectedCard.getValue().isSameCard(removedCard));
            if (wasSelectedCardRemoved) {
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
        return versionedCardFolder.equals(other.versionedCardFolder)
                && userPrefs.equals(other.userPrefs)
                && filteredCards.equals(other.filteredCards)
                && Objects.equals(selectedCard.get(), other.selectedCard.get());
    }

}
