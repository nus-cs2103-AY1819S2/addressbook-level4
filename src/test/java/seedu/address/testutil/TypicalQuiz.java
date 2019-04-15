package seedu.address.testutil;

import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2;

import java.util.List;

import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;

/**
 * A utility class containing a list of {@link QuizCard} objects used for testing.
 */
public class TypicalQuiz {
    public static final Quiz QUIZ_LEARN = new Quiz(SESSION_DEFAULT_2.generateSession(), SESSION_DEFAULT_2.getMode());

    private static List<QuizCard> generatedLearnCards = QUIZ_LEARN.getGeneratedQuizCardList();
    public static final QuizCard FIRST_REVIEW_CARD = generatedLearnCards.get(2);
    public static final QuizCard LAST_REVIEW_CARD = generatedLearnCards.get(5);
}
