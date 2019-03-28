package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalBooks.BOOKTHIEF;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BookBrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.book.Book;



public class BookBrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Book> selectedBook = new SimpleObjectProperty<>();
    private BookBrowserPanel browserPanel;
    private BookBrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BookBrowserPanel(selectedBook));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BookBrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(BookBrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        guiRobot.interact(() -> selectedBook.set(BOOKTHIEF));
        URL expectedPersonUrl = new URL(BookBrowserPanel.SEARCH_PAGE_URL
            + BOOKTHIEF.getBookName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
