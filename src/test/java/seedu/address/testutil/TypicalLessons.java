package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;

/**
 * A utility class containing a list of {@code Lesson} objects used for {@code Lesson} testing.
 */
public class TypicalLessons {
    public static final String CARD_CAPITAL_CORE1 = "Korea";
    public static final String CARD_CAPITAL_CORE2 = "Seoul";
    public static final String CARD_CAPITAL_OPT1 = "Starts with S";
    public static final String CARD_CAPITAL_OPT2 = "Ends with L";
    public static final Card CARD_ONE_OPT = new CardBuilder()
            .withCores(CARD_CAPITAL_CORE1, CARD_CAPITAL_CORE2)
            .withOptionals(CARD_CAPITAL_OPT1).build();
    public static final Card CARD_TWO_OPT = new CardBuilder()
            .withCores(CARD_CAPITAL_CORE1, CARD_CAPITAL_CORE2)
            .withOptionals(CARD_CAPITAL_OPT1, CARD_CAPITAL_OPT2).build();

    public static final Lesson LESSON_ONE_OPT = new LessonBuilder().withCards(CARD_ONE_OPT).build();

    public static final List<String> FIELDS_TWO_OPTS = List.of("Country", "Capital", "Hint", "Country Code");
    public static final Lesson LESSON_TWO_OPT = new LessonBuilder()
            .withFields(FIELDS_TWO_OPTS).withCards(CARD_TWO_OPT).build();

    private TypicalLessons() {} // prevents instantiation

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(LESSON_ONE_OPT, LESSON_TWO_OPT));
    }
}
