package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * A handler for the {@code QuizResultDisplay} of the UI
 */
public class QuizResultDisplayHandle extends NodeHandle<TextFlow> {

    public static final String RESULT_DISPLAY_ID = "#quizResultDisplay";

    public QuizResultDisplayHandle(TextFlow quizResultDisplayNode) {
        super(quizResultDisplayNode);
    }

    /**
     * Returns the text in the result display.
     */
    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (Node node: getRootNode().getChildren()) {
            sb.append(((Text) node).getText());
        }

        return sb.toString();
    }
}
