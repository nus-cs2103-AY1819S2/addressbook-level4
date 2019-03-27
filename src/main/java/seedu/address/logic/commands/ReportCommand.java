package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_INSIDE_REPORT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Display a report for a folder identified using it's displayed index from the card folder list.
 */
public class ReportCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_SUCCESS = "Report displayed";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isInFolder()) {
            throw new CommandException(MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        }
        
        if (model.inReportDisplay()) {
            throw new CommandException(MESSAGE_INVALID_COMMAND_INSIDE_REPORT);
        }

        model.enterReportDisplay();
        return new CommandResult(MESSAGE_SUCCESS, CommandResult.Type.ENTERED_REPORT);
    }
}
