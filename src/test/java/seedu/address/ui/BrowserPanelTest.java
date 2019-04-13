package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

// import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.MainApp;
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
    public void display_placeholder() throws Exception {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        guiRobot.interact(() -> selectedPerson.set(ALICE));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(BrowserPanel.PLACEHOLDER_PAGE, browserPanelHandle.getLoadedUrl());
    }

    @Test
    public void display_storageCv() throws Exception {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        guiRobot.interact(() -> selectedPerson.set(BENSON));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + "html/bensonmeier.html"),
                browserPanelHandle.getLoadedUrl());
    }
}
