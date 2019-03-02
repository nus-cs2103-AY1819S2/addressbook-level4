package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;

/**
 * Command to add a patient record into QuickDocs
 */
public class AddPatientCommand extends Command {
    public static final String COMMAND_WORD = "padd";

    private Patient toAdd;

    public AddPatientCommand(Patient patient) {
        toAdd = patient;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.duplicatePatient(toAdd)) {
            throw new CommandException("Patient with same NRIC already exist");
        }
        model.addPatient(toAdd);

        StringBuilder sb = new StringBuilder();
        sb.append("Patient Added:\n");
        sb.append("==============================\n");
        sb.append(toAdd.toString());
        CommandResult result = new CommandResult(sb.toString(), false, false);
        return result;
    }

    public Patient getToAdd() {
        return toAdd;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddPatientCommand
                && toAdd.equals(((AddPatientCommand) other).getToAdd()));
    }
}
