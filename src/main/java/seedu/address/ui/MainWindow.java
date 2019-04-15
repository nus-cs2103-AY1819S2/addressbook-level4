package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.statistics.exceptions.BillNotFoundException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String TABLE_MODE_THEME = "view/TableModeTheme.css";
    private static final String RESTAURANT_MODE_THEME = "view/RestaurantModeTheme.css";
    private static final String MENU_MODE_THEME = "view/MenuModeTheme.css";
    private static final String BILL_MODE_THEME = "view/BillModeTheme.css";
    private static final String STATISTIC_MODE_THEME = "view/StatisticsModeTheme.css";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private MenuListPanel menuListPanel;
    private OrderItemListPanel orderItemListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private BillPanel billPanel;
    private TableFlowPanel tableFlowPanel;
    private MenuItemFlowPanel menuItemFlowPanel;
    private StatisticsFlowPanel statisticsFlowPanel;
    private PopularMenuListPanel popularMenuListPanel;
    private StatusBarFooter statusBarFooter;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private ScrollPane scrollPane;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void changeTheme(String newTheme) {
        primaryStage.getScene().getStylesheets().set(0, newTheme);
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        tableFlowPanel = new TableFlowPanel(logic.getRestOrRant().getTables().getTableList(), scrollPane);
        browserPlaceholder.getChildren().addAll(tableFlowPanel.getRoot());

        orderItemListPanel = new OrderItemListPanel(logic.getFilteredOrderItemList(), logic.selectedOrderItemProperty(),
                logic::setSelectedOrderItem);
        listPanelPlaceholder.getChildren().add(orderItemListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        statusBarFooter = new StatusBarFooter("Restaurant Mode");
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic.getHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        resultDisplay.setFeedbackToUser("Welcome to RestOrRant!");
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings =
                new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(), (int) primaryStage.getX(),
                        (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public MenuListPanel getMenuListPanel() {
        return menuListPanel;
    }

    /**
     * Changes application mode.
     */
    @FXML
    private void handleChangeMode(Mode mode, boolean isDaily, boolean isMonthly, boolean isYearly) {
        requireNonNull(mode);
        browserPlaceholder.getChildren().clear();
        listPanelPlaceholder.getChildren().clear();

        switch (mode) {

        case RESTAURANT_MODE:
            tableFlowPanel = new TableFlowPanel(logic.getFilteredTableList(), scrollPane);
            browserPlaceholder.getChildren().add(tableFlowPanel.getRoot());

            orderItemListPanel =
                    new OrderItemListPanel(logic.getFilteredOrderItemList(), logic.selectedOrderItemProperty(),
                            logic::setSelectedOrderItem);
            listPanelPlaceholder.getChildren().add(orderItemListPanel.getRoot());

            statusBarFooter.updateMode("Restaurant Mode");
            changeTheme(RESTAURANT_MODE_THEME);
            break;

        case TABLE_MODE:
            menuItemFlowPanel = new MenuItemFlowPanel(logic.getFilteredMenuItemList(), scrollPane);
            browserPlaceholder.getChildren().add(menuItemFlowPanel.getRoot());

            orderItemListPanel =
                    new OrderItemListPanel(logic.getFilteredOrderItemList(), logic.selectedOrderItemProperty(),
                            logic::setSelectedOrderItem);
            listPanelPlaceholder.getChildren().add(orderItemListPanel.getRoot());

            statusBarFooter.updateMode(
                    "Table Mode: Table " + logic.selectedTableProperty().getValue().getTableNumber().toString());

            changeTheme(TABLE_MODE_THEME);
            break;

        case MENU_MODE:
            // TODO: change to browser panel to app logo

            menuListPanel = new MenuListPanel(logic.getFilteredMenuItemList(), logic.selectedMenuItemProperty(),
                    logic::setSelectedMenuItem);
            listPanelPlaceholder.getChildren().add(menuListPanel.getRoot());

            statusBarFooter.updateMode("Menu Mode");
            changeTheme(MENU_MODE_THEME);
            break;

        case BILL_MODE:
            if (logic.getRecentBill().equals(null)) {
                throw new BillNotFoundException();
            }
            billPanel = new BillPanel(logic.getRecentBill());
            browserPlaceholder.getChildren().add(billPanel.getRoot());

            statusBarFooter.updateMode("Table Mode");
            changeTheme(BILL_MODE_THEME);
            break;

        case STATISTICS_MODE:
            popularMenuListPanel = new PopularMenuListPanel(logic.getFilteredSortedMenuItemList(),
                    logic.selectedMenuItemProperty(), logic::setSelectedMenuItem);
            listPanelPlaceholder.getChildren().add(popularMenuListPanel.getRoot());

            if (isYearly) {
                statisticsFlowPanel =
                        new StatisticsFlowPanel(logic.getFilteredRevenueList(), scrollPane, false, false, true);
                browserPlaceholder.getChildren().add(statisticsFlowPanel.getRoot());
            } else if (isMonthly) {
                statisticsFlowPanel =
                        new StatisticsFlowPanel(logic.getFilteredRevenueList(), scrollPane, false, true, false);
                browserPlaceholder.getChildren().add(statisticsFlowPanel.getRoot());
            } else {
                statisticsFlowPanel =
                        new StatisticsFlowPanel(logic.getFilteredRevenueList(), scrollPane, true, false, false);
                browserPlaceholder.getChildren().add(statisticsFlowPanel.getRoot());
            }

            statusBarFooter.updateMode("Statistics Mode");
            changeTheme(STATISTIC_MODE_THEME);
            break;

        default:
            break;
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            Mode newMode = commandResult.newModeStatus();
            boolean isDaily = commandResult.isDaily();
            boolean isMonthly = commandResult.isMonthly();
            boolean isYearly = commandResult.isYearly();

            if (newMode != null) {
                handleChangeMode(newMode, isDaily, isMonthly, isYearly);
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
