package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;

import guitests.guihandles.BookBrowserPanelHandle;
import guitests.guihandles.BookListPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.TestApp;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ListBookCommand;
import seedu.address.logic.commands.ListReviewCommand;
import seedu.address.model.BookShelf;
import seedu.address.model.Model;
import seedu.address.testutil.TypicalBooks;
import seedu.address.ui.CommandBox;

/**
 * A system test class for BookShelf, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class BookShelfSystemTest {
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
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        waitUntilBrowserLoaded(getBrowserPanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected BookShelf getInitialData() {
        return TypicalBooks.getTypicalBookShelf();
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

    public BookListPanelHandle getBookListPanel() {
        return mainWindowHandle.getBookListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public BookBrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
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

        waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Displays all books in the book shelf.
     */
    protected void showAllBooks() {
        executeCommand(ListBookCommand.COMMAND_WORD);
        assertEquals(getModel().getBookShelf().getBookList().size(), getModel().getFilteredBookList().size());
    }

    /**
     * Displays all books with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showBooksWithName(String keyword) {
        executeCommand(ListBookCommand.COMMAND_WORD + " " + PREFIX_NAME + keyword);
        assertTrue(getModel().getFilteredBookList().size() < getModel().getBookShelf().getBookList().size());
    }

    /**
     * Selects the book at {@code index} of the displayed list.
     */
    protected void selectBook(Index index) {
        executeCommand(ListReviewCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getBookListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all books in the book shelf.
     */
    protected void deleteAllBooks() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getBookShelf().getBookList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same book objects as {@code expectedModel}
     * and the book list panel displays the books in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new BookShelf(expectedModel.getBookShelf()), testApp.readStorageBookShelf());
        assertListMatching(getBookListPanel(), expectedModel.getFilteredBookList());
    }

    /**
     * Calls {@code BookBrowserPanelHandle}, {@code BookListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        // getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getBookListPanel().rememberSelectedBookCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url is now displaying the
     * default page.
     * @see BookBrowserPanelHandle#isUrlChanged()
     */
    @Ignore
    protected void assertSelectedBookCardDeselected() {
        assertFalse(getBookListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the book in the book list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BookBrowserPanelHandle#isUrlChanged()
     * @see BookListPanelHandle#isSelectedBookCardChanged()
     */
    @Ignore
    protected void assertSelectedBookCardChanged(Index expectedSelectedCardIndex) {
        getBookListPanel().navigateToCard(getBookListPanel().getSelectedCardIndex());
        String selectedCardName = getBookListPanel().getHandleToSelectedCard().getName();

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getBookListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the book list panel remain unchanged.
     * @see BookBrowserPanelHandle#isUrlChanged()
     * @see BookListPanelHandle#isSelectedBookCardChanged()
     */
    protected void assertSelectedBookCardUnchanged() {
        assertFalse(getBookListPanel().isSelectedBookCardChanged());
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
    @Ignore
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getBookListPanel(), getModel().getFilteredBookList());
        // assertEquals(ReviewBrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
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
