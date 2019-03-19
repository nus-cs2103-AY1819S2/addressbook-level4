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
    private static final String MESSAGE_CORRECT_ANSWER = "\"Wow you answered correctly, so smart\"";
    private static final String MESSAGE_WRONG_ANSWER = "\"Wrong answer, better luck next time!\"";

    private Card cardToTest; //final?

    @FXML
    private GridPane testSessionPage;
    @FXML
    private Label testCardQuestion;
    @FXML
    private Label testCardAnswer;
    @FXML
    private Label testMessage;

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
        testCardAnswer.setText("Correct answer:\n" + cardToTest.getAnswer().fullAnswer);
        testSessionPage.getChildren().add(testCardQuestion);
    }

    /**
     * Updates the UI to show the answer for a correctly answered card.
     */
    public void handleCorrectAnswer() {
        testSessionPage.setStyle("-fx-background-color: #47AB6C;");
        testMessage.setText(MESSAGE_CORRECT_ANSWER);
        testSessionPage.getChildren().addAll(testCardAnswer, testMessage);
    }

    /**
     * Updates the UI to show the answer for a wrongly answered card.
     */
    public void handleWrongAnswer() {
        testSessionPage.setStyle("-fx-background-color: #ED553B;");
        testMessage.setText(MESSAGE_WRONG_ANSWER);
        testSessionPage.getChildren().addAll(testCardAnswer, testMessage);
    }
}

