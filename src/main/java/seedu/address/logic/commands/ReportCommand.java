package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.AnswerCommandResultType;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Display a report for a folder identified using it's displayed index from the card folder list.
 */
public class ReportCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_SUCCESS = "Report displayed";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false,
                null, false, true, AnswerCommandResultType.NOT_ANSWER_COMMAND);
    }
}
