package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.flashcard.Flashcard;

/**
 * Provides a handle to a flashcard card in the flashcard list panel.
 */
public class FlashcardCardViewHandle extends NodeHandle<Node> {
    private static final String FRONTFACE_FIELD_ID = "#frontFace";
    private static final String BACKFACE_FIELD_ID = "#backFace";

    private final Label frontFaceLabel;
    private final Label backFaceLabel;

    public FlashcardCardViewHandle(Node cardNode) {
        super(cardNode);

        frontFaceLabel = getChildNode(FRONTFACE_FIELD_ID);
        backFaceLabel = getChildNode(BACKFACE_FIELD_ID);
    }

    public String getFrontFace() {
        return frontFaceLabel.getText();
    }

    public String getBackFace() {
        return backFaceLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code flashcard}.
     */
    public boolean equals(Flashcard flashcard) {
        return getFrontFace().equals(flashcard.getFrontFace().text)
            && getBackFace().equals(flashcard.getBackFace().text);
    }
}
