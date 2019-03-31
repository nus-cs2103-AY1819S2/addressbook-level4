package seedu.finance.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.finance.commons.core.LogsCenter;
import seedu.finance.commons.events.ShowGraphRequestEvent;

/**
 * The Graph Panel of the App.
 */
public class GraphPanel extends UiPart<Region> {
    //Not sure about the page urls, KIV most likely have to edit again
    public static final String DEFAULT_PAGE = "main/docs/GraphPage.html";
    public static final String SEARCH_PAGE_URL = "main/docs/GraphPage.html";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public GraphPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
    }

    /**
     * Loads graph using attendance data from event object.
     */
    private void loadGraph(ShowGraphRequestEvent event) {
        String url = SEARCH_PAGE_URL;
        loadPage(url);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE);
    }

    @Subscribe
    private void showGraphRequestEvent(ShowGraphRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadGraph(event);
    }
}
