package braintrain.model.card;

import java.time.Instant;

import braintrain.model.card.exceptions.MissingCoreException;
import braintrain.model.lesson.Lesson;
import braintrain.model.user.CardData;

/**
 * Represents a flashcard contains both core fields(question and answer) and srs data.
 */
public class SrsCard {
    private Card card;
    private CardData cardData;
    private Lesson lesson;
    private int questionIndex;
    private int answerIndex;

    public SrsCard(Card card, CardData cardData, Lesson lesson) {
        this.card = card;
        this.cardData = cardData;
        this.lesson = lesson;

        questionIndex = lesson.getQuestionCoreIndex();
        answerIndex = lesson.getAnswerCoreIndex();
    }

    public SrsCard() {

    }

    public String getQuestion() throws MissingCoreException {
        return card.getCore(questionIndex);
    }

    public String getAnswer() throws MissingCoreException {
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
