package guitests.guihandles;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.review.Review;

/**
 * Provides a handle to a restaurant card in the restaurant list panel.
 */
public class RestaurantCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String REVIEWS_FIELD_ID = "#reviews";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;
    private final List<Label> reviewLabels;

    public RestaurantCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        Region reviewsContainer = getChildNode(REVIEWS_FIELD_ID);
        reviewLabels = reviewsContainer
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

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<Review> getReviews() {
        Iterator<Label> labelIterator = reviewLabels.iterator();
        List<Review> reviews = new ArrayList<>();

        // Read in every 3 labels to construct a list of Reviews
        // @TODO ensure that list of reviewLabels is in multiples of 3 and in the order timeStamp, rating, entry
        while (labelIterator.hasNext()) {
            Timestamp timeStamp = Timestamp.valueOf(LocalDateTime.parse(labelIterator.next().getText()));
            Rating rating = new Rating(labelIterator.next().getText());
            Entry entry = new Entry(labelIterator.next().getText());
            reviews.add(new Review(entry, rating, timeStamp));
        }

        return reviews;
    }

    /**
     * Returns true if this handle contains {@code restaurant}.
     */
    public boolean equals(Restaurant restaurant) {
        return getName().equals(restaurant.getName().fullName)
                && getAddress().equals(restaurant.getAddress().value)
                && getPhone().equals(restaurant.getPhone().value)
                && getEmail().equals(restaurant.getEmail().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(restaurant.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())))
                && ImmutableMultiset.copyOf(getReviews()).equals(ImmutableMultiset.copyOf(restaurant.getReviews()));
    }
}
