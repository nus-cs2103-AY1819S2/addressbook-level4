package quickdocs.logic.commands;

import static quickdocs.logic.parser.AddPatientParser.PREFIX_ADDRESS;
import static quickdocs.logic.parser.AddPatientParser.PREFIX_CONTACT;
import static quickdocs.logic.parser.AddPatientParser.PREFIX_DOB;
import static quickdocs.logic.parser.AddPatientParser.PREFIX_EMAIL;
import static quickdocs.logic.parser.AddPatientParser.PREFIX_GENDER;
import static quickdocs.logic.parser.AddPatientParser.PREFIX_NAME;
import static quickdocs.logic.parser.AddPatientParser.PREFIX_NRIC;
import static quickdocs.logic.parser.AddPatientParser.PREFIX_TAG;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.patient.Patient;

/**
 * Command to add a patient record into QuickDocs
 */
public class AddPatientCommand extends Command {
    public static final String COMMAND_WORD = "addpat";
    public static final String COMMAND_ALIAS = "ap";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient to QuickDocs.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_DOB + "DATE OF BIRTH "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_CONTACT + "CONTACT "
            + PREFIX_GENDER + "GENDER "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Note: You can enter the parameters in any order as long as the essential parameters are captured.\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NRIC + "S9876543A "
            + PREFIX_DOB + "1980-03-03 "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_CONTACT + "92344321 "
            + PREFIX_GENDER + "M "
            + PREFIX_TAG + "highbloodpressure\n";
    public static final String CONFLICTING_NRIC = "Patient with same NRIC already existed";

    private Patient patientToAdd;

    public AddPatientCommand(Patient patient) {
        patientToAdd = patient;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.duplicatePatient(patientToAdd)) {
            throw new CommandException(CONFLICTING_NRIC);
        }
        model.addPatient(patientToAdd);

        StringBuilder sb = new StringBuilder();
        sb.append("Patient Added:\n");
        sb.append("==============================\n");
        sb.append(patientToAdd.toString());
        CommandResult result = new CommandResult(sb.toString(), false, false);
        return result;
    }

    public Patient getPatientToAdd() {
        return patientToAdd;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddPatientCommand
                && patientToAdd.equals(((AddPatientCommand) other).getPatientToAdd()));
    }
}
