package seedu.address.logic.commands.doctor;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DOCTORS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.doctor.DoctorContainsKeywordsPredicate;

/**
 * Lists all doctors in the docX to the user.
 */
public class ListDoctorCommand extends Command {

    public static final String COMMAND_WORD = "list-d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the doctors whose fields' "
            + "values match the user inputs. If no inputs are given, all existing doctors will be listed.\n"
            + "Parameters: "
            + "[ KEYWORD ] "
            + "[ KEYWORD ] ..."
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Listed all doctors";

    private final DoctorContainsKeywordsPredicate predicate;

    public ListDoctorCommand() {
        this.predicate = null;
    }

    public ListDoctorCommand(DoctorContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (predicate == null) {
            model.updateFilteredDoctorList(PREDICATE_SHOW_ALL_DOCTORS);
        } else {
            model.updateFilteredDoctorList(predicate);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_DOCTORS_LISTED_OVERVIEW, model.getFilteredDoctorList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (predicate == null) {
            return other == this;
        }
        return other == this // short circuit if same object
                || (other instanceof ListDoctorCommand // instanceof handles nulls
                && predicate.equals(((ListDoctorCommand) other).predicate)); // state check
    }
}
