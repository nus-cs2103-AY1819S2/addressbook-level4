package seedu.address.logic.commands.management;

import static org.junit.Assert.assertEquals;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.ManagementModel;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualManagementModel} matches {@code expectedManagementModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, ManagementModel actualManagementModel,
                                            CommandHistory actualCommandHistory, CommandResult expectedCommandResult,
                                            ManagementModel expectedManagementModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualManagementModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedManagementModel, actualManagementModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, ManagementModel, CommandHistory,
     * CommandResult, ManagementModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, ManagementModel actualManagementModel,
                                            CommandHistory actualCommandHistory, String expectedMessage,
                                            ManagementModel expectedManagementModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualManagementModel, actualCommandHistory, expectedCommandResult,
                expectedManagementModel);
    }
}
