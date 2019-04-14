package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.quiz.QuizCard;

/**
 * Provides a handle to a lesson card in the lesson list panel.
 */
public class QuizResultPanelHandle extends NodeHandle<Node> {
    private static final String FIELD_ID = "#quizResultPanel";

    private final ListView<QuizCard> quizResultPanel;

    public QuizResultPanelHandle(Node cardNode) {
        super(cardNode);

        quizResultPanel = getChildNode(FIELD_ID);
    }

    public int getViewItemCount() {
        return quizResultPanel.getItems().size();
    }
}
