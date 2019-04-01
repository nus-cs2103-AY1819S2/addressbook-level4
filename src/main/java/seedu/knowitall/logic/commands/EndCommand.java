package seedu.knowitall.logic.commands;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.Model.State;

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
switch(model.getState()) {
    case State.IN_TEST:
        model.endTestSession();
        return new CommandResult(MESSAGE_END_TEST_SESSION_SUCCESS, CommandResult.Type.END_TEST_SESSION);
    case State.IN_REPORT:
        model.exitReportDisplay();
        return new CommandResult(MESSAGE_END_REPORT_DISPLAY_SUCCESS, CommandResult.Type.EXITED_REPORT);
    default: 
        throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN);
}
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN);
        }

        if (model.getState() == State.IN_TEST) {
            model.endTestSession();
            return new CommandResult(MESSAGE_END_TEST_SESSION_SUCCESS, CommandResult.Type.END_TEST_SESSION);
        } else {
            model.exitReportDisplay();
            return new CommandResult(MESSAGE_END_REPORT_DISPLAY_SUCCESS, CommandResult.Type.EXITED_REPORT);
        }

    }

}
