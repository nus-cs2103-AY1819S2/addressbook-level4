package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.model.MapGrid;

public class StatusBarFooterTest extends GuiUnitTest {

    private static final Path STUB_SAVE_LOCATION = Paths.get("Stub");
    private static final Path RELATIVE_PATH = Paths.get(".");

    private static final Clock injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    private StatusBarFooterHandle statusBarFooterHandle;
    private final MapGrid mapGrid = new MapGrid();

    @BeforeClass
    public static void setUpBeforeClass() {
    // EDIT FOR TESTING
    }

    @AfterClass
    public static void tearDownAfterClass() {
    // EDIT FOR TESTING
    }

    @Before
    public void setUp() {
        StatusBarFooter statusBarFooter = new StatusBarFooter();
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // after address book is updated
        //guiRobot.interact(() -> mapGrid.addPerson(ALICE));
        assertStatusBarContent();
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, and the
     * sync status matches that of {@code expectedSyncStatus}.
     */
    private void assertStatusBarContent() {

        StatusBarFooter statusBarFooter1 = new StatusBarFooter();
        StatusBarFooter statusBarFooter2 = new StatusBarFooter();

        // create 2 status bar and compare the timing
        assertEquals(statusBarFooter1.getElapsedTime(System.nanoTime()),
                   statusBarFooter2.getElapsedTime(System.nanoTime()));
        guiRobot.pauseForHuman();
    }

}
