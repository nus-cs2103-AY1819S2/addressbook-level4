package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.OrderItemListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import guitests.guihandles.TablesFlowPanelHandle;
import seedu.address.TestApp;

import seedu.address.logic.commands.AddTableCommand;
import seedu.address.logic.commands.ClearTablesCommand;
import seedu.address.logic.commands.EditPaxCommand;
import seedu.address.logic.commands.TableModeCommand;
import seedu.address.model.Model;
import seedu.address.model.RestOrRant;
import seedu.address.model.table.Table;
import seedu.address.testutil.TypicalRestOrRant;
import seedu.address.ui.CommandBox;
import seedu.address.ui.testutil.OrdersGuiTestAssert;

/**
 * A system test class for RestOrRant, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class RestOrRantSystemTest {

    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();

        testApp = setupHelper.setupApplication(this::getInitialData, getTablesFileLocation(), getOrdersFileLocation(),
                getMenuFileLocation(), getStatsFileLocation());

        mainWindowHandle = setupHelper.setupMainWindowHandle();
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the given files.
     */
    protected RestOrRant getInitialData() {
        return TypicalRestOrRant.getTypicalRestOrRant();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getTablesFileLocation() {
        return TestApp.TABLES_FOR_TESTING;
    }

    protected Path getOrdersFileLocation() {
        return TestApp.ORDERS_FOR_TESTING;
    }

    protected Path getMenuFileLocation() {
        return TestApp.MENU_FOR_TESTING;
    }

    protected Path getStatsFileLocation() {
        return TestApp.STATS_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public OrderItemListPanelHandle getOrderItemListPanel() {
        return mainWindowHandle.getOrderItemListPanel();
    }

    public TablesFlowPanelHandle getTablesFlowPanel() {
        return mainWindowHandle.getTableFlowPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);
    }

    /**
     * Deletes all tables in the restaurant.
     */
    protected void deleteAllTables() {
        clearOccupancy();
        executeCommand(ClearTablesCommand.COMMAND_WORD);
        assertEquals(0, getModel().getRestOrRant().getTables().getTableList().size());
    }

    /**
     * Clears the occupancy of all tables in the restaurant.
     */
    protected void clearOccupancy() {
        for (Table table : getModel().getRestOrRant().getTables().getTableList()) {
            executeCommand(EditPaxCommand.COMMAND_WORD + " " + table.getTableNumber() + " 0");
        }
        assertTrue(getModel().isRestaurantEmpty());
    }

    /**
     * Adds 8 tables and occupies some of them.
     */
    protected void occupyTables() {
        executeCommand(AddTableCommand.COMMAND_WORD + " 4 5 4 5 6 7 4 5");
        executeCommand(EditPaxCommand.COMMAND_WORD + " 1 4");
        executeCommand(EditPaxCommand.COMMAND_WORD + " 2 4");
        executeCommand(EditPaxCommand.COMMAND_WORD + " 3 4");
        executeCommand(EditPaxCommand.COMMAND_WORD + " 6 4");
        executeCommand(EditPaxCommand.COMMAND_WORD + " 8 4");
        assertFalse(getModel().isRestaurantEmpty());
    }

    /**
     * Switches over to Table Mode after occupying the specified table.
     */
    protected void goToTableMode(String tableNumber) {
        executeCommand(EditPaxCommand.COMMAND_WORD + " " + tableNumber + " 1");
        executeCommand(TableModeCommand.COMMAND_WORD + " " + tableNumber);
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same objects as {@code expectedModel}
     * and the panel displays the items in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new RestOrRant(expectedModel.getRestOrRant()), testApp.readStorageRestOrRant());
        OrdersGuiTestAssert.assertListMatching(getOrderItemListPanel(), expectedModel.getFilteredOrderItemList());
        // TODO: check if we need this for tables
        // TablesGuiTestAssert.assertListMatching(getTablesFlowPanel(), expectedModel.getFilteredTableList());
        // TODO: check for the other lists and panes too
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code OrderItemListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        statusBarFooterHandle.rememberCurrentMode();
        statusBarFooterHandle.rememberSyncStatus();
        getOrderItemListPanel().rememberSelectedOrderItemCard();
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isCurrentModeChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the current mode changed.
     */
    protected void assertStatusBarUnchangedExceptCurrentMode(String newMode) {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSyncStatusChanged());
        assertEquals(newMode, handle.getCurrentMode());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("Welcome to RestOrRant!", getResultDisplay().getText());
        OrdersGuiTestAssert.assertListMatching(getOrderItemListPanel(), getModel().getFilteredOrderItemList());
        // TODO: Check if we need this for tables
        // TablesGuiTestAssert.assertListMatching(getTablesFlowPanel(), getModel().getFilteredTableList());
        assertEquals("Restaurant Mode", getStatusBarFooter().getCurrentMode());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
