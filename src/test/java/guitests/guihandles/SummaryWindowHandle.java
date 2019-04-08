package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class SummaryWindowHandle extends StageHandle {

    public static final String SUMMARY_WINDOW_TITLE = "Summary";

    public SummaryWindowHandle(Stage statsWindowStage) {
        super(statsWindowStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(SUMMARY_WINDOW_TITLE);
    }
}
