package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalEquipments.ACHORVALECC;

import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.equipment.Equipment;

public class BrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Equipment> selectedPerson = new SimpleObjectProperty<>();
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedPerson));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a equipment
        guiRobot.interact(() -> selectedPerson.set(ACHORVALECC));
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBQ5YiOpupDO8JnZqmqYTujAwP9U4R5JBA")
                .build();
        String expectedUrlString = BrowserPanel.MAP_PAGE_BASE_URL;
        URL expectedUrl;
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    ACHORVALECC.getAddress().toString()).await();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if (results.length > 0) {
                System.out.println();
                expectedUrlString = BrowserPanel.MAP_PAGE_BASE_URL + "?coordinates=[["
                        + results[0].geometry.location.lng + ","
                        + results[0].geometry.location.lat + "]]&title=[\""
                        + ACHORVALECC.getName()
                        + "\"]&icon=[\"monument\"]";
            }
        } catch (ApiException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            expectedUrl = new URL(expectedUrlString.replace("\"", "%22").replace(" ", "%20"));
        }
        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedUrl, browserPanelHandle.getLoadedUrl());
    }
}
