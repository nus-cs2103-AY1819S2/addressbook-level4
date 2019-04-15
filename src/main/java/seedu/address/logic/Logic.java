package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyCardCollection;
import seedu.address.model.flashcard.Flashcard;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the CardCollection.
     *
     * @see seedu.address.model.Model#getCardCollection()
     */
    ReadOnlyCardCollection getCardCollection();

    /**
     * Returns an unmodifiable view of the filtered list of flashcards
     */
    ObservableList<Flashcard> getFilteredFlashcardList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' card collection file path.
     */
    Path getCardCollectionFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected flashcard in the filtered flashcard list.
     * null if no flashcard is selected.
     *
     * @see seedu.address.model.Model#selectedFlashcardProperty()
     */
    ReadOnlyProperty<Flashcard> selectedFlashcardProperty();

    /**
     * Current quiz mode. -1 for front face, 1 for back face, 0 for non-quiz mode.
     *
     * @see seedu.address.model.Model#quizModeProperty()
     */
    ReadOnlyProperty<Integer> quizModeProperty();

    /**
     * Sets the selected flashcard in the filtered flashcard list.
     *
     * @see seedu.address.model.Model#setSelectedFlashcard(Flashcard)
     */
    void setSelectedFlashcard(Flashcard flashcard);

    /**
     * Sets the quiz mode
     *
     * @see seedu.address.model.Model#setQuizMode(Integer)
     */
    void setQuizMode(Integer quizMode);

    /**
     * Sets the Spaced Repetition System Model. mode
     *
     * @see seedu.address.model.Model#setIsQuizSrs(Boolean)
     */
    void setIsQuizSrs(Boolean isQuizSrs);

    ObservableList<Flashcard> getQuizFlashcards();

    /**
     * @return the number of good feedback in the current quiz
     * @see seedu.address.model.Model#getQuizGood()
     */
    ReadOnlyProperty<Integer> quizGoodProperty();

    /**
     * @return the number of bad feedback in the current quiz
     * @see seedu.address.model.Model#getQuizBad()
     */
    ReadOnlyProperty<Integer> quizBadProperty();

    /**
     * @return true if SRS mode activated, else review mode.
     * @see seedu.address.model.Model#getIsQuizSrs()
     */
    ReadOnlyProperty<Boolean> isQuizSrsProperty();
}
