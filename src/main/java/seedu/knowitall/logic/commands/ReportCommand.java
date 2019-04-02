package seedu.knowitall.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER;
import static seedu.knowitall.model.Model.COMPARATOR_ASC_SCORE_CARDS;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.Model.State;

/**
 * Display a report for a folder identified using it's displayed index from the card folder list.
 */
public class ReportCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_SUCCESS = "Report displayed";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getState() != State.IN_FOLDER) {
            throw new CommandException(MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        }

        model.enterReportDisplay();
        model.sortFilteredCard(COMPARATOR_ASC_SCORE_CARDS);
        return new CommandResult(MESSAGE_SUCCESS, CommandResult.Type.ENTERED_REPORT);
    }
}
