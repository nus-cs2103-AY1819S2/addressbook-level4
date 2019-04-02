package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private CardListPanelHandle cardListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private BrowserPanelHandle browserPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
    }

    /**
     * Initialises the {@code cardListPanel} and {@code browserPanel}. The reason a separate method is necessary
     * is because these two elements will not be present on app initialisation, but only when a folder is entered.
     */
    public void initialiseCardListPanelHandle() {
        cardListPanel = new CardListPanelHandle(getChildNode(CardListPanelHandle.CARD_LIST_VIEW_ID));
        browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.CURRENT_CARD_QUESTION));
    }

    public CardListPanelHandle getCardListPanel() {
        return cardListPanel;
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
