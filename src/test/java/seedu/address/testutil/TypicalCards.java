package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TopDeck;
import seedu.address.model.deck.Card;

/**
 * A utility class containing a list of {@code Card} objects to be used in tests.
 */
public class TypicalCards {
    public static final Card ADDITION = new CardBuilder().withQuestion("What is 1 + 1?")
            .withAnswer("2").withTags("Math").build();

    public static final Card SUBTRACTION = new CardBuilder().withQuestion("What is 10 - 10?")
            .withAnswer("0").withTags("Math").build();

    public static final Card MULTIPLICATION = new CardBuilder().withQuestion("What is 8 * 8?")
            .withAnswer("64").withTags("Math").build();

    public static final Card DIVISION = new CardBuilder().withQuestion("What is 1024 / 2?")
            .withAnswer("512").withTags("Math").build();

    public static final Card HELLO_WORLD = new CardBuilder().withQuestion("Hello?")
            .withAnswer("World").withTags("Simple", "CS").build();

    private TypicalCards() {} // prevents instantiation

    /**
     * Returns an {@code TopDeck} with all the typical cards.
     */
    public static TopDeck getTypicalTopDeck() {
        TopDeck td = new TopDeck();
        for (Card card : getTypicalCards()) {
            td.addCard(card);
        }
        return td;
    }


    public static List<Card> getTypicalCards() {
        return new ArrayList<>(Arrays.asList(ADDITION, SUBTRACTION, HELLO_WORLD));
    }

}
