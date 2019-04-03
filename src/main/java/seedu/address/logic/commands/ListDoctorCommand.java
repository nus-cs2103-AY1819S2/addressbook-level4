package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DOCTORS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all doctors in the docX to the user.
 */
public class ListDoctorCommand extends Command {

    public static final String COMMAND_WORD = "list-doctor";

    public static final String MESSAGE_SUCCESS = "Listed all doctors";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDoctorList(PREDICATE_SHOW_ALL_DOCTORS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
