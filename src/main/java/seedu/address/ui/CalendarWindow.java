package seedu.address.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    }

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
    }

}
