package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.flashcard.Face;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Statistics;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Flashcard objects.
 */
public class FlashcardBuilder {

    public static final String DEFAULT_FRONTFACE = "Lorem";
    public static final String DEFAULT_BACKFACE = "Ipsum";

    private Face frontFace;
    private Face backFace;
    private Set<Tag> tags;
    private Statistics statistics;

    public FlashcardBuilder() {
        frontFace = new Face(DEFAULT_FRONTFACE);
        backFace = new Face(DEFAULT_BACKFACE);
        tags = new HashSet<>();
        statistics = new Statistics();
    }

    /**
     * Initializes the FlashcardBuilder with the data of {@code flashcardToCopy}.
     */
    public FlashcardBuilder(Flashcard flashcardToCopy) {
        frontFace = flashcardToCopy.getFrontFace();
        backFace = flashcardToCopy.getBackFace();
        tags = new HashSet<>(flashcardToCopy.getTags());
        statistics = flashcardToCopy.getStatistics();
    }


    /**
     * Sets the {@code frontFace} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withFrontFace(String frontFace) {
        this.frontFace = new Face(frontFace);
        return this;
    }

    /**
     * Sets the {@code backFace} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withBackFace(String backFace) {
        this.backFace = new Face(backFace);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code statistics} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withStatistics(int success, int attempts) {
        this.statistics = new Statistics(success, attempts);
        return this;
    }

    public Flashcard build() {
        return new Flashcard(frontFace, backFace, statistics, tags);
    }

}
