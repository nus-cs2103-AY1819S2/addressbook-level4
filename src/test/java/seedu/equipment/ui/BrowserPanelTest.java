package seedu.equipment.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;

import java.net.URL;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.tag.Tag;

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
        guiRobot.interact(() -> selectedPerson.set(ANCHORVALECC));
        String url = BrowserPanel.MAP_PAGE_BASE_URL;
        URL expectedUrl;
        try {

            double[] coordiantes = ANCHORVALECC.getCoordiantes();
            if (coordiantes != null) {
                url = BrowserPanel.MAP_PAGE_BASE_URL + "?coordinates=[[" + coordiantes[0] + ","
                        + coordiantes[1] + "]]&name=[\"" + ANCHORVALECC.getName()
                        + "\"]&address=[\"" + ANCHORVALECC.getAddress() + "\"]&phone=[\""
                        + ANCHORVALECC.getPhone() + "\"]&serial=[\""
                        + ANCHORVALECC.getSerialNumber() + "\"]&date=[\""
                        + ANCHORVALECC.getDate().toString() + "\"]&tags=[[";
                Set<Tag> tags = ANCHORVALECC.getTags();
                int count = 0;
                for (Tag tag:tags) {
                    url += "\"" + tag.getTagName() + "\"";
                    count++;
                    if (count < tags.size()) {
                        url += ",";
                    }
                }
                url += "]]";
            }
        } catch (Exception e) {
            fail("No exception should be raised. But " + e.getMessage() + " is raised.");
        } finally {
            expectedUrl = new URL(url.replace("\"", "%22").replace(" ", "%20"));
        }
        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedUrl, browserPanelHandle.getLoadedUrl());
    }
}
