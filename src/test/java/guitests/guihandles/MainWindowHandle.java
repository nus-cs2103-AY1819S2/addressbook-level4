package guitests.guihandles;

import static seedu.finance.testutil.EventsUtil.postNow;

import javafx.stage.Stage;
import seedu.finance.commons.events.SwapBrowserPanelEvent;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final RecordListPanelHandle recordListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final BrowserPanelHandle browserPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        recordListPanel = new RecordListPanelHandle(getChildNode(RecordListPanelHandle.RECORD_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
        postNow(new SwapBrowserPanelEvent(SwapBrowserPanelEvent.PanelType.SUMMARY));
    }

    public RecordListPanelHandle getRecordListPanel() {
        return recordListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }

}
