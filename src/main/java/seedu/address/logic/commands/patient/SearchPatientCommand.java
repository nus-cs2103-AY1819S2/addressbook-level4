package seedu.address.logic.commands.patient;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.patient.PatientNameContainsKeywordsPredicate;

/**
 * Searches and lists all patients in docX record whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchPatientCommand extends Command {

    public static final String COMMAND_WORD = "search-p-name";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for patients whose names contain any of "
            + "the specified keywords. (case-insensitive)\n"
            + "Parameters: [NAME] [NAME2]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final PatientNameContainsKeywordsPredicate predicate;

    public SearchPatientCommand(PatientNameContainsKeywordsPredicate predicate) {
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
                || (other instanceof SearchPatientCommand // instanceof handles nulls
                && predicate.equals(((SearchPatientCommand) other).predicate)); // state check
    }
}
