package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.Person;

public class BrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
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

        // associated web page of a person
        guiRobot.interact(() -> selectedPerson.set(ALICE));
        URL expectedPersonUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + ALICE.getName()
                .fullName.replaceAll(" ", "%20"));
        URL expectedPersonUrlLogin = new URL("https://www.linkedin.com/m/login/"); //Login Page returned first

        waitUntilBrowserLoaded(browserPanelHandle);
        // there are two options depending on if the page has been logged into or not
        try {
            assertEquals(expectedPersonUrlLogin, browserPanelHandle.getLoadedUrl());
        } catch(Exception AssertionError) {
            assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
        }
    }
}
