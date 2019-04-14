package seedu.address.ui.patient;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.patient.Patient;
import seedu.address.ui.UiPart;

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
    private Label pid;
    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label age;
    @FXML
    private Label phone;
    @FXML
    private Label number;
    @FXML
    private Label address;
    @FXML
    private Label tags;

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
     *
     * @param patient selected to be displayed.
     */
    private void loadPatientPage(Patient patient) {
        patientPage.getChildren().clear();

        pid.setText("Pid: " + patient.getIdToString());
        name.setText("Name: " + patient.getName().fullName);
        gender.setText("Gender: " + patient.getGender().value);
        age.setText("Age: " + patient.getAge().value);
        phone.setText("Phone: " + patient.getPhone().value);
        address.setText("Address: " + patient.getAddress().value);

        String tagsName = patient.getTags().toString().replaceAll("^\\[|\\]$", "");
        tags.setText("Tags: " + tagsName);

        patientPage.getChildren().addAll(pid, name, gender, age, phone, address, tags);
    }


    /**
     * Loads a default blank medical history with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        patientPage.getChildren().clear();
    }

}
