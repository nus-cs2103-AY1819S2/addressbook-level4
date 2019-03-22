package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import javafx.beans.property.SimpleObjectProperty;

import seedu.address.model.person.Person;

import guitests.guihandles.MapPanelHandle;

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

        // TODO: Previously show MapUrl for person. Need to change to display details of Requests
    }
}
