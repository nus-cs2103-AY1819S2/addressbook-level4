package seedu.address.logic.commands;

import static seedu.address.logic.parser.ConsultationCommandParser.PREFIX_NRIC;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;

/**
 * Creates a consultation session for the current patient
 */
public class ConsultationCommand extends Command {

    public static final String COMMAND_WORD = "consult";
    public static final String COMMAND_ALIAS = "c";
    public static final String NO_PATIENT_FOUND = "No patient with NRIC: %s found.\n";
    public static final String CONSULTATION_START = "Consultation session for: %s started\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": starts a consultation session for a patient.\n"
            + "Parameters: " + PREFIX_NRIC + "NRIC of current patient.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NRIC + "S9237162A\n";

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
            throw new CommandException(String.format(NO_PATIENT_FOUND, nric));
        }

        model.createConsultation(patient);

        String consultationResult = String.format(CONSULTATION_START, nric);

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
