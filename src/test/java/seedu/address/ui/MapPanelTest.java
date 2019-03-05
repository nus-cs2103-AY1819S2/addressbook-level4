package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.MapPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.Person;

public class MapPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
    private MapPanel mapPanel;
    private MapPanelHandle mapPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> mapPanel = new MapPanel(selectedPerson));
        uiPartRule.setUiPart(mapPanel);

        mapPanelHandle = new MapPanelHandle(mapPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, mapPanelHandle.getLoadedUrl());

        // associated web page of a person
        guiRobot.interact(() -> selectedPerson.set(ALICE));
        //Before: URL expectedPersonUrl = new URL(BrowserPanel.SEARCH_PAGE_URL +
        //ALICE.getName().fullName.replaceAll(" ", "%20"));

        URL expectedPersonUrl = new URL(MapPanel.GOOGLE_MAPS_URL);
        waitUntilBrowserLoaded(mapPanelHandle);
        assertEquals(expectedPersonUrl, mapPanelHandle.getLoadedUrl());
    }
}
