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

    // Sample cards for Sample-Muscles-Anatomy lesson
    public static final String SAMPLE_2_CARD_1_CORE_1 = "Deltoid"; // First core
    public static final String SAMPLE_2_CARD_1_CORE_2 = "Abduction of arm";
    public static final String SAMPLE_2_CARD_1_CORE_3 = "Axillary nerve";
    public static final String SAMPLE_2_CARD_1_OPT_1 = "Most superficial muscle on shoulder";
    public static final Card SAMPLE_2_CARD_1 =
            new Card(List.of(SAMPLE_2_CARD_1_CORE_1, SAMPLE_2_CARD_1_CORE_2, SAMPLE_2_CARD_1_CORE_3),
                    List.of(SAMPLE_2_CARD_1_OPT_1));

    public static final String SAMPLE_2_CARD_2_CORE_1 = "Orbicularis oculi"; // First core
    public static final String SAMPLE_2_CARD_2_CORE_2 = "Closes the eyelids";
    public static final String SAMPLE_2_CARD_2_CORE_3 = "Facial nerve";
    public static final String SAMPLE_2_CARD_2_OPT_1 = "\uD83D\uDE09";
    public static final Card SAMPLE_2_CARD_2 =
            new Card(List.of(SAMPLE_2_CARD_2_CORE_1, SAMPLE_2_CARD_2_CORE_2, SAMPLE_2_CARD_2_CORE_3),
                    List.of(SAMPLE_2_CARD_2_OPT_1));

    public static final String SAMPLE_2_CARD_3_CORE_1 = "Quadriceps femoris"; // First core
    public static final String SAMPLE_2_CARD_3_CORE_2 = "Extension of knee";
    public static final String SAMPLE_2_CARD_3_CORE_3 = "Femoral nerve";
    public static final String SAMPLE_2_CARD_3_OPT_1 = "Knee jerk reflex";
    public static final Card SAMPLE_2_CARD_3 =
            new Card(List.of(SAMPLE_2_CARD_3_CORE_1, SAMPLE_2_CARD_3_CORE_2, SAMPLE_2_CARD_3_CORE_3),
                    List.of(SAMPLE_2_CARD_3_OPT_1));

    /**
     * This is a constants-only (utility) class which should not be instantiated.
     * Note that this is not a singleton class given that not even a single instance is allowed.
     */
    private SampleCards() { }
}
