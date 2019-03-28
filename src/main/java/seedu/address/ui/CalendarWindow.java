package seedu.address.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 *  Task Calendar Window. Provides a graphical interface for task viewing
 *  with a built in Calendar element for the user. Elements are automatically
 *  added in on construction
 */
public class CalendarWindow extends UiPart<Stage> {

    private static final String FXML = "CalendarWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private TaskListPanel taskListPanel;
    private TaskCalendar taskCalendarPanel;
    private DatePicker datePicker;

    @FXML
    private StackPane taskListPanelPlaceholder;
    @FXML
    private StackPane calendarPanel;
    @FXML
    private StackPane commandBoxPlaceholder;


    public CalendarWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
        this.logic = logic;

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanel.setForCalender();
        taskListPanel.getRoot().getStylesheets().addAll("view/Calendar.css", "view/Extensions.css");
        taskListPanelPlaceholder.getStylesheets().addAll("view/Calendar.css", "view/Extensions.css");
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        VBox vb = new VBox();
        CommandBox commandBox = new CommandBox(this::executeCommand, this.logic.getHistory(), true);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        this.calendarPanel = new StackPane();
        LocalDate localDate = LocalDate.now();
        createCalender(localDate);
        vb.getChildren().addAll(calendarPanel, commandBoxPlaceholder);
        SplitPane sp = new SplitPane();
        sp.getItems().addAll(taskListPanelPlaceholder, vb);

        Scene scene = new Scene(sp);
        this.primaryStage.setScene(scene);
        scene.getStylesheets().add("view/Calendar.css");
        this.primaryStage.setTitle("Task Calendar");
        this.primaryStage.show();
    }

    /**
     *
     * @param commandText
     * @return
     * @throws CommandException
     * @throws ParseException
     */
    private CommandResult executeCommand(String commandText) {
        this.datePicker.setValue(LocalDate.now());
        System.out.println(commandText);
        CommandResult cr = new CommandResult("test");
        return cr;
    }

    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.close();
    }

    void show() {
        primaryStage.show();
    }

    public void createCalender(LocalDate localDate) {

        this.datePicker = new DatePicker(localDate);
        datePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);

                        } else {

                            StackPane cell_pane = new StackPane();
                            Circle circle = new Circle(15);

                            Label label = new Label();
                            label.setText(getText());
                            label.setTextFill(Color.WHITE);
                            label.setLabelFor(circle);
                            label.setStyle("-fx-font-weight: bold; -fx-border-color: transparent");
                            label.setFont(Font.font("Helvetica", 15));

                            if (item.getDayOfMonth() == 3 || item.getDayOfMonth() == 19) {
                                circle.setFill(Color.web("#FF0000"));
                            }
                            else {
                                circle.setFill(Color.TRANSPARENT);
                            }
                            if (item.getDayOfWeek().getValue() == 7 || item.getDayOfWeek().getValue() == 6) {
                                label.setTextFill(Color.web("#29F6E8"));
                            }
                            cell_pane.getChildren().addAll(circle, label);
                            cell_pane.setPrefSize(50, 50);
                            setGraphic(cell_pane);
                            setText("");
                        }
                    }
                };
            }
        });
        DatePickerSkin datepopup = new DatePickerSkin(datePicker);
        Node out = datepopup.getPopupContent();
        for (Node node : out.lookupAll(".day-name-cell")) {
            if (node.toString().contains("Sun") || node.toString().contains("Sat")) {
                node.setStyle("-fx-text-fill: #29F6E8");
            }
        }
        for (Node node : out.lookupAll(".button")) {
            node.setFocusTraversable(false);
        }
        for (Node node : out.lookupAll(".day-cell")) {
            node.setOnMouseClicked(event -> {
                DateCell dateCell = (DateCell) node;
                System.out.println("You clicked: " + dateCell.getItem().toString());
            });
        }
        this.calendarPanel.getChildren().addAll(out);
    }

}
