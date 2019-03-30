package seedu.address.ui;

import org.junit.Before;

import guitests.guihandles.BookBrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.book.Review;


public class BookBrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Review> selectedReview = new SimpleObjectProperty<>();
    private BookBrowserPanel browserPanel;
    private BookBrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BookBrowserPanel(selectedReview));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BookBrowserPanelHandle(browserPanel.getRoot());
    }
    //
    //    @Test
    //    public void display() throws Exception {
    //        // default web page
    //        assertEquals(BookBrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());
    //
    //        // associated web page of a person
    //        guiRobot.interact(() -> selectedBook.set(BOOKTHIEF));
    //        URL expectedPersonUrl = new URL(BookBrowserPanel.SEARCH_PAGE_URL
    //            + BOOKTHIEF.getBookName().fullName.replaceAll(" ", "%20"));
    //
    //        waitUntilBrowserLoaded(browserPanelHandle);
    //        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    //    }
}
