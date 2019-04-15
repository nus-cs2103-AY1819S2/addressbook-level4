package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.pdf.model.pdf.Pdf;

/**
 * Provides a handle to a pdf card in the pdf list panel.
 */
public class PdfCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DIRECTORY_FIELD_ID = "#directory";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String SIZE_FIELD_ID = "#size";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label directoryLabel;
    private final Label deadlineLabel;
    private final Label sizeLabel;
    private final List<Label> tagLabels;

    public PdfCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        directoryLabel = getChildNode(DIRECTORY_FIELD_ID);
        deadlineLabel = getChildNode(DEADLINE_FIELD_ID);
        sizeLabel = getChildNode(SIZE_FIELD_ID);

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

    public String getDirectory() {
        return directoryLabel.getText();
    }

    public String getDeadline() {
        return deadlineLabel.getText();
    }

    public String getSize() {
        return sizeLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code pdf}.
     */
    public boolean equals(Pdf pdf) {
        return getName().equals(pdf.getName().getFullName())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(pdf.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
