package seedu.address.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_DATE_OF_APPT;
import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_APPT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.FutureAppointment;
import seedu.address.model.person.patient.Patient;

/**
 * Adds a new appointment between a patient and doctor.
 */
public class AddAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "add-appt";
    public static final String COMMAND_EXAMPLE = "Example: " + COMMAND_WORD + " "
            + PREFIX_PATIENT_ID + "1 "
            + PREFIX_DOCTOR_ID + "7 "
            + PREFIX_DATE_OF_APPT + "2019-06-01 "
            + PREFIX_START_TIME + "09:00 ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new appointment to docX. "
            + "Parameters: "
            + PREFIX_PATIENT_ID + PLACEHOLDER_PATIENT_ID + " "
            + PREFIX_DOCTOR_ID + PLACEHOLDER_DOCTOR_ID + " "
            + PREFIX_DATE_OF_APPT + PLACEHOLDER_DATE_OF_APPT + " "
            + PREFIX_START_TIME + PLACEHOLDER_START_TIME + " " + "\n"
            + COMMAND_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in docX";
    public static final String MESSAGE_PATIENT_NOT_FOUND =
            "Patient with the ID is not found. Please enter a valid patient ID.";
    public static final String MESSAGE_DOCTOR_NOT_NOT_FOUND =
            "Doctor with the ID is not found. Please enter a valid doctor ID.";

    private FutureAppointment appointment;

    public AddAppointmentCommand(FutureAppointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.hasAppointment(appointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        appointment.setPatient(model.getPatientById(appointment.getPatientId()));
        appointment.setDoctor(model.getDoctorById(appointment.getDoctorId()));

        if (appointment.getPatient() == null) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }
        if (appointment.getDoctor() == null) {
            throw new CommandException(MESSAGE_DOCTOR_NOT_NOT_FOUND);
        }

        model.addAppointment(appointment);
        model.commitDocX();

        Patient patientToChange = appointment.getPatient();
        Patient changedPatient = patientToChange.changeAppointmentStatus(AppointmentStatus.ACTIVE);
        model.setPatient(patientToChange, changedPatient);
        model.commitDocX();

        return new CommandResult(String.format(MESSAGE_SUCCESS, appointment));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddAppointmentCommand that = (AddAppointmentCommand) o;
        return appointment.equals(that.appointment);
    }
}
