package seedu.finance.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.finance.testutil.TypicalRecords.ALICE;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Record;

public class BrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Record> selectedRecord = new SimpleObjectProperty<>();
    private BrowserPanel browserPanel;
    private ObjectProperty<Amount> budgetData = new SimpleObjectProperty<>();
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedRecord, budgetData));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // associated web page of a record
        guiRobot.interact(() -> selectedRecord.set(ALICE));
        URL expectedRecordUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + ALICE.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedRecordUrl, browserPanelHandle.getLoadedUrl());
    }
}
