package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.NoInternetException;
import seedu.address.model.restaurant.Weblink;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Stage> {

    public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";

    private static final String FXML = "BrowserPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel(Stage root) {
        super(FXML, root);
    }

    public BrowserPanel() {
        this(new Stage());
    }

    /**
     * Opens browser window pop-up
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Takes in a Weblink and validate whether it is a valid Url.
     * @throws NoInternetException if there is no internet connection, NoInternetException is called
     */
    public void loadPage(Weblink weblink) {
        // Load restaurant page when selected restaurant changes.
        loadPage(weblink.value);
    }

    /**
     * Takes in a String url and loads url
     * @param url String format of a valid url
     */
    public void loadPage(String url) {
        logger.info("Loading website : " + url);
        Platform.runLater(() -> browser.getEngine().load(url));
    }

}
