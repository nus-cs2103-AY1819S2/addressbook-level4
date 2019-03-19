package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;

/**
 * An UI component when user enters a test session.
 */
public class TestSession extends UiPart<Region> {

    private static final String FXML = "TestSession.fxml";

    private Card cardToTest; //final?

    @FXML
    private GridPane testSessionPage;
    @FXML
    private Label testCardQuestion;
    @FXML
    private Label testCardAnswer;

    public TestSession() {
        super(FXML);
    }
    public TestSession(Card cardToTest) {
        super(FXML);
        this.cardToTest = cardToTest;
        displayCard(cardToTest);
    }

    /**
     * Updates the UI to show test session page with the question of the specified card
     * @param cardToTest card to be tested in this page of test session
     */
    public void displayCard(Card cardToTest) {
        testSessionPage.getChildren().clear();
        testCardQuestion.setText(cardToTest.getQuestion().fullQuestion);
        testCardAnswer.setText(cardToTest.getAnswer().fullAnswer);
        testSessionPage.getChildren().add(testCardQuestion);
    }

    /**
     * Updates the UI to show the answer for a correctly answered card
     */
    public void handleCorrectAnswer() {
        testSessionPage.setStyle("-fx-background-color: #47AB6C;");
        testSessionPage.getChildren().add(testCardAnswer);
    }
}

