/* @@author wayneswq */
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.PatientApptStatusContainsKeywordsPredicate;

/**
 * Searches and lists all patients in docX record whose appointment status contains any of the 4 keywords.
 * Keyword matching is case insensitive.
 */
public class SearchPatientApptStatusCommand extends Command {

    public static final String COMMAND_WORD = "search-appt-status";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all patients whose appointment status "
            + "is one of the following: ACTIVE, CANCELLED, COMPLETED or MISSED (not case-sensitive)\n"
            + "Parameters: [KEYWORD]\n"
            + "Example: " + COMMAND_WORD + " MISSED";

    public static final String MESSAGE_INVALID_STATUS = COMMAND_WORD + ":\n"
            + "Please use only one of the following: ACTIVE, CANCELLED, COMPLETED or MISSED (case-insensitive)\n"
            + "Parameters: [KEYWORD]\n";

    private final PatientApptStatusContainsKeywordsPredicate predicate;

    public SearchPatientApptStatusCommand(PatientApptStatusContainsKeywordsPredicate predicate) {
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
                || (other instanceof SearchPatientApptStatusCommand // instanceof handles nulls
                && predicate.equals(((SearchPatientApptStatusCommand) other).predicate)); // state check
    }
}
