package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sort all medical histories in the list by date.
 */
public class SortMedHistCommand extends Command {
    public static final String COMMAND_WORD = "sort-med-hist";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sort medical histories by default descending order (from latest to oldest)\n"
            + COMMAND_WORD
            + " [ORDER]"
            + ": sort medical histories by ascending(ASC) and descending(DESC) order"
            + "Example: sort-med-hist\n"
            + "         sort-med-hist ASC  sort-med-hist DESC";

    public static final String MESSAGE_SUCCESS = "All listed medical histories sorted.\n"
            + "If you don't see the list, please execute list-med-hist [pid/PATIENT_ID]";

    public static final String ASCENDING = "ASC";

    public static final String DESCENDING = "DESC";

    private final Optional<String> order;

    public SortMedHistCommand() {
        this.order = Optional.empty();
    }

    public SortMedHistCommand(String order) {
        this.order = Optional.of(order);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (!order.isPresent() || order.get().equals(DESCENDING)) {
            model.sortFilteredMedHistList(Model.COMPARATOR_MED_HIST_DATE_DESC);
        } else if (order.get().equals(ASCENDING)) {
            model.sortFilteredMedHistList(Model.COMPARATOR_MED_HIST_DATE_ASC);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
