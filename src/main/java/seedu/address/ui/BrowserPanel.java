package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.Card;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_CARD_PAGE = "";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;
    @FXML
    private GridPane cardPage;
    @FXML
    private Label cardQuestion;
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
                loadDefaultCard();
                return;
            }
            loadCardPage(newValue);
        });

        loadDefaultCard();
    }

    /**
     * Load the current selected {@code Card} into the browser panel with all card info.
     * @param card selected to be displayed.
     */
    private void loadCardPage(Card card) {
        cardPage.getChildren().clear();

        cardQuestion.setText(card.getQuestion().fullQuestion);
        answer.setText("Ans: " + card.getAnswer().fullAnswer);
        score.setText("Score: " + card.getScore().toString());
        // Set empty string for hint by default
        hint.setText("");
        if (!card.getHints().isEmpty()) {
            assert card.getHints().size() <= 1;
            card.getHints().forEach(hintVal -> hint.setText("Hint: " + hintVal.hintName));
        }

        cardPage.getChildren().addAll(cardQuestion, answer, score, hint);
    }

    /**
     * Loads a default blank card with a background that matches the general theme.
     */
    private void loadDefaultCard() {
        cardPage.getChildren().clear();
        cardQuestion.setText("");
        answer.setText("");
        score.setText("");
        hint.setText("");
        cardPage.getChildren().addAll(cardQuestion, answer, score, hint);
    }

}
