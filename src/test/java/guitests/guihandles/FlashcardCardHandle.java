package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.flashcard.Flashcard;

/**
 * Provides a handle to a flashcard card in the flashcard list panel.
 */
public class FlashcardCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String FRONTFACE_FIELD_ID = "#frontFace";
    private static final String BACKFACE_FIELD_ID = "#backFace";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label frontFaceLabel;
    private final Label backFaceLabel;
    private final List<Label> tagLabels;

    public FlashcardCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        frontFaceLabel = getChildNode(FRONTFACE_FIELD_ID);
        backFaceLabel = getChildNode(BACKFACE_FIELD_ID);

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

    public String getFrontFace() {
        return frontFaceLabel.getText();
    }

    public String getBackFace() {
        return backFaceLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
            .stream()
            .map(Label::getText)
            .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code flashcard}.
     */
    public boolean equals(Flashcard flashcard) {
        return getFrontFace().equals(flashcard.getFrontFace().text)
            && getBackFace().equals(flashcard.getBackFace().text)
            && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(flashcard.getTags().stream()
            .map(tag -> tag.tagName)
            .collect(Collectors.toList())));
    }
}
