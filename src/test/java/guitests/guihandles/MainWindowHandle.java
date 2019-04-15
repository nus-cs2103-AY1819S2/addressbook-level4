package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final RestaurantListPanelHandle restaurantListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final ReviewListPanelHandle reviewListPanel;
    private final RestaurantSummaryPanelHandle restaurantSummaryPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        restaurantListPanel = new RestaurantListPanelHandle(getChildNode(RestaurantListPanelHandle
                .RESTAURANT_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        reviewListPanel = new ReviewListPanelHandle(getChildNode(ReviewListPanelHandle.REVIEW_LIST_VIEW_ID));
        restaurantSummaryPanel = new RestaurantSummaryPanelHandle(
                getChildNode(RestaurantSummaryPanelHandle.SUMMARY_PANE_ID));
    }

    public RestaurantListPanelHandle getRestaurantListPanel() {
        return restaurantListPanel;
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

    public ReviewListPanelHandle getReviewListPanel() {
        return reviewListPanel;
    }

    public RestaurantSummaryPanelHandle getRestaurantSummaryPanel() {
        return restaurantSummaryPanel;
    }
}
