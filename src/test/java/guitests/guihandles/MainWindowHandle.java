package guitests.guihandles;

import javafx.stage.Stage;
import seedu.address.ui.HealthWorkerListPanel;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final MainMenuHandle mainMenu;
    private final CommandBoxHandle commandBox;
    private final ResultDisplayHandle resultDisplay;
    private final RequestListPanelHandle requestListPanel;
    private final InfoPanelHandle infoPanel;
    private final HealthWorkerListPanelHandle healthWorkerListPanel;
    private final StatusBarFooterHandle statusBarFooter;

    public MainWindowHandle(Stage stage) {
        super(stage);

        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        requestListPanel = new RequestListPanelHandle(getChildNode(RequestListPanelHandle.REQUEST_LIST_VIEW_ID));
        infoPanel = new InfoPanelHandle(getChildNode(InfoPanelHandle.WEB_VIEW_ID));
        healthWorkerListPanel = new HealthWorkerListPanelHandle(getChildNode(
                HealthWorkerListPanelHandle.HEALTH_WORKER_LIST_VIEW_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public RequestListPanelHandle getRequestListPanel() { return requestListPanel; }

    public InfoPanelHandle getInfoPanel() {
        return infoPanel;
    }

    public HealthWorkerListPanelHandle getHealthWorkerListPanel() { return healthWorkerListPanel; }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }
}
