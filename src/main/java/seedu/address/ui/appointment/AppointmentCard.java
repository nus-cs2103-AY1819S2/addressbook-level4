package seedu.address.ui.appointment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {
    public static final String MESSAGE_PATIENT_DELETED = "Patient deleted.";
    public static final String MESSAGE_DOCTOR_DELETED = "Doctor deleted.";
    private static final String FXML = "AppointmentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on DocX level 4</a>
     */

    public final Appointment appointment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label status;
    @FXML
    private Label patientId;
    @FXML
    private Label patientName;
    @FXML
    private Label doctorId;
    @FXML
    private Label doctorName;
    @FXML
    private Label date;
    @FXML
    private Label time;

    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;
        id.setText(displayedIndex + ". ");
        status.setText(appointment.getAppointmentStatus().name());
        if (appointment.getPatient() == null) {
            patientId.setText(MESSAGE_PATIENT_DELETED);
            patientName.setText(MESSAGE_PATIENT_DELETED);
        } else {
            patientId.setText(String.valueOf(appointment.getPatientId()));
            patientName.setText(appointment.getPatient().getName().fullName);
        }

        if (appointment.getDoctor() == null) {
            doctorId.setText(MESSAGE_DOCTOR_DELETED);
            doctorName.setText(MESSAGE_DOCTOR_DELETED);
        } else {
            doctorId.setText(String.valueOf(appointment.getDoctorId()));
            doctorName.setText(appointment.getDoctor().getName().fullName);
        }

        date.setText(appointment.getDate().toString());
        time.setText(appointment.getTime().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentCard)) {
            return false;
        }

        // state check
        AppointmentCard card = (AppointmentCard) other;
        return id.getText().equals(card.id.getText())
                && patientId.equals(card.patientId)
                && doctorId.equals(card.doctorId)
                && date.equals(card.date)
                && time.equals(card.time)
                && appointment.equals(card.appointment);
    }
}
