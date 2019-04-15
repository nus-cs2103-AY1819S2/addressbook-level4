package seedu.finance.ui;

import org.junit.Before;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.budget.TotalBudget;
import seedu.finance.model.record.Record;

public class BrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Record> selectedRecord = new SimpleObjectProperty<>();
    private BrowserPanel browserPanel;
    private Budget budgetData = new TotalBudget();
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedRecord, budgetData));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    // TODO: Failed Test; need to update
    /*
    @Test
    public void display() throws Exception {
        // associated web page of a record
        guiRobot.interact(() -> selectedRecord.set(APPLE));
        URL expectedRecordUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + APPLE.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedRecordUrl, browserPanelHandle.getLoadedUrl());
    }*/
}
