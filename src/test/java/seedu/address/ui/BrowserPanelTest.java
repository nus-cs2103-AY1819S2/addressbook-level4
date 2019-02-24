package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCards.ALICE;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.card.Card;

public class BrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Card> selectedCard = new SimpleObjectProperty<>();
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedCard));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a card
        guiRobot.interact(() -> selectedCard.set(ALICE));
        URL expectedCardUrl = new URL(
                BrowserPanel.SEARCH_PAGE_URL + ALICE.getQuestion().fullQuestion.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedCardUrl, browserPanelHandle.getLoadedUrl());
    }
}
