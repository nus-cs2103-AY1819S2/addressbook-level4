package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelManager.quizModel.QuizModel;

/**
 * Contains helper methods for testing commands.
 */
public class QuizCommandTestUtil {
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(QuizCommand command, QuizModel actualModel,
                                            CommandHistory actualCommandHistory, CommandResult expectedCommandResult,
                                            QuizModel expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(QuizCommand, QuizModel, CommandHistory,
     * CommandResult, QuizModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(QuizCommand command, QuizModel actualModel,
                                            CommandHistory actualCommandHistory,
                                            String expectedMessage, QuizModel expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }
}
