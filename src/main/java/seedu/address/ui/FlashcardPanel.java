package seedu.address.ui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;

/**
 * Panel containing the list of lessons.
 */
public class FlashcardPanel extends UiPart<Region> {
    private static final String FXML = "FlashcardPanel.fxml";
    //private final Logger logger = LogsCenter.getLogger(LessonListPanel.class);

    @FXML
    private ListView<Card> flashcardView;

    private int questionIndex;
    private int answerIndex;

    public FlashcardPanel() {
        super(FXML);
    }

    /**
     * Updates the card list in GUI.
     *
     * @param lesson the lesson from {@code ManagementModel}
     */
    public void updateCardList(Lesson lesson) {
        List<Card> cards = lesson.getCards();
        this.questionIndex = lesson.getQuestionCoreIndex();
        this.answerIndex = lesson.getAnswerCoreIndex();

        flashcardView.setItems(FXCollections.observableList(cards));
        flashcardView.setCellFactory(listView -> new CardViewCell());
    }

    /**
     * Hides the card list in GUI.
     */
    public void hideCardList() {
        flashcardView.setItems(FXCollections.emptyObservableList());
        flashcardView.setCellFactory(listView -> new CardViewCell());
    }

    public int getViewItemCount() {
        return flashcardView.getItems().size();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code FlashcardCard}..
     */
    class CardViewCell extends ListCell<Card> {
        @Override
        protected void updateItem(Card card, boolean empty) {
            super.updateItem(card, empty);

            if (empty || card == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FlashcardCard(card, getIndex() + 1,
                        questionIndex, answerIndex).getRoot());
            }
        }
    }
}
