package seedu.address.logic;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.quiz.QuizCard;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns either management or quiz mode dependent on isQuizDone
     */
    LogicManager.Mode getMode();

    /**
     * @return a list of lessons for UI display purposes
     */
    List<Lesson> getLessons();

    String getCurrentLessonName();
    Lesson getOpenedLesson();

    /**
     * Returns the current quiz card for UI MainPanel display
     */
    QuizCard getCurrentQuizCard();

    /**
     * Returns the total correct out of total attempts for UI MainPanel display
     */
    String getTotalCorrectAndTotalAttempts();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' theme.
     */
    String getTheme();

    /**
     * Returns the list of QuizCard from Quiz.
     */
    List<QuizCard> getQuizCardList();
}
