package seedu.knowitall.ui;

import static org.junit.Assert.assertEquals;
import static seedu.knowitall.ui.StatusBarFooter.STATUS_IN_FOLDER;
import static seedu.knowitall.ui.StatusBarFooter.STATUS_IN_HOME_DIRECTORY;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;

public class StatusBarFooterTest extends GuiUnitTest {

    private StatusBarFooterHandle statusBarFooterHandle;
    private StatusBarFooter statusBarFooter;

    @Before
    public void setUp() {
        statusBarFooter = new StatusBarFooter();
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(STATUS_IN_HOME_DIRECTORY);

        // new status received
        guiRobot.interact(() -> statusBarFooter.updateStatusBarInFolder());
        assertStatusBarContent(STATUS_IN_FOLDER);
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}.
     */
    private void assertStatusBarContent(String expectedSaveLocation) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getCurrentStatus());
        guiRobot.pauseForHuman();
    }

}
