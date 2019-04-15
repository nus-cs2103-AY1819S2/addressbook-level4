package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code CalendarWindow} in the GUI.
 */
public class CalendarWindowHandle extends StageHandle {

    public static final String CALENDAR_WINDOW_TITLE = "Task Calendar";

    private static final String CALENDAR_WINDOW_ID = "#calendar";

    public CalendarWindowHandle(Stage calendarStage) {
        super(calendarStage);
    }

    /**
     * Returns true if a task calendar is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(CALENDAR_WINDOW_TITLE);
    }

    public static boolean focused() {
        return new GuiRobot().getStage(CALENDAR_WINDOW_TITLE).isFocused();
    }

}
