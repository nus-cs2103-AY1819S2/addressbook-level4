package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.prescription.Prescription;

/**
 * An UI component that displays information of a {@code Prescription}.
 */
public class PrescriptionCard extends UiPart<Region> {
    private static final String FXML = "PrescriptionListCard.fxml";

    public final Prescription prescription;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label patientId;
    @FXML
    private Label doctorId;
    @FXML
    private Label medicineName;
    @FXML
    private Label description;
    @FXML
    private Label id;

    public PrescriptionCard(Prescription prescription, int displayedIndex) {
        super(FXML);
        this.prescription = prescription;
        id.setText(displayedIndex + ". ");
        patientId.setText("Patient ID: " + prescription.getPatientId());
        doctorId.setText("Doctor ID: " + prescription.getDoctorId());
        medicineName.setText("Medicine Name: " + prescription.getMedicine().getName());
        description.setText("Description: " + prescription.getDescription().toString());

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof PrescriptionCard == false) {
            return false;
        }

        PrescriptionCard another = (PrescriptionCard) other;
        return another.medicineName.equals(this.medicineName)
                && another.description.equals(this.description)
                && id.getText().equals(another.id.getText());
    }

}
