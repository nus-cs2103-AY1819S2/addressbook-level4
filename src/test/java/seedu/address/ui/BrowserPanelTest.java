package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalRestaurants.ALICE;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.restaurant.Restaurant;

public class BrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Restaurant> selectedRestaurant = new SimpleObjectProperty<>();
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedRestaurant));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a restaurant
        guiRobot.interact(() -> selectedRestaurant.set(ALICE));
        URL expectedRestaurantUrl = new URL(selectedRestaurant.get().getWeblink().value + "/");

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedRestaurantUrl, browserPanelHandle.getLoadedUrl());
    }
}
