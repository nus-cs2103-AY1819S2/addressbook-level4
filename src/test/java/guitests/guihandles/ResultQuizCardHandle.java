package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a lesson card in the lesson list panel.
 */
public class ResultQuizCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String CORES_FIELD_ID = "#cores";
    private static final String STREAK_FIELD_ID = "#streak";
    private static final String TOTAL_ATTEMPTS_FIELD_ID = "#totalAttempts";
    private static final String ACCURACY_FIELD_ID = "#accuracy";

    private final Label idLabel;
    private final Label coresLabel;
    private final Label streakLabel;
    private final Label totalAttemptsLabel;
    private final Label accuracyLabel;

    public ResultQuizCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        coresLabel = getChildNode(CORES_FIELD_ID);
        streakLabel = getChildNode(STREAK_FIELD_ID);
        totalAttemptsLabel = getChildNode(TOTAL_ATTEMPTS_FIELD_ID);
        accuracyLabel = getChildNode(ACCURACY_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getCores() {
        return coresLabel.getText();
    }

    public String getStreak() {
        return streakLabel.getText();
    }

    public String getTotalAttempts() {
        return totalAttemptsLabel.getText();
    }

    public String getAccuracy() {
        return accuracyLabel.getText();
    }
}
