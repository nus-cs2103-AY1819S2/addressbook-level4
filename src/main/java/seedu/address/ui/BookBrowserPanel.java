package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.model.book.Book;

/**
 * The Browser Panel of the App.
 */
public class BookBrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE = requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));

    public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";

    private static final String FXML = "BrowserPanel.fxml";

    //private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BookBrowserPanel(ObservableValue<Book> selectedBook) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load book's review when selected book changes.
        selectedBook.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadReviewPage(newValue);
        });

        loadDefaultPage();
    }

    private void loadReviewPage(Book book) {
        loadPage(SEARCH_PAGE_URL + book.getBookName().fullName);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }
}
