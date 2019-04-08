package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.Patient;

/**
 * The Browser Panel of the App.
 */
public class PatientInfoPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));

    public static final String SEARCH_PAGE_URL = "";

    private static final String FXML = "PatientInfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;
    @FXML
    private GridPane patientPage;
    @FXML
    private Label patientName;
    @FXML
    private Label patientId;

    @FXML
    private Label doctorName;
    @FXML
    private Label date;
    @FXML
    private Label writeUp;

    public PatientInfoPanel(ObservableValue<Patient> selectedPatient) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load patient page when selected patient changes.
        selectedPatient.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadPatientPage(newValue);
        });

        loadDefaultPage();
    }

    /**
     * Load the current selected {@code Patient} into the browser panel with all patient info.
     * @param patient selected to be displayed.
     */
    private void loadPatientPage(Patient patient) {
        patientPage.getChildren().clear();

        patientId.setText("Patient ID: " + patient.getIdToString());
        patientName.setText("Patient Name: " + patient.getName());

        doctorId.setText("Doctor ID: " + medHist.getDoctorId());
        doctorName.setText("Doctor Name: ");
        date.setText("Date: " + medHist.getDate().toString());
        writeUp.setText("Short Write Up from Doctor: " + medHist.getWriteUp().toString());

        patientPage.getChildren().addAll(medHistId, patientId, patientName, doctorId, doctorName, date, writeUp);
    }


    /**
     * Loads a default blank medical history with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        medHistPage.getChildren().clear();

        medHistId.setText("");
        patientId.setText("");
        patientName.setText("");
        doctorId.setText("");
        doctorName.setText("");
        date.setText("");
        writeUp.setText("");
        medHistPage.getChildren().addAll(medHistId, patientId, patientName, doctorId, doctorName, date, writeUp);
    }

}
