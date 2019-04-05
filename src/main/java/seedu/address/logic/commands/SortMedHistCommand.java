package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Sort all medical histories in the list by date.
 */
public class SortMedHistCommand extends Command{
    public static final String COMMAND_WORD = "sort-med-hist";

    public static final String MESSAGE_SUCCESS = "All listed medical histories sorted.\n" +
            "If you don't see the list, please execute list-med-hist [pid/PATIENT_ID]";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        //model.sortFilteredMedHistList(Model.COMPARATOR_MED_HIST_DATE_ASC);
        model.sortFilteredMedHistList(Model.COMPARATOR_MED_HIST_DATE_DESC);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
