package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;

/**
 * Creates a consultation session for the current patient
 */
public class ConsultationCommand extends Command {

    public static final String COMMAND_WORD = "consult";

    // current consultations can only be created by nric searches
    // will explore into other consultation creation (i.e. name, index) in future builds
    private String nric;

    public ConsultationCommand(String nric) {
        this.nric = nric;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Patient patient = model.getPatientByNric(nric);
        if (patient == null) {
            throw new CommandException("No patient found to start consultation");
        }

        model.createConsultation(patient);

        String consultationResult = "Consultation session for: " + nric + " started\n";

        return new CommandResult(consultationResult);
    }

    public String getNric() {
        return nric;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ConsultationCommand
                && nric.equals(((ConsultationCommand) other).getNric()));
    }
}
