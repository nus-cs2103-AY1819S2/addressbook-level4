package seedu.address.logic.commands.doctor;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.doctor.Doctor;


/**
 * Selects a doctor identified using it's displayed index from the docX record.
 */
public class SelectDoctorCommand extends Command {

    public static final String COMMAND_WORD = "select-d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the information of the doctor identified by"
            + " the index number used in the displayed doctor list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_DOCTOR_SUCCESS = "Selected Doctor: %1$s";

    private final Index targetIndex;

    public SelectDoctorCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Doctor> filteredDoctorList = model.getFilteredDoctorList();

        if (targetIndex.getZeroBased() >= filteredDoctorList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
        }

        model.setSelectedDoctor(filteredDoctorList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_DOCTOR_SUCCESS, targetIndex.getOneBased()),
                CommandResult.ShowBrowser.DOCTOR_BROWSER);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectDoctorCommand // instanceof handles nulls
                && targetIndex.equals(((SelectDoctorCommand) other).targetIndex)); // state check
    }
}
