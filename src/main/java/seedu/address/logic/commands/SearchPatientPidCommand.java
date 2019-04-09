/* @@author wayneswq */
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.RecordContainsPatientIdPredicate;

/**
 * Searches for a patient in the docX record whose pid matches the input pid.
 */
public class SearchPatientPidCommand extends Command {

    public static final String COMMAND_WORD = "search-pid";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for a patient whose pid matches any of the"
            + "pid input and displays him/her\n"
            + "Parameters: [PID]\n"
            + "Example: " + COMMAND_WORD + " 7";

    private final RecordContainsPatientIdPredicate predicate;

    public SearchPatientPidCommand(RecordContainsPatientIdPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPatientList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PATIENTS_LISTED_OVERVIEW, model.getFilteredPatientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchPatientPidCommand // instanceof handles nulls
                && predicate.equals(((SearchPatientPidCommand) other).predicate)); // state check
    }
}
