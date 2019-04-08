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
import seedu.address.model.person.Doctor;

/**
 * The Doctor Browser Panel of the App.
 */

public class DoctorBrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_DOCTOR_PAGE = "";

    private static final String FXML = "DoctorBrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;
    @FXML
    private GridPane doctorPage;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label year;
    @FXML
    private Label phone;
    @FXML
    private Label specs;
    @FXML
    private Label medHist;
    @FXML
    private Label appt;


    public DoctorBrowserPanel(ObservableValue<Doctor> selectedDoctor) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load card page when selected card changes.
        selectedDoctor.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultDoctor();
                return;
            }
            loadDoctorPage(newValue);
        });

        loadDefaultDoctor();
    }

    /**
     * Load the current selected {@code MedicalHistory} into the browser panel with all medical history info.
     * @param doctor selected to be displayed.
     */
    private void loadDoctorPage(Doctor doctor) {
        doctorPage.getChildren().clear();

        id.setText("Doctor ID: " + doctor.getId());
        name.setText("Doctor name: " + doctor.getName());
        gender.setText("Gender: " + doctor.getGender());
        year.setText("Year(s) of specialisation: " + doctor.getYear());
        phone.setText("Phone: " + doctor.getPhone());
        specs.setText("Specialisations: " + doctor.getSpecs()); // must loop through and display
        medHist.setText("Medical histories: "); // to do
        appt.setText("Appointments:"); // to do

        doctorPage.getChildren().addAll(id, name, gender, year, phone, specs, medHist, appt);
    }

    /**
     * Loads a default blank medical history with a background that matches the general theme.
     */
    private void loadDefaultDoctor() {
        doctorPage.getChildren().clear();

        id.setText("");
        name.setText("");
        gender.setText("");
        year.setText("");
        phone.setText("");
        specs.setText("");
        medHist.setText("");
        appt.setText("");
        doctorPage.getChildren().addAll(id, name, gender, year, phone, specs, medHist, appt);
    }

}
