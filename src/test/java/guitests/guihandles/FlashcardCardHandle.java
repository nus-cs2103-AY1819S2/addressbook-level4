package guitests.guihandles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;
import seedu.address.ui.FlashcardCard;

/**
 * Provides a handle to a lesson card in the lesson list panel.
 */
public class FlashcardCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String TAGS_FIELD_ID = "#headers";

    private final Label idLabel;
    private final Label nameLabel;
    private final List<Label> tagLabels;

    public FlashcardCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }


    public List<String> getHeaders() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code lesson}.
     */
    public boolean equals(Card card) {
        if (!getName().equals(FlashcardCard.formatName(card.getCore(0), card.getCore(1)))) {
            return false;
        }

        List<String> headers = getHeaders();
        List<String> headersToCompare = new ArrayList<>(card.getCores());
        headersToCompare.addAll(card.getOptionals());
        return headers.equals(headersToCompare);
    }
}
