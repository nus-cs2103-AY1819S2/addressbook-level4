package seedu.address.model.session;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.management.StartCommand;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.srscard.SrsCard;
import seedu.address.model.user.CardSrsData;


/**
 * Represents a session that stores cards based on srs data.
 * It will be generated based on user input when staring a quiz
 * and pass the needed list of {@link QuizCard} to the quiz session.
 */
public class Session {
    public static final int CARD_COUNT_MINIMUM = 1;

    private String name;
    private QuizMode mode;
    private int cardCount;
    private int lessonIndex;
    private List<SrsCard> srsCards;

    /**
     * Creates a session with the {@code name} of the {@link seedu.address.model.lesson.Lesson}
     * which will be used for users of the current {@link seedu.address.model.lesson.Lesson}
     * in {@link seedu.address.model.quiz.Quiz}.
     * @param name {@code name} of the lesson
     * @param cardCount number of cards that users want to be tested
     * @param mode {@link QuizMode} for users
     */
    public Session(String name, int cardCount, QuizMode mode) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Invalid name");
        }
        if (cardCount < CARD_COUNT_MINIMUM) {
            throw new IllegalArgumentException("CardCount should not be less than 1 in a single session");
        }
        if ((mode != QuizMode.LEARN) & (mode != QuizMode.REVIEW) & (mode != QuizMode.PREVIEW)
            & (mode != QuizMode.DIFFICULT)) {
            throw new IllegalArgumentException("Invalid mode");
        }
        this.name = name;
        this.cardCount = cardCount;
        this.mode = mode;
    }

    public Session(String name, int cardCount, QuizMode mode, List<SrsCard> srsCards) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Invalid name");
        }
        if (cardCount < CARD_COUNT_MINIMUM) {
            throw new IllegalArgumentException("CardCount should not be less than 1 in a single session");
        }
        if ((mode != QuizMode.LEARN) & (mode != QuizMode.REVIEW) & (mode != QuizMode.PREVIEW)
            & (mode != QuizMode.DIFFICULT)) {
            throw new IllegalArgumentException("Invalid mode");
        }

        this.name = name;
        this.cardCount = cardCount;
        this.mode = mode;
        this.srsCards = srsCards;
    }

    public Session(String name, QuizMode mode, List<SrsCard> srsCards) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Invalid name");
        }
        if ((mode != QuizMode.LEARN) & (mode != QuizMode.REVIEW) & (mode != QuizMode.PREVIEW)
            & (mode != QuizMode.DIFFICULT)) {
            throw new IllegalArgumentException("Invalid mode");
        }

        this.name = name;
        this.cardCount = 10;
        this.mode = mode;
        this.srsCards = srsCards;
    }

    public Session(int index, int cardCount, QuizMode mode) {
        if (cardCount < CARD_COUNT_MINIMUM) {
            throw new IllegalArgumentException("CardCount should not be less than 1 in a single session");
        }
        if ((mode != QuizMode.LEARN) & (mode != QuizMode.REVIEW) & (mode != QuizMode.PREVIEW)
                & (mode != QuizMode.DIFFICULT)) {
            throw new IllegalArgumentException("Invalid mode");
        }
        this.lessonIndex = index;
        this.cardCount = cardCount;
        this.mode = mode;
        this.name = "default";
    }

    /**
     * Creates the session when user input a {@link StartCommand}
     * @param index {@code index} shows the index of the {@link seedu.address.model.lesson.Lesson}
     *                           in {@link seedu.address.model.lesson.LessonList}
     * @param cardCount number of cards that users want to be tested
     * @param mode {@link QuizMode} for users
     * @param srsCards list of srsCards to generate the {@link QuizCard}
     */
    public Session(int index, int cardCount, QuizMode mode, List<SrsCard> srsCards) {
        if (cardCount < CARD_COUNT_MINIMUM) {
            throw new IllegalArgumentException("CardCount should not be less than 1 in a single session");
        }
        if ((mode != QuizMode.LEARN) & (mode != QuizMode.REVIEW) & (mode != QuizMode.PREVIEW)
                & (mode != QuizMode.DIFFICULT)) {
            throw new IllegalArgumentException("Invalid mode");
        }
        this.lessonIndex = index;
        this.cardCount = cardCount;
        this.mode = mode;
        this.name = "default";
        this.srsCards = srsCards;
    }

    /**
     * Generate a list of quizCards that will pass to quiz system.
     * @return the list of {@link QuizCard}
     */
    public List<QuizCard> generateSession() {
        SrsCard currentCard;
        List<QuizCard> quizCards = new ArrayList<>();

        for (int i = 0; i < cardCount; i++) {
            currentCard = srsCards.get(i);
            quizCards.add(new QuizCard(currentCard.getQuestion(), currentCard.getAnswer(),
                    currentCard.getHints(), currentCard.getQuestionHeader(), currentCard.getAnswerHeader()));
        }
        return quizCards;
    }

    /**
     * Returns the needed attributes.
     * @return Different attributes that will be needed later.
     */
    public QuizMode getMode() {
        return mode;
    }
    public int getLessonIndex() {
        return lessonIndex;
    }
    public int getCount() {
        return cardCount;
    }
    public void setCount(int count) {
        cardCount = count;
    }

    public String getName() {
        return name;
    }

    public List<SrsCard> getSrsCards() {
        return srsCards;
    }
    public List<SrsCard> getQuizSrsCards() {
        List<SrsCard> quizSrsCards = new ArrayList<>();
        for (int i = 0; i < cardCount; i++) {
            quizSrsCards.add(srsCards.get(i));
        }
        return quizSrsCards;
    }

    /**
     * Returns user profile after quiz ends.
     *
     * @param quizInformation from quiz.
     * @return List of {@link CardSrsData} to update the user data.
     */
    public List<CardSrsData> updateUserProfile(List<List<Integer>> quizInformation) {
        Instant currentDate = Instant.now();
        SrsCardsManager updateManager = new SrsCardsManager(this.getQuizSrsCards(), quizInformation, currentDate);
        return updateManager.updateCardData();
    }
}
