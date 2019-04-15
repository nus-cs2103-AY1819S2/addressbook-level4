package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;
import seedu.address.model.quiz.QuizCard;

/**
 * An UI component that displays information of a {@link Card}.
 */
public class ResultQuizCard extends UiPart<Region> {

    private static final String FXML = "ResultQuizCard.fxml";

    @FXML
    private Label id;

    @FXML
    private Label cores;

    @FXML
    private Label streak;

    @FXML
    private Label totalAttempts;

    @FXML
    private Label accuracy;

    public ResultQuizCard(QuizCard card, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ". ");
        cores.setText(String.format("%s / %s", card.getQuestion(), card.getAnswer()));
        int cardStreak = card.getStreak();
        int cardTotalAttempts = card.getTotalAttempts();
        int cardAccuracy = (int) ((float) cardStreak / cardTotalAttempts * 100);

        streak.setText("Streak: " + cardStreak);
        totalAttempts.setText("Total attempts: " + cardTotalAttempts);
        accuracy.setText("Accuracy: " + cardAccuracy + "%");
    }
}
