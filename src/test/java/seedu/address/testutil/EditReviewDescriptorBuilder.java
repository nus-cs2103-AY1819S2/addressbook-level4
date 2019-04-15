package seedu.address.testutil;

import seedu.address.logic.commands.EditReviewCommand.EditReviewDescriptor;
import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.review.Review;

/**
 * A utility class to help with building EditReviewDescriptor objects.
 */
public class EditReviewDescriptorBuilder {

    private static final Entry DEFAULT_ENTRY = new Entry("Amazing place");
    private static final Rating DEFAULT_RATING = new Rating("4");
    private EditReviewDescriptor descriptor;

    public EditReviewDescriptorBuilder() {
        this(new Review(DEFAULT_ENTRY, DEFAULT_RATING));
    }

    public EditReviewDescriptorBuilder(EditReviewDescriptor descriptor) {
        this.descriptor = new EditReviewDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRestaurantDescriptor} with fields containing {@code restaurant}'s details
     */
    public EditReviewDescriptorBuilder(Review review) {
        descriptor = new EditReviewDescriptor();
        descriptor.setEntry(review.getEntry());
        descriptor.setRating(review.getRating());
    }

    /**
     * Sets the {@code Name} of the {@code EditRestaurantDescriptor} that we are building.
     */
    public EditReviewDescriptorBuilder withEntry(String entry) {
        descriptor.setEntry(new Entry(entry));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditRestaurantDescriptor} that we are building.
     */
    public EditReviewDescriptorBuilder withRating(String rating) {
        descriptor.setRating(new Rating(rating));
        return this;
    }

    public EditReviewDescriptor build() {
        return descriptor;
    }

}
