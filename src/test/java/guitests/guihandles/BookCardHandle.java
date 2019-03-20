package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.book.Book;

/**
 * Provides a handle to a book card in the book list panel.
 */
public class BookCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String AUTHOR_FIELD_ID = "#author";
    private static final String RATING_FIELD_ID = "#rating";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label authorLabel;
    private final Label ratingLabel;
    private final List<Label> tagLabels;

    public BookCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        authorLabel = getChildNode(AUTHOR_FIELD_ID);
        ratingLabel = getChildNode(RATING_FIELD_ID);

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

    public String getAuthor() {
        return authorLabel.getText();
    }

    public String getRating() {
        return ratingLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }

    /**
     * Returns true if this handle contains {@code book}.
     */
    public boolean equals(Book book) {
        return getName().equals(book.getBookName().fullName)
                && getAuthor().equals(book.getAuthor().fullName)
                && getRating().equals(book.getRating().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(book.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
