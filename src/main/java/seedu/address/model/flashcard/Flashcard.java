package seedu.address.model.flashcard;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
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
    private final ImagePath imagePath;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final Statistics statistics;
    private final Proficiency proficiency;

    /**
     * Every field must be present and not null.
     */
    public Flashcard(Face frontFace, Face backFace, Statistics statistics, Proficiency proficiency, Set<Tag> tags) {
        requireAllNonNull(frontFace, backFace, statistics, proficiency, tags);
        this.frontFace = frontFace;
        this.backFace = backFace;
        this.imagePath = new ImagePath(Optional.empty());
        this.statistics = statistics;
        this.proficiency = proficiency;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Flashcard(Face frontFace, Face backFace, ImagePath imagePath, Statistics statistics, Proficiency proficiency,
                     Set<Tag> tags) {
        requireAllNonNull(frontFace, backFace, statistics, tags, proficiency);
        this.frontFace = frontFace;
        this.backFace = backFace;
        this.imagePath = imagePath;
        this.statistics = statistics;
        this.proficiency = proficiency;
        this.tags.addAll(tags);
    }

    public Proficiency getProficiency() {
        return proficiency;
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

    public ImagePath getImagePath() {
        return imagePath;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * @return success rate of the flashcard in quiz or review in percentage.
     */
    public Double getSuccessRate() {
        return getStatistics().getSuccessRate() * 100;
    }

    /**
     * @return the status of this flashcard in the quiz.
     */
    public String getQuizSrsStatus() {
        return getProficiency().getQuizSrsStatus();
    }

    /**
     * Returns a flashcard with updated statistics and SRS info based on the result of the quiz.
     *
     * @param isSuccess success in quiz mode
     * @param isQuizSrs is it srs mode in the quiz
     * @return the modified flashcard
     */
    public Flashcard quizAttempt(boolean isSuccess, boolean isQuizSrs) {
        Proficiency rProficiency = proficiency;
        if (isQuizSrs) {
            rProficiency = rProficiency.quizAttempt(isSuccess);
        }
        Statistics rStatistics = statistics.quizAttempt(isSuccess);
        return new Flashcard(frontFace, backFace, imagePath, rStatistics, rProficiency, tags);
    }

    public boolean isIncludedInCurrentQuiz() {
        return getProficiency().isIncludedInCurrentQuiz();
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
                && otherFlashcard.getBackFace().equals(getBackFace())
                && otherFlashcard.getImagePath().equals(getImagePath());
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
                && getImagePath().equals(flashcard.getImagePath())
                && getTags().equals(flashcard.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrontFace(), getBackFace(), getImagePath(), getTags(), getProficiency());
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
