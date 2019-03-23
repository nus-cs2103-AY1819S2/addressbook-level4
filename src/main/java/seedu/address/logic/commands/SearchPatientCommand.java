package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.PatientNameContainsKeywordsPredicate;

/**
 * Searches and lists all patients in docX record whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchPatientCommand extends Command {

    public static final String COMMAND_WORD = "search-patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all patients whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
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
