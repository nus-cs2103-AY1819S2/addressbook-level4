package braintrain.quiz.commands;

import static org.junit.Assert.assertEquals;

import braintrain.logic.CommandHistory;
import braintrain.logic.commands.CommandResult;
import braintrain.logic.commands.exceptions.CommandException;
import braintrain.quiz.QuizModel;

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
