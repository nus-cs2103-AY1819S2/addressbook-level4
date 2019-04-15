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
import seedu.address.commons.core.QuizState;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.exceptions.FlashcardNotFoundException;

/**
 * Represents the in-memory model of the card collection data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedCardCollection versionedCardCollection;
    private final UserPrefs userPrefs;
    private final FilteredList<Flashcard> filteredFlashcards;
    private final SimpleObjectProperty<Flashcard> selectedFlashcard = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Integer> quizMode = new SimpleObjectProperty<>(0);
    private final SimpleObjectProperty<Integer> quizGood = new SimpleObjectProperty<>(0);
    private final SimpleObjectProperty<Integer> quizBad = new SimpleObjectProperty<>(0);
    private final SimpleObjectProperty<Boolean> isQuizSrs = new SimpleObjectProperty<>(false);
    private ObservableList<Flashcard> quizFlashcards;

    /**
     * Initializes a ModelManager with the given cardCollection and userPrefs.
     */
    public ModelManager(ReadOnlyCardCollection cardCollection, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(cardCollection, userPrefs);

        logger.fine("Initializing with card collection: " + cardCollection + " and user prefs " + userPrefs);

        versionedCardCollection = new VersionedCardCollection(cardCollection);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashcards = new FilteredList<>(versionedCardCollection.getFlashcardList());
        filteredFlashcards.addListener(this::ensureSelectedFlashcardIsValid);
    }

    public ModelManager() {
        this(new CardCollection(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
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
    public Path getCardCollectionFilePath() {
        return userPrefs.getCardCollectionFilePath();
    }

    @Override
    public void setCardCollectionFilePath(Path cardCollectionFilePath) {
        requireNonNull(cardCollectionFilePath);
        userPrefs.setCardCollectionFilePath(cardCollectionFilePath);
    }

    //=========== CardCollection ================================================================================

    @Override
    public ReadOnlyCardCollection getCardCollection() {
        return versionedCardCollection;
    }

    @Override
    public void setCardCollection(ReadOnlyCardCollection cardCollection) {
        versionedCardCollection.resetData(cardCollection);
    }

    @Override
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return versionedCardCollection.hasFlashcard(flashcard);
    }

    @Override
    public void deleteFlashcard(Flashcard target) {
        versionedCardCollection.removeFlashcard(target);
    }

    @Override
    public void addFlashcard(Flashcard flashcard) {
        versionedCardCollection.addFlashcard(flashcard);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        versionedCardCollection.setFlashcard(target, editedFlashcard);
    }

    //=========== Filtered Flashcard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} backed by the internal list of
     * {@code versionedCardCollection}
     */
    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return filteredFlashcards;
    }

    @Override
    public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
        requireNonNull(predicate);
        filteredFlashcards.setPredicate(predicate);
    }

    //=========== Undo/Redo ====================================================================================

    @Override
    public boolean canUndoCardCollection() {
        return versionedCardCollection.canUndo();
    }

    @Override
    public boolean canRedoCardCollection() {
        return versionedCardCollection.canRedo();
    }

    @Override
    public String undoCardCollection() {
        return versionedCardCollection.undo();
    }

    @Override
    public String redoCardCollection() {
        return versionedCardCollection.redo();
    }

    @Override
    public void commitCardCollection(String commandText) {
        versionedCardCollection.commit(commandText);
    }

    @Override
    public void commitCardCollection() {
        versionedCardCollection.commit("");
    }

    //=========== Selected flashcard ============================================================================

    @Override
    public ReadOnlyProperty<Flashcard> selectedFlashcardProperty() {
        return selectedFlashcard;
    }

    @Override
    public Flashcard getSelectedFlashcard() {
        return selectedFlashcard.getValue();
    }

    @Override
    public void setSelectedFlashcard(Flashcard flashcard) {
        if (flashcard != null && !filteredFlashcards.contains(flashcard)) {
            throw new FlashcardNotFoundException();
        }
        selectedFlashcard.setValue(flashcard);
    }

    /**
     * Ensures {@code selectedFlashcard} is a valid flashcard in {@code filteredFlashcards}.
     */
    private void ensureSelectedFlashcardIsValid(ListChangeListener.Change<? extends Flashcard> change) {
        while (change.next()) {
            if (selectedFlashcard.getValue() == null) {
                // null is always a valid selected flashcard, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedFlashcardReplaced =
                change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedFlashcard.getValue());
            if (wasSelectedFlashcardReplaced) {
                // Update selectedFlashcard to its new value.
                int index = change.getRemoved().indexOf(selectedFlashcard.getValue());
                selectedFlashcard.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedFlashcardRemoved = change.getRemoved().stream()
                .anyMatch(removedFlashcard -> selectedFlashcard.getValue().isSameFlashcard(removedFlashcard))
                && !change.getAddedSubList().contains(selectedFlashcard.getValue());
            if (wasSelectedFlashcardRemoved) {
                // Select the flashcard that came before it in the list,
                // or clear the selection if there is no such flashcard.
                selectedFlashcard.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    //=========== Quiz Mode =====================================================================================

    @Override
    public ObservableList<Flashcard> getQuizFlashcards() {
        return quizFlashcards;
    }

    @Override
    public void setQuizFlashcards(ObservableList<Flashcard> flashcards) {
        quizFlashcards = flashcards;
    }

    @Override
    public ReadOnlyProperty<Integer> quizModeProperty() {
        return quizMode;
    }

    @Override
    public Integer getQuizMode() {
        return quizMode.getValue();
    }

    @Override
    public void setQuizMode(Integer quizMode) {
        this.quizMode.setValue(quizMode);
    }

    @Override
    public void showNextQuizCard() {
        quizMode.setValue(QuizState.QUIZ_MODE_FRONT);
        Flashcard flashcard = quizFlashcards.get(0);
        setSelectedFlashcard(flashcard);
        quizFlashcards.remove(0);
    }

    @Override
    public ReadOnlyProperty<Integer> getQuizGood() {
        return quizGood;
    }

    @Override
    public ReadOnlyProperty<Integer> getQuizBad() {
        return quizBad;
    }

    @Override
    public ReadOnlyProperty<Boolean> getIsQuizSrs() {
        return isQuizSrs;
    }

    @Override
    public void setIsQuizSrs(Boolean isQuizSrs) {
        this.isQuizSrs.setValue(isQuizSrs);
    }

    @Override
    public void resetQuizStat() {
        quizGood.setValue(0);
        quizBad.setValue(0);
    }

    @Override
    public void addGoodFeedback() {
        Flashcard updatedFlashcard = selectedFlashcard.getValue().quizAttempt(true, isQuizSrs.getValue());
        setFlashcard(selectedFlashcard.getValue(), updatedFlashcard);
        quizGood.setValue(quizGood.getValue() + 1);
    }

    @Override
    public void addBadFeedback() {
        Flashcard updatedFlashcard = selectedFlashcard.getValue().quizAttempt(false, isQuizSrs.getValue());
        setFlashcard(selectedFlashcard.getValue(), updatedFlashcard);
        quizBad.setValue(quizBad.getValue() + 1);
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
        return versionedCardCollection.equals(other.versionedCardCollection)
            && userPrefs.equals(other.userPrefs)
            && filteredFlashcards.equals(other.filteredFlashcards)
            && Objects.equals(selectedFlashcard.get(), other.selectedFlashcard.get());
    }

}
