package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.card.Card;

/**
 * Provides a handle to a folder folder in the folder list panel.
 */
public class CardThumbnailHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String QUESTION_FIELD_ID = "#question";

    private final Label idLabel;
    private final Label questionLabel;

    public CardThumbnailHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        questionLabel = getChildNode(QUESTION_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getQuestion() {
        return questionLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code folder}.
     */
    public boolean equals(Card card) {
        return getQuestion().equals(card.getQuestion().fullQuestion);
    }
}
