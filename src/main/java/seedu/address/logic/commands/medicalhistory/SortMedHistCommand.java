package seedu.address.logic.commands.medicalhistory;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Sort all medical histories in the list by date.
 */
public class SortMedHistCommand extends Command {
    public static final String COMMAND_WORD = "sort-med-hist";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sort medical histories by default descending order (from latest to oldest)\n"
            + COMMAND_WORD
            + " [ASC/DESC]"
            + ": sort medical histories by ascending(ASC) and descending(DESC) order\n"
            + "Example: sort-med-hist\n"
            + "         sort-med-hist ASC\n"
            + "         sort-med-hist DESC";

    public static final String MESSAGE_SUCCESS = "All listed medical histories sorted by date ";

    public static final String REMINDER_OF_LIST = "If you don't see the list, please execute list-med-hist command";

    public static final String SORT_BY_ASC = "in ascending order.\n";

    public static final String SORT_BY_DESC = "in descending order.\n";

    public static final String ASCENDING = "ASC";

    public static final String DESCENDING = "DESC";

    private final SortMedHistDescriptor sortMedHistDescriptor;

    public SortMedHistCommand(SortMedHistDescriptor sortMedHistDescriptor) {
        this.sortMedHistDescriptor = sortMedHistDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String result = MESSAGE_SUCCESS;

        if (!sortMedHistDescriptor.getOrder().isPresent()
                || sortMedHistDescriptor.getOrder().get().equals(DESCENDING)) {
            model.sortFilteredMedHistList(Model.COMPARATOR_MED_HIST_DATE_DESC);
            result = result + SORT_BY_DESC;
        } else if (sortMedHistDescriptor.getOrder().get().equals(ASCENDING)) {
            model.sortFilteredMedHistList(Model.COMPARATOR_MED_HIST_DATE_ASC);
            result = result + SORT_BY_ASC;
        }

        result = result + REMINDER_OF_LIST;

        return new CommandResult(result);
    }


    /**
     * Stores the fields to sort medical history by.
     */
    public static class SortMedHistDescriptor {

        private Optional<String> order;

        public SortMedHistDescriptor() {
            order = Optional.empty();
        }

        /**
         * Copy constructor. For defensive purposes, ensures only a copy is used.
         */
        public SortMedHistDescriptor(SortMedHistDescriptor toCopy) {
            setOrder(toCopy.order);
        }

        public Optional<String> getOrder() {
            return order;
        }

        public void setOrder(Optional<String> order) {
            this.order = order;
        }
    }
}
