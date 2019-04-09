/* @@author wayneswq */
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.AdvancedPatientSearchPredicate;

/**
 * Searches and lists all patients in docX record whose details contains all of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchAdvancedPatientCommand extends Command {

    public static final String COMMAND_WORD = "search-advanced";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all patients whose full details contain "
            + "any of the specified keywords, even if the keyword is only a substring of the details.\n"
            + "Advanced feature: Use double quoted keyword to search only for the full keyword.\n"
            + "Parameters: [KEYWORD] [\"QUOTED KEYWORD\"]...\n"
            + "Example: " + COMMAND_WORD + " 9321 \"flu\"\n"
            + "pid and appt status are not searched";

    private final AdvancedPatientSearchPredicate predicate;

    public SearchAdvancedPatientCommand(AdvancedPatientSearchPredicate predicate) {
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
                || (other instanceof SearchAdvancedPatientCommand // instanceof handles nulls
                && predicate.equals(((SearchAdvancedPatientCommand) other).predicate)); // state check
    }
}
