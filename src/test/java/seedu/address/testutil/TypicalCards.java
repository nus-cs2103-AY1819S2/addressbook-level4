package seedu.address.testutil;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

        import seedu.address.model.deck.Card;

public class TypicalCards {
    public static final Card ADDITION = new CardBuilder().withQuestion("What is 1 + 1?")
            .withAnswer("2").withTags("Easy").build();

    public static final Card SUBTRACTION = new CardBuilder().withQuestion("What is 10 - 10?")
            .withAnswer("0").withTags("Easy").build();

    public static final Card HELLO_WORLD = new CardBuilder().withQuestion("Hello?")
            .withAnswer("World").build();

    private TypicalCards() {} // prevents instantiation


    public static List<Card> getTypicalCards() {
        return new ArrayList<>(Arrays.asList(ADDITION, SUBTRACTION, HELLO_WORLD));
    }

}
