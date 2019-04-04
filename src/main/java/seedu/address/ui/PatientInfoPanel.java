package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;

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

