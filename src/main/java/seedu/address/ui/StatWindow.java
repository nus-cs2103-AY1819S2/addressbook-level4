package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * Controller for a stat window page.
 */
public class StatWindow extends UiPart<Stage> {

    //TODO: Proper statistic report formatting and data
    static final String FXML = "StatWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

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

    void show() {
        primaryStage.show();
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
