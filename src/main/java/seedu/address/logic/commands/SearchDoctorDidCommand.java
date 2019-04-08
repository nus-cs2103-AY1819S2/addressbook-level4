package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.RecordContainsDoctorIdPredicate;

/**
 * Searches for a doctor in the docX record whose pid matches the input pid.
 */
public class SearchDoctorDidCommand extends Command {

    public static final String COMMAND_WORD = "search-did";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for a doctor whose did matches any of the"
            + "did input and displays him/her\n"
            + "Parameters: [DID]\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final RecordContainsDoctorIdPredicate predicate;

    public SearchDoctorDidCommand(RecordContainsDoctorIdPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDoctorList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_DOCTORS_LISTED_OVERVIEW, model.getFilteredDoctorList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchDoctorDidCommand // instanceof handles nulls
                && predicate.equals(((SearchDoctorDidCommand) other).predicate)); // state check
    }
}
