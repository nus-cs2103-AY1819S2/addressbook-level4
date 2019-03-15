package seedu.address.model.flashcard;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Flashcard in the card collection. Guarantees: details are present and not null, field values are
 * validated, immutable.
 */
public class Flashcard {

    // Identity fields
    private final Face frontFace;
    private final Face backFace;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final Statistics statistics;

    /**
     * Every field must be present and not null.
     */
    public Flashcard(Face frontFace, Face backFace, Statistics statistics, Set<Tag> tags) {
        requireAllNonNull(frontFace, backFace, statistics, tags);
        this.frontFace = frontFace;
        this.backFace = backFace;
        this.statistics = statistics;
        this.tags.addAll(tags);
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public Face getFrontFace() {
        return frontFace;
    }

    public Face getBackFace() {
        return backFace;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both flashcards of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two flashcards.
     */
    public boolean isSameFlashcard(Flashcard otherFlashcard) {
        if (otherFlashcard == this) {
            return true;
        }

        return otherFlashcard != null
                && otherFlashcard.getFrontFace().equals(getFrontFace())
                && otherFlashcard.getBackFace().equals(getBackFace());
    }

    /**
     * Returns true if both flashcards have the same identity and data fields. This defines a stronger notion of
     * equality between two flashcards.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Flashcard)) {
            return false;
        }
        Flashcard flashcard = (Flashcard) o;
        return getFrontFace().equals(flashcard.getFrontFace())
                && getBackFace().equals(flashcard.getBackFace())
                && getTags().equals(flashcard.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrontFace(), getBackFace(), getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Front: ")
                .append(getFrontFace().text)
                .append(" Back: ")
                .append(getBackFace().text)
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
