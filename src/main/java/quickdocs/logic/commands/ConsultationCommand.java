package quickdocs.logic.commands;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.logic.parser.ConsultationCommandParser;
import quickdocs.model.Model;
import quickdocs.model.patient.Patient;

/**
 * Starts a consultation session for the specified patient using his or her NRIC
 */
public class ConsultationCommand extends Command {

    public static final String COMMAND_WORD = "consult";
    public static final String COMMAND_ALIAS = "c";
    public static final String NO_PATIENT_FOUND = "No patient with NRIC: %s found.\n";
    public static final String CONSULTATION_START = "Consultation session for: %s started\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": starts a consultation session for a patient.\n"
            + "Parameters: " + ConsultationCommandParser.PREFIX_NRIC + "NRIC of current patient.\n"
            + "Example: " + COMMAND_WORD + " " + ConsultationCommandParser.PREFIX_NRIC + "S9237162A\n";

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
