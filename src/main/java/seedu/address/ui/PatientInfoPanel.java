package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;

/**
 *  Tab panel that contains two tabs.
 *  One tab contains patient particular and the other contains record details.
 *  Record details tab only shows up during GOTO mode
 */
public class PatientInfoPanel extends UiPart<Region> {

    private static final String FXML = "PatientInfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private ScrollPane background;
    @FXML
    private TabPane tabManger;
    @FXML
    private Tab patientParticulars;
    @FXML
    private Tab patientRecords;
    @FXML
    private VBox particularsPane;
    @FXML
    private VBox recordsPane;
    @FXML
    private Label name;
    @FXML
    private Label nric;
    @FXML
    private Label sex;
    @FXML
    private Label age;
    @FXML
    private Label dob;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Line seperator;
    @FXML
    private Label drugallergy;
    @FXML
    private Label teethtype;
    @FXML
    private Label description;
    @FXML
    private Line seperator2;
    @FXML
    private Label nokname;
    @FXML
    private Label nokrelation;
    @FXML
    private Label nokaddress;
    @FXML
    private Label nokemail;
    @FXML
    private Label nokphone;
    @FXML
    private Label recorddoctor;
    @FXML
    private Label recorddate;
    @FXML
    private Label recorddescription;

    public PatientInfoPanel(ObservableValue<Person> selectedPerson, ObservableValue<Record> selectedRecord) {
        super(FXML);
        tabManger = new TabPane();
        background.widthProperty().addListener((observable, oldValue, newValue) -> {
            name.maxWidthProperty().bind(background.widthProperty().subtract(100));
            seperator.endXProperty().bind(background.widthProperty());
            seperator2.endXProperty().bind(background.widthProperty());
            tabManger.getTabs().set(0, patientParticulars);
        });
        this.patientParticulars = new Tab("Patient Particulars");
        particularsPane.setStyle("-fx-padding: 0 0 0 5");
        recordsPane.setStyle("-fx-padding: 0 0 0 5");
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadPersonPage(newValue);
        });

        selectedRecord.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadRecordTab();
                return;
            }
            loadRecordPage(newValue);
        });
        loadDefaultPage();
        tabManger.getTabs().add(patientParticulars);
        tabManger.minWidthProperty().bind(background.widthProperty());
        tabManger.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabManger.setStyle("-fx-close-tab-animation: NONE");
        background.setContent(tabManger);
        background.setStyle("-fx-background-color: transparent");
    }

    /**
     * Loads default page until a patient is selected
     */
    public void loadDefaultPage() {
        Label defaultPage = new Label(" No patient has been selected yet.");
        defaultPage.setStyle("-fx-text-fill: white");
        this.patientParticulars.setContent(defaultPage);
    }

    /**
     *  Populates the tab with information of a selected person
     * @param person whose details are to be loaded
     */
    public void loadPersonPage(Person person) {
        name.setText(person.getName().fullName);
        name.setWrapText(true);
        nric.setText(((Patient) person).getNric().getNric());
        sex.setText(((Patient) person).getSex().value);
        dob.setText(((Patient) person).getDateOfBirth().getDate());
        age.setText(String.valueOf(((Patient) person).getDateOfBirth().getAge()));
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        address.setText(person.getAddress().value);

        seperator.endXProperty().bind(this.particularsPane.widthProperty());

        drugallergy.setText(((Patient) person).getDrugAllergy().allergyName);
        description.setText(((Patient) person).getPatientDesc().value);
        teethtype.setText(((Patient) person).getTeeth().getTeethType());

        seperator2.endXProperty().bind(this.particularsPane.widthProperty());

        nokname.setText(((Patient) person).getNextOfKin().getName().fullName);
        nokrelation.setText(((Patient) person).getNextOfKin().getKinRelation().relationship);
        nokaddress.setText(((Patient) person).getNextOfKin().getAddress().value);
        nokphone.setText(((Patient) person).getNextOfKin().getPhone().value);
        nokemail.setText(((Patient) person).getNextOfKin().getEmail().value);

        patientParticulars.setContent(particularsPane);
        tabManger.getTabs().set(0, patientParticulars);
    }

    /**
     * Populates record tab with details of a selected record
     * @param record that is being selected
     */
    public void loadRecordPage(Record record) {
        recorddoctor.setText(record.getDoctorName().fullName);
        recorddate.setText(record.getRecordDate().getDate());
        recorddescription.setText(record.getDescription().value);
        patientRecords.setContent(recordsPane);
        tabManger.getTabs().set(1, patientRecords);
    }

    /**
     * Loads the record tab and gives it a default label until a record is selected.
     * Should only run when goto command is ran successfully
     */
    public void loadRecordTab() {
        Label placeholder = new Label(" No record has been selected. ");
        placeholder.setStyle("-fx-text-fill: white");
        patientRecords.setContent(placeholder);
        if (tabManger.getTabs().size() > 1) {
            tabManger.getTabs().set(1, patientRecords);
        } else {
            tabManger.getTabs().add(patientRecords);
        }
        tabManger.getSelectionModel().select(patientRecords);
    }

    public void closeRecordTab() {
        tabManger.getTabs().remove(patientRecords);
    }


}
