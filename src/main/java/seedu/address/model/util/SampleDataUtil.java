package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.CardCollection;
import seedu.address.model.ReadOnlyCardCollection;
import seedu.address.model.flashcard.Face;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CardCollection} with sample data.
 */
public class SampleDataUtil {
    public static Flashcard[] getSampleFlashcards() {
        return new Flashcard[] {
            new Flashcard(new Face("Hello"), new Face("Halo"),
                getTagSet("indonesian")),
            new Flashcard(new Face("Hola"), new Face("你好"),
                getTagSet("chinese", "spanish")),
            new Flashcard(new Face("Newton's 3rd law"), new Face("idk"),
                getTagSet("alevel", "physics"))
        };
    }

    public static ReadOnlyCardCollection getSampleCardCollection() {
        CardCollection sampleAb = new CardCollection();
        for (Flashcard sampleFlashcard : getSampleFlashcards()) {
            sampleAb.addFlashcard(sampleFlashcard);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

}
