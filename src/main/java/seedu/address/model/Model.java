package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.flashcard.Flashcard;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Flashcard> PREDICATE_SHOW_ALL_FLASHCARDS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' card collection file path.
     */
    Path getCardCollectionFilePath();

    /**
     * Sets the user prefs' card collection file path.
     */
    void setCardCollectionFilePath(Path cardCollectionFilePath);

    /**
     * Replaces card collection data with the data in {@code cardCollection}.
     */
    void setCardCollection(ReadOnlyCardCollection cardCollection);

    /**
     * Returns the CardCollection
     */
    ReadOnlyCardCollection getCardCollection();

    /**
     * Returns true if a flashcard with the same identity as {@code flashcard} exists in the card collection.
     */
    boolean hasFlashcard(Flashcard flashcard);

    /**
     * Deletes the given flashcard.
     * The flashcard must exist in the card collection.
     */
    void deleteFlashcard(Flashcard target);

    /**
     * Adds the given flashcard.
     * {@code flashcard} must not already exist in the card collection.
     */
    void addFlashcard(Flashcard flashcard);

    /**
     * Replaces the given flashcard {@code target} with {@code editedFlashcard}.
     * {@code target} must exist in the card collection.
     * The flashcard identity of {@code editedFlashcard} must not be the same as another existing flashcard in the card
     * collection.
     */
    void setFlashcard(Flashcard target, Flashcard editedFlashcard);

    /**
     * Returns an unmodifiable view of the filtered flashcard list
     */
    ObservableList<Flashcard> getFilteredFlashcardList();

    /**
     * Updates the filter of the filtered flashcard list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFlashcardList(Predicate<Flashcard> predicate);

    /**
     * Returns true if the model has previous card collection states to restore.
     */
    boolean canUndoCardCollection();

    /**
     * Returns true if the model has undone card collection states to restore.
     */
    boolean canRedoCardCollection();

    /**
     * Restores the model's card collection to its previous state.
     * @return  the command that makes change before and after undo.
     */
    String undoCardCollection();

    /**
     * Restores the model's card collection to its previously undone state.
     * @return  the command that makes change before and after redo.
     */
    String redoCardCollection();

    /**
     * Saves the current card collection state for undo/redo.
     * @param command the command that makes the change.
     */
    void commitCardCollection(String command);

    /**
     * Saves the current card collection state for undo/redo.
     */
    void commitCardCollection();

    /**
     * Selected flashcard in the filtered flashcard list.
     * null if no flashcard is selected.
     */
    ReadOnlyProperty<Flashcard> selectedFlashcardProperty();

    /**
     * Current quiz mode. -1 for front face, 1 for back face, 0 for non-quiz mode.
     */
    ReadOnlyProperty<Integer> quizModeProperty();

    /**
     * Returns the selected flashcard in the filtered flashcard list.
     * null if no flashcard is selected.
     */
    Flashcard getSelectedFlashcard();

    /**
     * Sets the selected flashcard in the filtered flashcard list.
     */
    void setSelectedFlashcard(Flashcard flashcard);

    /**
     * Returns the quiz mode.
     */
    Integer getQuizMode();

    /**
     * Sets the quiz mode.
     */
    void setQuizMode(Integer quizMode);

    /**
     * Sets the SRS mode.
     */
    void setIsQuizSrs(Boolean isQuizSrs);

    /**
     * Gets the quiz mode flashcards.
     */
    ObservableList<Flashcard> getQuizFlashcards();

    /**
     * Sets the quiz mode flashcards.
     *
     * @param flashcards the flashcards that is going to be quizzed
     */
    void setQuizFlashcards(ObservableList<Flashcard> flashcards);

    /**
     * Shows a flashcard for quiz mode from quiz flashcards.
     */
    void showNextQuizCard();

    /**
     * @return the number of good feedback in the current quiz mode
     */
    ReadOnlyProperty<Integer> getQuizGood();

    /**
     * @return the number of bad feedback in the current quiz mode
     */
    ReadOnlyProperty<Integer> getQuizBad();

    /**
     * @return is the current quiz session uses SRS mode.
     */
    ReadOnlyProperty<Boolean> getIsQuizSrs();

    /**
     * Resets the quiz statistics.
     */
    void resetQuizStat();

    /**
     * Adds good feedback.
     */
    void addGoodFeedback();

    /**
     * Adds bad feedback.
     */
    void addBadFeedback();
}
