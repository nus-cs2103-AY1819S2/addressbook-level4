package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.book.Book;

/**
<<<<<<< HEAD
 * Provides a handle to a book card in the book list panel.
 */
public class BookCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String AUTHOR_FIELD_ID = "#author";
    private static final String RATING_FIELD_ID = "#rating";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
=======
 * Provides a handle to a book card in the book shelf list panel.
 */
public class BookCardHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#name";
    private static final String AUTHOR_FIELD_ID = "#author";
    private static final String RATING_FIELD_ID = "#rating";
    private static final String REVIEWS_FIELD_ID = "#reviews";
    private static final String TAGS_FIELD_ID = "#tags";

>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
    private final Label nameLabel;
    private final Label authorLabel;
    private final Label ratingLabel;
    private final List<Label> tagLabels;

    public BookCardHandle(Node cardNode) {
        super(cardNode);

<<<<<<< HEAD
        idLabel = getChildNode(ID_FIELD_ID);
=======
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
        nameLabel = getChildNode(NAME_FIELD_ID);
        authorLabel = getChildNode(AUTHOR_FIELD_ID);
        ratingLabel = getChildNode(RATING_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
<<<<<<< HEAD
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
=======
            .getChildrenUnmodifiable()
            .stream()
            .map(Label.class::cast)
            .collect(Collectors.toList());
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
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
<<<<<<< HEAD
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
=======
            .stream()
            .map(Label::getText)
            .collect(Collectors.toList());
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
    }

    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
<<<<<<< HEAD
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
=======
            .stream()
            .filter(label -> label.getText().equals(tag))
            .map(Label::getStyleClass)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No such tag."));
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
    }

    /**
     * Returns true if this handle contains {@code book}.
     */
<<<<<<< HEAD
    public boolean equals(Book book) {
        return getName().equals(book.getBookName().fullName)
                && getAuthor().equals(book.getAuthor().fullName)
                && getRating().equals(book.getRating().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(book.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
=======

    public boolean equals(Book book) {

        return getName().equals(book.getBookName().fullName)
            && getAuthor().equals(book.getAuthor().fullName)
            && getRating().equals(book.getRating().value)
            && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(book.getTags().stream()
            .map(tag -> tag.tagName)
            .collect(Collectors.toList())));
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
    }
}
