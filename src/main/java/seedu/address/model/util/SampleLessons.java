package seedu.address.model.util;

import static seedu.address.model.util.SampleCards.SAMPLE_1_CARD_1;
import static seedu.address.model.util.SampleCards.SAMPLE_1_CARD_2;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;

/**
 * A utility class containing a list of {@code Lesson} objects used to form sample lessons.
 */
public class SampleLessons {
    public static final String SAMPLE_1_NAME = "Sample-Capitals";
    public static final List<String> SAMPLE_1_CORES =
            new ArrayList<>(List.of("Country", "Capital"));
    public static final List<String> SAMPLE_1_OPTIONALS =
            new ArrayList<>(List.of("First letter"));
    public static final List<Card> SAMPLE_1_CARDS =
            new ArrayList<>(List.of(SAMPLE_1_CARD_2, SAMPLE_1_CARD_1));
    public static final Lesson SAMPLE_1_LESSON = new Lesson(
            SAMPLE_1_NAME, SAMPLE_1_CORES, SAMPLE_1_OPTIONALS, 0, 1, SAMPLE_1_CARDS);

    /**
     * This is a constants-only (utility) class which should not be instantiated.
     * Note that this is not a singleton class given that not even a single instance is allowed.
     */
    private SampleLessons() { }
}
