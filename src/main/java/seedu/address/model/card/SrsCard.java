package seedu.address.model.card;

import java.time.Instant;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.user.CardSrsData;

/**
 * Represents a flashcard contains both core fields(question and answer) and srs data.
 */
public class SrsCard {
    private Card card;
    private CardSrsData cardData;
    private Lesson lesson;
    private int questionIndex;
    private int answerIndex;

    public SrsCard(Card card, CardSrsData cardData, Lesson lesson) {
        this.card = card;
        this.cardData = cardData;
        this.lesson = lesson;

        questionIndex = lesson.getQuestionCoreIndex();
        answerIndex = lesson.getAnswerCoreIndex();
    }

    public SrsCard() {

    }

    public String getQuestion() {
        return card.getCore(questionIndex);
    }

    public String getAnswer() {
        return card.getCore(answerIndex);
    }

    public Instant getSrsDueDate() {
        return cardData.getSrsDueDate();
    }

    public int getHashcode() {
        return cardData.getHashCode();
    }

    public int getNumOfAttempts() {
        return cardData.getNumOfAttempts();
    }

    public int getStreak() {
        return cardData.getStreak();
    }

    public Lesson getLesson() {
        return lesson;
    }
}
