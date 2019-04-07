package guitests.guihandles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;
import seedu.address.ui.LessonCard;

/**
 * Provides a handle to a lesson card in the lesson list panel.
 */
public class LessonOverviewHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#name";
    private static final String COUNT_FIELD_ID = "#count";
    private static final String TAGS_FIELD_ID = "#bigHeaders";
    private final Label nameLabel;
    private final Label countLabel;
    private final List<Label> tagLabels;

    public LessonOverviewHandle(Node cardNode) {
        super(cardNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
        countLabel = getChildNode(COUNT_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getCount() {
        return countLabel.getText();
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
    public boolean equals(Lesson lesson) {
        if (!getName().equals(lesson.getName())
                || (getCount().equals(LessonCard.getCountString(lesson.getCardCount())))) {
            return false;
        }

        List<String> headers = getHeaders();
        List<String> headersToCompare = new ArrayList<>(lesson.getCoreHeaders());
        headersToCompare.addAll(lesson.getOptionalHeaders());
        return headers.equals(headersToCompare);
    }
}
