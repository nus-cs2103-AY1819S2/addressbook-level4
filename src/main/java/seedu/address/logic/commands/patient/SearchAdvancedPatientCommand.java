/* @@author wayneswq */
package seedu.address.logic.commands.patient;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.patient.AdvancedPatientSearchPredicate;

/**
 * Searches and lists all patients in docX record whose details contains all of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchAdvancedPatientCommand extends Command {

    public static final String COMMAND_WORD = "search-p-advanced";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for patients whose full detail contains "
            + "ALL of the entered keywords.\n"
            + "Advanced feature: Use double-quoted \"keyword\" to search for the exact full keyword.\n"
            + "Parameters: [KEYWORD] [\"QUOTED KEYWORD\"]...\n"
            + "Example: " + COMMAND_WORD + " 9321 \"stroke\"\n";

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
