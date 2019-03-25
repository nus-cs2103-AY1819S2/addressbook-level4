package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.hint.Hint;

/**
 * Represents a Card in the folder folder.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Card {

    // Identity fields
    private final Question question;
    private final Answer answer;

    // Data fields
    private final Score score;
    private final Set<Hint> hints = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Card(Question question, Answer answer, Score score, Set<Hint> hints) {
        requireAllNonNull(question, answer, score, hints);
        this.question = question;
        this.answer = answer;
        this.score = score;
        this.hints.addAll(hints);
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Score getScore() {
        return score;
    }

    /**
     * Returns an immutable hint set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Hint> getHints() {
        return Collections.unmodifiableSet(hints);
    }

    /**
     * Returns true if both cards have the same question and answer.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Card)) {
            return false;
        }

        Card otherCard = (Card) other;
        return otherCard.getQuestion().equals(getQuestion())
                && otherCard.getAnswer().equals(getAnswer());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question, answer, score, hints);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getQuestion())
                .append(" Answer: ")
                .append(getAnswer())
                .append(" Score: ")
                .append(getScore())
                .append(" Hints: ");
        getHints().forEach(builder::append);
        return builder.toString();
    }

}
