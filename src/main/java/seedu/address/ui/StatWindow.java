package seedu.address.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
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
    private TeethPanel teethPanel;

    @FXML
    private GridPane reportPlaceholder;
    @FXML
    private Label patientTitle;
    @FXML
    private Label nric;
    @FXML
    private Label sex;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label kinRelation;
    @FXML
    private Label kinPhone;
    @FXML
    private Label kinAddress;
    @FXML
    private Label kinName;
    @FXML
    private TableView recordStatTable;
    @FXML
    private VBox barChartBox;
    @FXML
    private VBox pieChartBox;
    @FXML
    private StackPane teethBox;
    @FXML
    private VBox teethHolder;


    public StatWindow(Stage root, Logic logic) {
        super(FXML, root);

        this.primaryStage = root;
        this.logic = logic;

        setWindowDefaultSize(this.logic.getGuiSettings());
    }

    public void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(720.0);
        primaryStage.setWidth(860.0);

        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
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
        populatePatientInfo();

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
        SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
        selectedPerson.setValue(toStat);
        ObservableValue<Person> localPatient = selectedPerson;


        this.teethPanel = new TeethPanel(localPatient, true, 280.0);
        // teethPanel.getRoot().setSnapToPixel(true);

        teethBox.setAlignment(Pos.TOP_CENTER);

        teethHolder.getChildren().add(teethPanel.getRoot());


        populateStatTable();
        populateBarChart();
        populatePieChart();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Populates patient info
     */
    private void populatePatientInfo() {
        Patient statPatient = StatWindow.toStat;
        this.patientTitle.setText("Statistics Report for " + statPatient.getName().toString());
        patientTitle.setWrapText(true);

        sex.setText(statPatient.getSex().getSex() + "    ");
        nric.setText(statPatient.getNric().toString() + "    ");
        dateOfBirth.setText(statPatient.getDateOfBirth().getDate());
        phone.setText(statPatient.getPhone().toString());
        address.setText(statPatient.getAddress().toString());
        address.setWrapText(true);
        email.setText(statPatient.getEmail().toString());
        email.setWrapText(true);

        kinName.setText(statPatient.getNextOfKin().getName().toString());
        kinName.setWrapText(true);
        kinRelation.setText(statPatient.getNextOfKin().getKinRelation().toString());
        kinAddress.setText(statPatient.getNextOfKin().getAddress().toString());
        kinAddress.setWrapText(true);
        kinPhone.setText(statPatient.getNextOfKin().getPhone().toString());
    }

    /**
     * Populates stats table.
     */
    private void populateStatTable() {
        ObservableList<Pair<String, Integer>> data =
            FXCollections.observableArrayList();

        for (String procType: Procedure.PROCEDURE_LIST) {
            data.add(new Pair<>(procType, recordNumbers.get(procType)));
        }


        TableColumn<Pair<String, Integer>, String> procCol = new TableColumn<>("Procedure");
        procCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey()));
        procCol.prefWidthProperty().bind(recordStatTable.widthProperty().multiply(0.5));

        TableColumn<Pair<String, Integer>, Integer> numCol = new TableColumn<>("Number");
        numCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue()));
        numCol.prefWidthProperty().bind(recordStatTable.widthProperty().multiply(0.5));

        recordStatTable.getColumns().addAll(procCol, numCol);
        recordStatTable.getItems().addAll(data);
        //recordStatTable.setMinHeight(200);

    }

    /**
     * Populates the bar chart
     */
    private void populateBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> recordBarChart = new BarChart<String, Number>(xAxis, yAxis);
        xAxis.setLabel("Procedure");
        xAxis.setTickLabelFill(Color.WHITE);
        yAxis.setLabel("Count");
        yAxis.setTickLabelFill(Color.WHITE);

        XYChart.Series series = new XYChart.Series();
        series.setName("Overall Stat");
        for (String procType: Procedure.PROCEDURE_LIST) {
            series.getData().add(new XYChart.Data(procType, recordNumbers.get(procType)));
        }
        recordBarChart.getData().add(series);

        barChartBox.getChildren().add(recordBarChart);
    }

    /**
     * Populates the pie chart
     */
    private void populatePieChart() {
        PieChart pieChart = new PieChart();

        for (String procType: Procedure.PROCEDURE_LIST) {
            int tempValue = recordNumbers.get(procType);
            if (tempValue != 0) {
                pieChart.getData().add(new PieChart.Data(procType, tempValue));
            }
        }
        pieChart.setStyle("-fx-text-fill: white");
        pieChartBox.getChildren().add(pieChart);
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
