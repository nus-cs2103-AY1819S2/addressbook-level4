package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class WarningPanel extends UiPart<Region> {

    private static final String DUMMY_URL = "https://se-education.org/dummy-search-page/";
    private static final String FXML = "WarningPanel.fxml";

    @FXML
    private TextArea warning;

    @FXML
    private WebView browser;

    public WarningPanel() {
        super(FXML);
        loadWarning();
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    private void loadWarning() {
        loadPage(DUMMY_URL);
    }

//    public void setFeedbackToUser(String feedbackToUser) {
//        requireNonNull(feedbackToUser);
//        warning.setText(feedbackToUser);
//    }

}