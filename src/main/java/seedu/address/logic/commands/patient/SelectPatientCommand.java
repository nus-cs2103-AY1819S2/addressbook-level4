package seedu.address.logic.commands.patient;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.patient.Patient;

/**
 * Selects a patient identified using it's displayed index from the docX record.
 */
public class SelectPatientCommand extends Command {

    public static final String COMMAND_WORD = "select-p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the patient identified by the list index used in the displayed patient list.\n"
            + "Parameters: [INDEX] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Patient: %1$s";

    private final Index targetIndex;

    public SelectPatientCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Patient> filteredPatientList = model.getFilteredPatientList();

        if (targetIndex.getZeroBased() >= filteredPatientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        model.setSelectedPatient(filteredPatientList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()),
                CommandResult.ShowBrowser.PATIENT_BROWSER);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectPatientCommand // instanceof handles nulls
                && targetIndex.equals(((SelectPatientCommand) other).targetIndex)); // state check
    }
}
