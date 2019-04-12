package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.prescription.Prescription;

/**
 * The Prescription Browser Panel of the App.
 */

public class PrescriptionBrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_MED_HIST_PAGE = "";

    private static final String FXML = "PrescriptionBrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;
    @FXML
    private GridPane prescriptionPage;
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
    private Label description;

    public PrescriptionBrowserPanel(ObservableValue<Prescription> selectedPrescription) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load card page when selected card changes.
        selectedPrescription.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPrescription();
                return;
            }
            loadPrescriptionPage(newValue);
        });

        loadDefaultPrescription();
    }

    /**
     * Load the current selected {@code Prescription} into the browser panel with more detailed info.
     * @param prescription selected to be displayed.
     */
    private void loadPrescriptionPage(Prescription prescription) {
        prescriptionPage.getChildren().clear();


        if (prescription.getPatient() == null) {
            patientId.setText("Patient ID: Patient Deleted");
            patientName.setText("Patient Name: Patient Deleted");
        } else {
            patientId.setText("Patient ID: " + prescription.getPatientId());
            patientName.setText("Patient Name: " + prescription.getPatient().getName());
        }

        if (prescription.getDoctor() == null) {
            doctorId.setText("Doctor ID: Doctor Deleted");
            doctorName.setText("Doctor Name: Doctor Deleted");
        } else {
            doctorId.setText("Doctor ID: " + prescription.getDoctorId());
            doctorName.setText("Doctor Name: " + prescription.getDoctor().getName());
        }

        date.setText("Date: " + prescription.getDate().toString());
        description.setText("Short description from Doctor: " + prescription.getDescription().toString());

        prescriptionPage.getChildren().addAll(patientId, patientName,
                doctorId, doctorName, date, description);
    }

    /**
     * Loads a default blank prescription with a background that matches the general theme.
     */
    private void loadDefaultPrescription() {
        prescriptionPage.getChildren().clear();

        patientId.setText("");
        patientName.setText("");
        doctorId.setText("");
        doctorName.setText("");
        date.setText("");
        description.setText("");
        prescriptionPage.getChildren().addAll(patientId, patientName,
                doctorId, doctorName, date, description);
    }

}

