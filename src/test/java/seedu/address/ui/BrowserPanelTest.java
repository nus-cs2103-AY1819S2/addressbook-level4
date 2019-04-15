package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.BrowserPanelHandle;
import javafx.stage.Stage;

public class BrowserPanelTest extends GuiUnitTest {
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        FxToolkit.registerStage(browserPanel::getRoot);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        //assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a restaurant
        FxToolkit.showStage();
        guiRobot.interact(() -> browserPanel.loadPage("https://www.kfc.com.sg"));
        URL expectedRestaurantUrl = new URL("https://www.kfc.com.sg/");

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedRestaurantUrl, browserPanelHandle.getLoadedUrl());
    }

    @Test
    public void isShowing_browserPanelIsShowing_returnsTrue() {
        guiRobot.interact(browserPanel::show);
        assertTrue(browserPanel.isShowing());
    }

    @Test
    public void isShowing_browserPanelIsHiding_returnsFalse() {
        assertFalse(browserPanel.isShowing());
    }

    @Test
    public void focus_browserPanelNotFocused_focused() throws Exception {
        // TODO: This test skip can be removed once this bug is fixed:
        // https://github.com/javafxports/openjdk-jfx/issues/50
        //
        // When there are two stages (stage1 and stage2) shown,
        // stage1 is in focus and stage2.requestFocus() is called,
        // we expect that stage1.isFocused() will return false while
        // stage2.isFocused() returns true. However, as reported in the bug report,
        // both stage1.isFocused() and stage2.isFocused() returns true,
        // which fails the test.
        assumeFalse("Test skipped in headless mode: Window focus behavior is buggy.", guiRobot.isHeadlessMode());
        guiRobot.interact(browserPanel::show);

        // Focus on another stage to remove focus from the browserPanel
        guiRobot.interact(() -> {
            Stage temporaryStage = new Stage();
            temporaryStage.show();
            temporaryStage.requestFocus();
        });
        assertFalse(browserPanel.getRoot().isFocused());

        guiRobot.interact(browserPanel::focus);
        assertTrue(browserPanel.getRoot().isFocused());
    }

}
