package seedu.address.testutil;

import java.util.List;

import seedu.address.model.quiz.QuizMode;
import seedu.address.model.session.Session;
import seedu.address.model.srscard.SrsCard;

/**
 * A utility class to help with building {@code Session} objects.
 */
public class SessionBuilder {
    public static final String DEFAULT_NAME = "01-01-Learn";
    public static final int DEFAULT_COUNT = 1;
    public static final int DEFAULT_INDEX = 1;
    public static final QuizMode DEFAULT_MODE = QuizMode.LEARN;
    public static final List<SrsCard> DEFAULT_SRSCARDS = List.of();

    private String name;
    private int count;
    private QuizMode mode;
    private List<SrsCard> srsCards;
    private int index;

    public SessionBuilder() {
        name = DEFAULT_NAME;
        count = DEFAULT_COUNT;
        mode = DEFAULT_MODE;
        srsCards = DEFAULT_SRSCARDS;
        index = DEFAULT_INDEX;
    }

    /**
     * Initializes the SessionBuilder.
     */
    public SessionBuilder(Session currentSession) {
        name = currentSession.getName();
        count = currentSession.getCount();
        mode = currentSession.getMode();
        srsCards = currentSession.getSrsCards();
        index = currentSession.getLessonIndex();
    }

    /**
     * Sets the {@code Sessionn}'s name.
     */
    public SessionBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code Sessionn}'s mode.
     */
    public SessionBuilder withMode(QuizMode mode) {
        this.mode = mode;
        return this;
    }

    /**
     * Sets the {@code Sessionn}'s cardCount.
     */
    public SessionBuilder withCount(int count) {
        this.count = count;
        return this;
    }

    /**
     * Sets the {@code Sessionn}'s srsCards.
     */
    public SessionBuilder withSrsCards(List<SrsCard> srsCards) {
        this.srsCards = srsCards;
        return this;
    }

    /**
     * Builds and returns a session.
     * @return a session
     */
    public Session build() {
        return new Session(index, count, mode, srsCards);
    }
    public Session build_with_name() {
        return new Session(name, count, mode, srsCards);
    }

    public Session build_without_count() {
        return new Session(name, mode, srsCards);
    }
    public Session buildIndex_without_srsCards() {
        return new Session(index, count, mode);
    }

    public Session build_without_srsCards() {
        return new Session(name, count, mode);
    }
}
