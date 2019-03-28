package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InfoPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.request.Request;
import seedu.address.testutil.RequestBuilder;

/**
 * This class tests for any sort of standard operations done to the UI component,
 * Info Panel.
 * @author Hui Chun
 */
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

        // checks if loaded content is not empty, after loading sample request details
        Request sampleRequest = new RequestBuilder().build();
        String html = infoPanel.generateHtml(sampleRequest);
        infoPanel.loadContent(html);
        do {
            Thread.sleep(500);
        } while(!infoPanelHandle.isLoaded());
        String loadedContent = infoPanelHandle.getLoadedContent();
        assertTrue(loadedContent != "");

    }
}
