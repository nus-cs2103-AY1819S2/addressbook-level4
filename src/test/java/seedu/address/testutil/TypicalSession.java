package seedu.address.testutil;

import static seedu.address.testutil.TypicalCards.CARD_JAPAN;
import static seedu.address.testutil.TypicalLessons.LESSON_DEFAULT;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import seedu.address.model.session.Session;
import seedu.address.model.srscard.SrsCard;
import seedu.address.model.user.CardSrsData;

/**
 * A utility class containing a list of {@code QuizCard} objects used for testing.
 */
public class TypicalSession {
    public static final SrsCard SRS_CARD_JAPAN = new SrsCard(CARD_JAPAN, new CardSrsData(CARD_JAPAN.hashCode(), 1,
        1, Instant.now().plus(Duration.ofHours(2))), LESSON_DEFAULT);

    public static final Session SESSION_DEFAULT = new SessionBuilder().build();
    public static final Session SESSION_DEFAULT_2 = new SessionBuilder()
        .withCount(2)
        .withSrsCards(List.of(
            new SrsCardBuilder().build(),
            new SrsCardBuilder(SRS_CARD_JAPAN).build()))
        .build();

    public static final Session SESSION_DEFAULT_2_ACTUAL = new SessionBuilder()
        .withCount(2)
        .withSrsCards(List.of(
            new SrsCardBuilder().build(),
            new SrsCardBuilder(SRS_CARD_JAPAN).build()))
        .build();
}
