package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalFlashcards.getTypicalCardCollection;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.Flashcard;

/**
 * Contains integration tests and unit tests for QuizCommand.
 */
public class QuizCommandTest {

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardCollection(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @Test
    public void execute_quiz_success() throws CommandException {
        QuizCommand command = new QuizCommand();
        command.execute(model, commandHistory);
        assertEquals((int) model.getQuizMode(), QuizState.QUIZ_MODE_FRONT);
        assertEquals(false, model.getIsQuizSrs().getValue());
        assertEquals(model.getQuizFlashcards().size(), model.getFilteredFlashcardList().size() - 1);
    }

    @Test
    public void execute_quizReview_success() throws CommandException {
        QuizCommand command = new QuizCommand(false);
        command.execute(model, commandHistory);
        assertEquals((int) model.getQuizMode(), QuizState.QUIZ_MODE_FRONT);
        assertEquals(false, model.getIsQuizSrs().getValue());
        assertEquals(model.getQuizFlashcards().size(), model.getFilteredFlashcardList().size() - 1);
    }

    @Test
    public void execute_quizSrs_success() throws CommandException {
        QuizCommand command = new QuizCommand(true);
        command.execute(model, commandHistory);
        assertEquals((int) model.getQuizMode(), QuizState.QUIZ_MODE_FRONT);
        assertEquals(true, model.getIsQuizSrs().getValue());
        assertEquals(model.getQuizFlashcards().size(), getAvailableSrsFlashcards().size() - 1);
    }


    @Test
    public void execute_inQuizMode_failure() throws CommandException {
        QuizCommand command = new QuizCommand();
        model.setQuizMode(QuizState.QUIZ_MODE_FRONT);
        assertCommandFailure(command, model, commandHistory, QuizCommand.MESSAGE_QUIZ_FAILURE_IN_QUIZ);

        command = new QuizCommand(true);
        model.setQuizMode(QuizState.QUIZ_MODE_FRONT);
        assertCommandFailure(command, model, commandHistory, QuizCommand.MESSAGE_QUIZ_FAILURE_IN_QUIZ);

        command = new QuizCommand(false);
        model.setQuizMode(QuizState.QUIZ_MODE_FRONT);
        assertCommandFailure(command, model, commandHistory, QuizCommand.MESSAGE_QUIZ_FAILURE_IN_QUIZ);
    }

    @Test
    public void execute_emptyList_failure() throws CommandException {
        QuizCommand command = new QuizCommand();
        model.updateFilteredFlashcardList((x) -> false);
        assertCommandFailure(command, model, commandHistory, QuizCommand.MESSAGE_QUIZ_FAILURE_EMPTY);

        command = new QuizCommand(false);
        model.updateFilteredFlashcardList((x) -> false);
        assertCommandFailure(command, model, commandHistory, QuizCommand.MESSAGE_QUIZ_FAILURE_EMPTY);

        command = new QuizCommand(true);
        model.updateFilteredFlashcardList((x) -> false);
        assertCommandFailure(command, model, commandHistory, QuizCommand.MESSAGE_QUIZ_FAILURE_EMPTY);
    }

    private List<Flashcard> getAvailableSrsFlashcards() {
        List<Flashcard> cards = model.getFilteredFlashcardList();
        return cards.stream()
                .filter(Flashcard::isIncludedInCurrentQuiz)
                .collect(Collectors.toList());
    }
}
