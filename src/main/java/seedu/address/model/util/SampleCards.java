package seedu.address.model.util;

import java.util.List;

import seedu.address.model.card.Card;

/**
 * A utility class containing a list of {@code Card} objects used to form sample lessons.
 */
public class SampleCards {
    // Sample cards for Sample-Capitals lesson
    public static final String SAMPLE_1_CARD_1_CORE_1 = "Japan"; // First core
    public static final String SAMPLE_1_CARD_1_CORE_2 = "Tokyo";
    public static final String SAMPLE_1_CARD_1_OPT_1 = "T"; // First optional
    public static final Card SAMPLE_1_CARD_1 =
            new Card(List.of(SAMPLE_1_CARD_1_CORE_1, SAMPLE_1_CARD_1_CORE_2),
                    List.of(SAMPLE_1_CARD_1_OPT_1));

    public static final String SAMPLE_1_CARD_2_CORE_1 = "Belgium"; // First core
    public static final String SAMPLE_1_CARD_2_CORE_2 = "Brussels";
    public static final String SAMPLE_1_CARD_2_OPT_1 = "B"; // First optional
    public static final Card SAMPLE_1_CARD_2 =
            new Card(List.of(SAMPLE_1_CARD_2_CORE_1, SAMPLE_1_CARD_2_CORE_2),
                    List.of(SAMPLE_1_CARD_2_OPT_1));

    /**
     * This is a constants-only (utility) class which should not be instantiated.
     * Note that this is not a singleton class given that not even a single instance is allowed.
     */
    private SampleCards() { }
}
