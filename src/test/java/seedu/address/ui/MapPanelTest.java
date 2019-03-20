package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPatients.ALICE;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.MapPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;

public class MapPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Person> selectedPatient = new SimpleObjectProperty<>();
    private MapPanel mapPanel;
    private MapPanelHandle mapPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> mapPanel = new MapPanel(selectedPatient));
        uiPartRule.setUiPart(mapPanel);

        mapPanelHandle = new MapPanelHandle(mapPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(MapPanel.DEFAULT_PAGE, mapPanelHandle.getLoadedUrl());

        // associated web p
        // age of a person
        guiRobot.interact(() -> selectedPatient.set(ALICE));
        Address address = ALICE.getAddress();
        String mapAddress = address.toStreetNameOnly().replaceAll("\\s", "%20");
        URL expectedPersonUrl = new URL(MapPanel.MAP_URL + mapAddress + "%22&zoom=16&size=640x395&markers=%22"
                + mapAddress + ",red&sensor=false");

        waitUntilBrowserLoaded(mapPanelHandle);
        assertEquals(expectedPersonUrl, mapPanelHandle.getLoadedUrl());
    }
}
