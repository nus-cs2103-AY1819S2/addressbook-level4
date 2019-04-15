package seedu.address.logic.commands.prescription;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Sort all prescriptions in the list by date.
 */
public class SortPrescriptionCommand extends Command {
    public static final String COMMAND_WORD = "sort-presc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sort prescriptions in default order(descending)\n"
            + COMMAND_WORD
            + " [ORDER]"
            + ": sort prescriptions in ascending(ASC) or descending(DESC) order\n"
            + "Example: sort-presc\n"
            + "         sort-presc ASC\n"
            + "         sort-presc DESC";

    public static final String MESSAGE_SUCCESS = "All listed prescriptions sorted.\n"
            + "If you don't see the list, please execute list-presc [pid/PATIENT_ID]";

    public static final String ASCENDING = "ASC";

    public static final String DESCENDING = "DESC";

    private final Optional<String> order;

    public SortPrescriptionCommand() {
        this.order = Optional.empty();
    }

    public SortPrescriptionCommand(String order) {
        this.order = Optional.of(order);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (!order.isPresent() || order.get().equals(DESCENDING)) {
            model.sortFilteredPrescriptionList(Model.COMPARATOR_PRESC_DATE_DESC);
        } else if (order.get().equals(ASCENDING)) {
            model.sortFilteredPrescriptionList(Model.COMPARATOR_PRESC_DATE_ASC);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
