package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.Procedure;
import seedu.address.model.record.Record;

/**
 * Controller for a stat window page.
 */
public class StatWindow extends UiPart<Stage> {

    //TODO: Proper statistic report formatting and data
    static final String FXML = "StatWindow.fxml";
    private static Patient toStat;
    private final Map<String, Integer> recordNumbers = new HashMap<>();

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    @FXML
    private GridPane reportPlaceholder;
    @FXML
    private Label patientTitle;
    @FXML
    private Label nric;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private TableView recordStatTable;


    public StatWindow(Stage root, Logic logic) {
        super(FXML, root);

        this.primaryStage = root;
        this.logic = logic;

        setWindowDefaultSize(this.logic.getGuiSettings());
    }

    public void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());

        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX() + 50);
            primaryStage.setY(guiSettings.getWindowCoordinates().getY() - 50);
        }
    }

    public static void setStatPatient(Patient patient) {
        toStat = patient;
    }

    /**
     * populates a statWindow with the relevant data
     */
    public void populateData() {
        Patient statPatient = StatWindow.toStat;
        this.patientTitle.setText("Statistics Report for " + statPatient.getName().toString());
        nric.setText(statPatient.getNric().toString());
        dateOfBirth.setText(statPatient.getDateOfBirth().getDate());
        phone.setText(statPatient.getPhone().toString());
        address.setText(statPatient.getAddress().toString());
        email.setText(statPatient.getEmail().toString());

        String[] procList = Procedure.PROCEDURE_LIST;
        for (String procType: procList) {
            recordNumbers.put(procType, 0);
        }

        List<Record> recordList = statPatient.getRecords();

        for (Record rec: recordList) {
            String tempProc = rec.getProcedure().getProcedureType().toLowerCase();
            Integer count = recordNumbers.get(tempProc);
            recordNumbers.put(tempProc, count + 1);
        }
        populateStatTable();

    }

    void show() {
        primaryStage.show();
    }

    private void populateStatTable() {
        ObservableList<Pair<String, Integer>> data =
            FXCollections.observableArrayList();

        for (String procType: Procedure.PROCEDURE_LIST) {
            data.add(new Pair<>(procType, recordNumbers.get(procType)));
        }


        TableColumn<Pair<String, Integer>, String> procCol = new TableColumn<>("Procedure");
        procCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey()));

        TableColumn<Pair<String, Integer>, Integer> numCol = new TableColumn<>("Number");
        numCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue()));

        recordStatTable.getColumns().addAll(procCol, numCol);
        recordStatTable.getItems().addAll(data);
        //recordStatTable.setMinHeight(200);

    }

    /**
     * Returns true if a stat window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    void close() {
        primaryStage.close();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
            (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }
}
