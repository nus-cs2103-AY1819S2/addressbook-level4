package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.InformationPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.MedicineListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.TestApp;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Inventory;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;
import seedu.address.testutil.TypicalMedicines;
import seedu.address.ui.BatchTable;
import seedu.address.ui.CommandBox;

/**
 * A system test class for MediTabs, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class MediTabsSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);
    private static final Comparator<Medicine> comparator = Comparator.naturalOrder();

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
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected Inventory getInitialData() {
        return TypicalMedicines.getTypicalInventory();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public MedicineListPanelHandle getMedicineListPanel() {
        return mainWindowHandle.getMedicineListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public InformationPanelHandle getInformationPanel() {
        return mainWindowHandle.getInformationPanel();
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
     * Displays all medicines in the inventory.
     */
    protected void showAllMedicines() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getInventory().getSortedMedicineList(comparator).size(),
                getModel().getFilteredMedicineList().size());
    }

    /**
     * Displays all medicines with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showMedicinesWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + PREFIX_NAME + " " + keyword);
        assertTrue(getModel().getFilteredMedicineList().size()
                < getModel().getInventory().getSortedMedicineList(comparator).size());
    }

    /**
     * Selects the medicine at {@code index} of the displayed list.
     */
    protected void selectMedicine(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getMedicineListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all medicines in the inventory.
     */
    protected void deleteAllMedicines() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getInventory().getSortedMedicineList(comparator).size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same medicine objects as {@code expectedModel}
     * and the medicine list panel displays the medicines in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new Inventory(expectedModel.getInventory()), testApp.readStorageInventory());
        assertListMatching(getMedicineListPanel(), expectedModel.getFilteredMedicineList());
    }

    /**
     * Calls {@code InformationPanelHandle}, {@code MedicineListPanelHandle} and {@code StatusBarFooterHandle} to
     * remember their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getInformationPanel().rememberLoadedTableDetails();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getMedicineListPanel().rememberSelectedMedicineCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the information panel is not displaying any
     * loaded table.
     * @see InformationPanelHandle#isLoadedTableDetailsChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getInformationPanel().isBatchTableLoaded());
        assertFalse(getMedicineListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the information panel is changed to display the details of the medicine in the medicine list panel
     * at {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see InformationPanelHandle#isLoadedTableDetailsChanged()
     * @see MedicineListPanelHandle#isSelectedMedicineCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getMedicineListPanel().navigateToCard(getMedicineListPanel().getSelectedCardIndex());
        List<String> expectedDetails = new ArrayList<>();
        expectedDetails.add(getMedicineListPanel().getHandleToSelectedCard().getName());
        expectedDetails.add(getMedicineListPanel().getHandleToSelectedCard().getCompany());
        expectedDetails.add(BatchTable.BATCHTABLE_FOOTER_QUANTITY
                + getMedicineListPanel().getHandleToSelectedCard().getQuantity());
        expectedDetails.add(BatchTable.BATCHTABLE_FOOTER_EXPIRY
                + getMedicineListPanel().getHandleToSelectedCard().getExpiry());

        assertEquals(expectedDetails, getInformationPanel().getTableDetails());
        assertEquals(expectedSelectedCardIndex.getZeroBased(), getMedicineListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the loaded information table and the selected card in the medicine list panel remain
     * unchanged.
     * @see InformationPanelHandle#isLoadedTableDetailsChanged
     * @see MedicineListPanelHandle#isSelectedMedicineCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getInformationPanel().isLoadedTableDetailsChanged());
        assertFalse(getMedicineListPanel().isSelectedMedicineCardChanged());
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
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that the loaded information page has the correct information.
     */
    protected void assertInformationPageIsCorrect() {
        InformationPanelHandle informationPanelHandle = getInformationPanel();
        assertTrue(informationPanelHandle.isBatchTableLoaded());
        informationPanelHandle.assertTableCorrect(testApp.getSelectedMedicine(),
                getModel().getInformationPanelSettings().getValue());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getMedicineListPanel(), getModel().getFilteredMedicineList());
        assertFalse(getInformationPanel().isBatchTableLoaded());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
