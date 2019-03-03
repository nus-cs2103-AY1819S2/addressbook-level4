package braintrain.model.card;

import java.time.Instant;

import braintrain.model.lesson.Lesson;
import braintrain.model.user.CardData;

/**
 * Represents a flashcard contains both core fields(question and answer) and srs data.
 */
public class SRSCard {
    private Card card;
    private CardData cardData;
    private Lesson lesson;
    private int questionIndex;
    private int answerIndex;

    public SRSCard(Card card, CardData cardData, Lesson lesson) {
        this.card = card;
        this.cardData = cardData;
        this.lesson = lesson;

        questionIndex = lesson.getQuestionIndex();
        answerIndex = lesson.getAnswerIndex();
    }
    public SRSCard() {

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
