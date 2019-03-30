package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Ends the current test session.
 * This command is valid only when user is in a test session.
 */
public class EndCommand extends Command {

    public static final String COMMAND_WORD = "end";

    public static final String MESSAGE_END_TEST_SESSION_SUCCESS = "End test session";
    public static final String MESSAGE_END_REPORT_DISPLAY_SUCCESS = "End report display";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.checkIfInsideTestSession() && !model.inReportDisplay()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN);
        }

        if (model.checkIfInsideTestSession()) {
            model.endTestSession();
            return new CommandResult(MESSAGE_END_TEST_SESSION_SUCCESS, CommandResult.Type.END_TEST_SESSION);
        } else {
            model.exitReportDisplay();
            return new CommandResult(MESSAGE_END_REPORT_DISPLAY_SUCCESS, CommandResult.Type.EXITED_REPORT);
        }

    }

}
