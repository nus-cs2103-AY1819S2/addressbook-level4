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
import seedu.address.model.medicalhistory.MedicalHistory;

/**
 * The Medical History Browser Panel of the App.
 */

public class MedHistBrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_MED_HIST_PAGE = "";

    private static final String FXML = "MedHistBrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;
    @FXML
    private GridPane medHistPage;
    @FXML
    private Label medHistId;
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
    private Label writeUp;

    public MedHistBrowserPanel(ObservableValue<MedicalHistory> selectedMedHist) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load card page when selected card changes.
        selectedMedHist.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultMedHist();
                return;
            }
            loadMedHistPage(newValue);
        });

        loadDefaultMedHist();
    }

    /**
     * Load the current selected {@code MedicalHistory} into the browser panel with all medical history info.
     * @param medHist selected to be displayed.
     */
    private void loadMedHistPage(MedicalHistory medHist) {
        medHistPage.getChildren().clear();

        medHistId.setText("Medical History ID: " + medHist.getMedHistId());
        patientId.setText("Patient ID: " + medHist.getPatientId());
        patientName.setText("Patient Name: " + medHist.getPatient().getName());
        doctorId.setText("Doctor ID: " + medHist.getDoctorId());
        doctorName.setText("Doctor Name: " + medHist.getDoctor().getName());
        date.setText("Date: " + medHist.getDate().toString());
        writeUp.setText("Short Write Up from Doctor: " + medHist.getWriteUp().toString());

        medHistPage.getChildren().addAll(medHistId, patientId, patientName, doctorId, doctorName, date, writeUp);
    }

    /**
     * Loads a default blank medical history with a background that matches the general theme.
     */
    private void loadDefaultMedHist() {
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
