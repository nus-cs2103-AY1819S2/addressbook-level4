package seedu.address.testutil;

import java.util.List;

import seedu.address.model.quiz.Quiz;
import seedu.address.model.session.Session;
import seedu.address.model.srscard.SrsCard;

/**
 * A utility class to help with building {@code Session} objects.
 */
public class SessionBuilder {
    public static final String DEFAULT_NAME = "01-01-Learn";
    public static final int DEFAULT_COUNT = 1;
    public static final Quiz.Mode DEFAULT_MODE = Quiz.Mode.LEARN;
    public static final List<SrsCard> DEFAULT_SRSCARDS = List.of();

    private String name;
    private int count;
    private Quiz.Mode mode;
    private List<SrsCard> srsCards;

    public SessionBuilder() {
        name = DEFAULT_NAME;
        count = DEFAULT_COUNT;
        mode = DEFAULT_MODE;
        srsCards = DEFAULT_SRSCARDS;
    }

    /**
     * Initializes the SessionBuilder.
     */
    public SessionBuilder(Session currentSession) {
        name = currentSession.getName();
        count = currentSession.getCount();
        mode = currentSession.getMode();
        srsCards = currentSession.getSrsCards();
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
    public SessionBuilder withMode(Quiz.Mode mode) {
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
     * Builds and returns a session.
     * @return a session
     */
    public Session build() {
        return new Session(name, count, mode, srsCards);
    }

    public Session build_without_count() {
        return new Session(name, mode, srsCards);
    }

    public Session build_without_srsCards() {
        return new Session(name, count, mode);
    }
}
