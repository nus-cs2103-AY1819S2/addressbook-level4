/* @@author wayneswq */
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.PatientTagContainsKeywordsPredicate;

/**
 * Searches and lists all patients in docX record whose tag contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchPatientTagCommand extends Command {

    public static final String COMMAND_WORD = "search-tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all patients whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [KEYWORD] [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " highbloodpressure diabetes";

    private final PatientTagContainsKeywordsPredicate predicate;

    public SearchPatientTagCommand(PatientTagContainsKeywordsPredicate predicate) {
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
                || (other instanceof SearchPatientTagCommand // instanceof handles nulls
                && predicate.equals(((SearchPatientTagCommand) other).predicate)); // state check
    }
}
