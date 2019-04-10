package seedu.address.ui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.quiz.QuizCard;

/**
 * Panel contains list of {@link QuizCard}.
 */
public class QuizResultPanel extends UiPart<Region> {
    private static final String FXML = "QuizResultPanel.fxml";

    @FXML
    private ListView<QuizCard> quizResultPanel;

    public QuizResultPanel() {
        super(FXML);
    }

    public void setFeedbackToUser(List<QuizCard> quizCardList) {
        quizResultPanel.setItems(FXCollections.observableList(quizCardList));
        quizResultPanel.setCellFactory(listView -> new CardViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code FlashcardCard}..
     */
    class CardViewCell extends ListCell<QuizCard> {
        @Override
        protected void updateItem(QuizCard card, boolean empty) {
            super.updateItem(card, empty);

            if (empty || card == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ResultQuizCard(card, getIndex() + 1).getRoot());
            }
        }
    }
}
