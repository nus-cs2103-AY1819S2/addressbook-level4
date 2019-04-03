package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final OrderItemListPanelHandle orderItemListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    //    private final BrowserPanelHandle browserPanelHandle;
    //    private final BillBrowserPanelHandle billBrowserPanel;
    private final TablesFlowPanelHandle tableFlowPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        orderItemListPanel =
                new OrderItemListPanelHandle(getChildNode(OrderItemListPanelHandle.ORDER_ITEM_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        //        billBrowserPanel = new BillBrowserPanelHandle(getChildNode(BillBrowserPanelHandle.BILL_BROWSER_ID));
        tableFlowPanel = new TablesFlowPanelHandle(getChildNode(TablesFlowPanelHandle.TABLE_FLOW_PANEL_ID));
    }

    public OrderItemListPanelHandle getOrderItemListPanel() {
        return orderItemListPanel;
    }

    public TablesFlowPanelHandle getTableFlowPanel() {
        return tableFlowPanel;
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

    //    public BillBrowserPanelHandle getBillBrowserPanel() {
    //        return billBrowserPanel;
    //    }
}
