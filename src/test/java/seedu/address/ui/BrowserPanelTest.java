package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.BrowserPanelHandle;

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
}
