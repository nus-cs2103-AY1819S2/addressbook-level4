package seedu.address.model.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.CardCollection;
import seedu.address.model.ReadOnlyCardCollection;
import seedu.address.model.flashcard.Face;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Proficiency;
import seedu.address.model.flashcard.Statistics;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CardCollection} with sample data.
 */
public class SampleDataUtil {
    public static Flashcard[] getSampleFlashcards() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);
        return new Flashcard[]{
            new Flashcard(new Face("Hello"), new Face("Halo"), new Statistics(), new Proficiency(),
                getTagSet("indonesian")),
            new Flashcard(new Face("Hola"), new Face("你好"), new Statistics(5, 7), new Proficiency(tomorrow, 2),
                getTagSet("chinese", "spanish")),
            new Flashcard(new Face("Newton's 3rd law"), new Face("idk"), new Statistics(0, 5), new Proficiency(),
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
