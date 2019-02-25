package braintrain.model.quiz;

import static braintrain.commons.util.AppUtil.checkArgument;
import static braintrain.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

/**
 * Represents a partial of Card, only contains the necessary information for Quiz.
 */
public class QuizCard {

    public static final String MESSAGE_CONSTRAINTS = "Question/answer can take any values, and it"
        + " should not be blank or contain only whitespaces";

    private String question;
    private String answer;
    private List<String> opt;
    private boolean swap; //swap qn/ans
    private int totalAttempts;
    private int streak;

    public QuizCard(String question, String answer) {
        requireAllNonNull(question, answer);
        checkArgument(!question.trim().isEmpty() && !answer.isEmpty(), MESSAGE_CONSTRAINTS);

        this.question = question;
        this.answer = answer;
    }

    public QuizCard(String question, String answer, List<String> opt) {
        requireAllNonNull(question, answer, opt);
        checkArgument(!question.trim().isEmpty() && !answer.isEmpty(), MESSAGE_CONSTRAINTS);

        this.question = question;
        this.answer = answer;
        this.opt = opt;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getOpt() {
        return opt;
    }

    //
    //  public QuizCard(Card card, int totalAttempts, int streak) {
    //    this.card = card;
    //    this.totalAttempts = totalAttempts;
    //    this.streak = streak;
    //  }
    //
    //  public void setStreakTotal(boolean isCorrect) {
    //    this.totalAttempts += 1;
    //
    //    if (isCorrect) {
    //      this.streak += 1;
    //    } else {
    //      this.streak = 0;
    //    }
    //  }
}
