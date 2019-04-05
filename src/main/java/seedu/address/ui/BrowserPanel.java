package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.NoInternetException;
import seedu.address.commons.util.WebUtil;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Stage> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
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

    public void loadPage(Weblink weblink) throws NoInternetException {
        // Load restaurant page when selected restaurant changes.
        if (WebUtil.isNotValidWeblinkUrl(weblink.value));
        loadPage(WebUtil.prependHttps(weblink.value));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(WebUtil.prependHttps(url)));
    }

}
