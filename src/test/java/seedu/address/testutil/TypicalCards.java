package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.card.Card;

/**
 * A utility class containing a list of {@code Card} objects used for {@code Card} testing.
 */
public class TypicalCards {
    public static final Card CARD_BELGIUM = new CardBuilder().build();

    public static final String CARD_JAPAN_CORE1 = "Japan"; // First core
    public static final String CARD_JAPAN_CORE2 = "Tokyo";
    public static final String CARD_JAPAN_OPT1 = "T"; // First optional
    public static final Card CARD_JAPAN = new CardBuilder()
            .withCores(CARD_JAPAN_CORE1, CARD_JAPAN_CORE2)
            .withOptionals(CARD_JAPAN_OPT1).build();

    private TypicalCards() {} // Prevents instantiation

    public static List<Card> getTypicalCards() {
        return new ArrayList<>(Arrays.asList(CARD_BELGIUM, CARD_JAPAN));
    }
}
