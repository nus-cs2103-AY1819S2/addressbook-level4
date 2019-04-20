package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.card.Card;

/**
 * A utility class containing a list of {@code Card} objects used for testing.
 */
public class TypicalCards {
    public static final Card CARD_BELGIUM = new CardBuilder().build();

    public static final String CARD_JAPAN_CORE1 = "Japan"; // First core
    public static final String CARD_JAPAN_CORE2 = "Tokyo";
    public static final String CARD_JAPAN_OPT1 = "T"; // First optional
    public static final Card CARD_JAPAN = new CardBuilder()
            .withCores(CARD_JAPAN_CORE1, CARD_JAPAN_CORE2)
            .withOptionals(CARD_JAPAN_OPT1).build();
    public static final String CARD_CAT_CORE1 = "Cats have 9 lives."; // First core
    public static final String CARD_CAT_CORE2 = "No";
    public static final Card CARD_CAT = new CardBuilder()
            .withCores(CARD_CAT_CORE1, CARD_CAT_CORE2).build();
    public static final String CARD_DOG_CORE1 = "Dogs can eat chocolate."; // First core
    public static final String CARD_DOG_CORE2 = "No";
    public static final Card CARD_DOG = new CardBuilder()
            .withCores(CARD_DOG_CORE1, CARD_DOG_CORE2).build();
    public static final String CARD_DOGCAT_CORE1 = "Dogs are better than cats."; // First core
    public static final String CARD_DOGCAT_CORE2 = "Yes";
    public static final Card CARD_DOGCAT = new CardBuilder()
            .withCores(CARD_DOGCAT_CORE1, CARD_DOGCAT_CORE2).build();
    public static final String CARD_MULTI_CORE1 = "Japan."; // First core
    public static final String CARD_MULTI_CORE2 = "Tokyo";
    public static final String CARD_MULTI_CORE3 = "Japanese";
    public static final Card CARD_MULTI = new CardBuilder()
            .withCores(CARD_MULTI_CORE1, CARD_MULTI_CORE2, CARD_MULTI_CORE3).build();
    public static final String CARD_EMPTY_CORE1 = "Japan."; // First core
    public static final String CARD_EMPTY_CORE2 = "";
    public static final Card CARD_EMPTY = new CardBuilder()
            .withCores(CARD_EMPTY_CORE1, CARD_EMPTY_CORE2).build();

    private TypicalCards() {} // Prevents instantiation

    public static List<Card> getTypicalCards() {
        return new ArrayList<>(Arrays.asList(CARD_BELGIUM, CARD_JAPAN));
    }
}
