package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InfoPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.request.Request;

public class InfoPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Request> selectedRequest = new SimpleObjectProperty<>();
    private InfoPanel infoPanel;
    private InfoPanelHandle infoPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> infoPanel = new InfoPanel(selectedRequest));
        uiPartRule.setUiPart(infoPanel);

        infoPanelHandle = new InfoPanelHandle(infoPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(InfoPanel.DEFAULT_PAGE, infoPanelHandle.getLoadedUrl());

        // TODO: Previously show MapUrl for person. Need to change to display details of Requests
    }
}
