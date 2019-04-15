package seedu.address.logic.commands.prescription;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_PRESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.prescription.Prescription;


/**
 * Adds a prescription.
 */
public class AddPrescriptionCommand extends Command {

    public static final String COMMAND_WORD = "add-presc";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a prescription to the docX."
            + "Parameters: "
            + PREFIX_PATIENT_ID + "PATIENT-ID "
            + PREFIX_DOCTOR_ID + "DOCTOR-ID "
            + PREFIX_DATE_OF_PRESC + "DATE "
            + PREFIX_MEDICINE_NAME + "MEDICINE NAME "
            + PREFIX_DESCRIPTION + "SHORT DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATIENT_ID + "1 "
            + PREFIX_DOCTOR_ID + "2 "
            + PREFIX_DATE_OF_PRESC + "2018-05-13 "
            + PREFIX_MEDICINE_NAME + "Acetaminophen" + " "
            + PREFIX_DESCRIPTION + "500 mg for relieving pain";
    public static final String MESSAGE_SUCCESS = "New prescription added: %1$s";
    public static final String MESSAGE_DUPLICATE_PRESCRIPTION = "This prescription already exists in the docX";
    public static final String MESSAGE_PATIENT_NOT_FOUND =
            "Patient with the ID is not found. Please enter a valid patient ID.";
    public static final String MESSAGE_DOCTOR_NOT_FOUND =
            "Doctor with the ID is not found. Please enter a valid doctor ID.";

    private final Prescription prescriptionToAdd;

    /**
     * Creates an addPatientCommand to add the specified {@code Patient}
     */
    public AddPrescriptionCommand(Prescription prescriptionToAdd) {
        requireNonNull(prescriptionToAdd);
        this.prescriptionToAdd = prescriptionToAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPrescription(prescriptionToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRESCRIPTION);
        }
        getPatientById(model);
        getDoctorById(model);

        model.addPrescription(prescriptionToAdd);
        model.commitDocX();
        return new CommandResult(String.format(MESSAGE_SUCCESS, prescriptionToAdd));

    }

    private void getPatientById(Model model) throws CommandException {
        Patient patientWithId = model.getPatientById(prescriptionToAdd.getPatientId());
        if (patientWithId == null) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }
        this.prescriptionToAdd.setPatient(patientWithId);
    }

    private void getDoctorById(Model model) throws CommandException {
        Doctor doctorWithId = model.getDoctorById(prescriptionToAdd.getDoctorId());
        if (doctorWithId == null) {
            throw new CommandException(MESSAGE_DOCTOR_NOT_FOUND);
        }
        this.prescriptionToAdd.setDoctor(doctorWithId);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof AddPrescriptionCommand
                && this.prescriptionToAdd.equals(((AddPrescriptionCommand) other).prescriptionToAdd);
    }

}



