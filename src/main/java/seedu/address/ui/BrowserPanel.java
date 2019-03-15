package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.Card;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";

    private static Card CURRENT_CARD;

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;
    @FXML
    private GridPane cardPage;
    @FXML
    private Label question;
    @FXML
    private Label answer;
    @FXML
    private Label hint;
    @FXML
    private Label score;

    public BrowserPanel(ObservableValue<Card> selectedCard) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load card page when selected card changes.
        selectedCard.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadCardPage(newValue);
        });

        loadDefaultPage();
    }

    public String getCurrentCardQuestion() {
        return CURRENT_CARD.getQuestion().fullQuestion;
    }

    private void setCurrentCard(Card card) {
        CURRENT_CARD = card;
    }

    /**
     * Load the current selected {@code Card} into the browser panel with all card info.
     * @param card selected to be displayed.
     */
    private void loadCardPage(Card card) {
        cardPage.getChildren().clear();

        question.setText(card.getQuestion().fullQuestion);
        answer.setText("Ans: " + card.getAnswer().fullAnswer);
        score.setText("Score: " + card.getScore().toString());
        // Set empty string for hint by default
        hint.setText("");
        if (!card.getHints().isEmpty()) {
            assert card.getHints().size() <= 1;
            card.getHints().forEach(hintVal -> hint.setText("Hint: " + hintVal.hintName));
        }

        cardPage.getChildren().addAll(question, answer, score, hint);

        setCurrentCard(card);
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
