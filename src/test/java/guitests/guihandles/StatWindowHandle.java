package guitests.guihandles;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Handler for StatWindow
 */
public class StatWindowHandle extends StageHandle {
    private Stage statStage;

    public StatWindowHandle(Stage statWindowStage) {
        super(statWindowStage);
        statStage = statWindowStage;
    }

    private void closeStatWindowExternally() {
        guiRobot.interact(() -> statStage.fireEvent(new WindowEvent(statStage, WindowEvent.WINDOW_CLOSE_REQUEST)));
    }

}
