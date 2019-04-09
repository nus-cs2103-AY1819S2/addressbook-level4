package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.medicalhistory.MedicalHistory;

/**
 * An UI component that displays information of a {@code MedicalHistory}.
 */
public class MedHistCard extends UiPart<Region> {
    private static final String FXML = "MedHistListCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on DocX level 4</a>
     */
    public final MedicalHistory medHist;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label medicalHistoryId;
    @FXML
    private Label patientId;
    @FXML
    private Label doctorId;
    @FXML
    private Label date;
    @FXML
    private Label id;

    public MedHistCard(MedicalHistory medHist, int displayedIndex) {
        super(FXML);
        this.medHist = medHist;
        id.setText(displayedIndex + ". ");
        medicalHistoryId.setText("ID: " + medHist.getMedHistId());
        if (medHist.getPatient() == null) {
            patientId.setText("Patient ID: Patient Deleted");
        } else {
            patientId.setText("Patient ID: " + medHist.getPatientId());
        }
        if (medHist.getDoctor() == null) {
            doctorId.setText("Doctor ID: Doctor Deleted");
        } else {
            doctorId.setText("Doctor ID: " + medHist.getDoctorId());
        }
        date.setText("Date: " + medHist.getDate().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MedHistCard)) {
            return false;
        }

        // state check
        MedHistCard card = (MedHistCard) other;
        return id.getText().equals(card.id.getText()) && medicalHistoryId.equals(card.medicalHistoryId)
                && patientId.equals(card.patientId) && doctorId.equals(card.doctorId)
                && date.equals(card.date);
    }

}

