package seedu.address.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.flashcard.Flashcard;

/**
 * Panel containing the list of flashcards.
 */
public class QuizPanel extends UiPart<Region> {
    private static final String FXML = "QuizPanel.fxml";

    @FXML
    private Label cardsRemaining;

    @FXML
    private Label cardsRemainingLabel;

    @FXML
    private Label good;

    @FXML
    private Label bad;

    public QuizPanel(ObservableList<Flashcard> quizCards, ObservableValue<Integer> quizGood,
                     ObservableValue<Integer> quizBad) {
        super(FXML);
        updateCardsRemaining(quizCards.size() + 1);
        quizCards.addListener((ListChangeListener<Flashcard>) change ->
            updateCardsRemaining(change.getList().size() + 1));
        good.setText(String.valueOf(quizGood.getValue()));
        bad.setText(String.valueOf(quizBad.getValue()));
        quizGood.addListener(((observableValue, oldValue, newValue) -> good.setText(String.valueOf(newValue))));
        quizBad.addListener(((observableValue, oldValue, newValue) -> bad.setText(String.valueOf(newValue))));
    }

    /**
     * Updates the cards remaining labels for Quiz Panel UI.
     *
     * @param size the number of cards remaining
     */
    private void updateCardsRemaining(int size) {
        cardsRemaining.setText(String.valueOf(size));
        if (size == 1) {
            cardsRemainingLabel.setText("card remaining");
        } else {
            cardsRemainingLabel.setText("cards remaining");
        }
    }
}
