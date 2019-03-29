package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Option;
import seedu.address.model.card.Question;
import seedu.address.model.card.Score;
import seedu.address.model.hint.Hint;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Card objects.
 */
public class CardBuilder {

    public static final String DEFAULT_QUESTION = "What is the powerhouse of a cell?";
    public static final String DEFAULT_ANSWER = "Mitochondrion";
    public static final String DEFAULT_SCORE = "0/0";

    private Question question;
    private Answer answer;
    private Score score;
    private Set<Option> options;
    private Set<Hint> hints;

    public CardBuilder() {
        question = new Question(DEFAULT_QUESTION);
        answer = new Answer(DEFAULT_ANSWER);
        score = new Score(DEFAULT_SCORE);
        options = new HashSet<>();
        hints = new HashSet<>();
    }

    /**
     * Initializes the CardBuilder with the data of {@code cardToCopy}.
     */
    public CardBuilder(Card cardToCopy) {
        question = cardToCopy.getQuestion();
        answer = cardToCopy.getAnswer();
        score = cardToCopy.getScore();
        options = new HashSet<>(cardToCopy.getOptions());
        hints = new HashSet<>(cardToCopy.getHints());
    }

    /**
     * Sets the {@code Question} of the {@code Card} that we are building.
     */
    public CardBuilder withQuestion(String question) {
        this.question = new Question(question);
        return this;
    }

    /**
     * Parses the {@code hints} into a {@code Set<Hint>} and set it to the {@code Card} that we are building.
     */
    public CardBuilder withHint(String ... hint) {
        this.hints = SampleDataUtil.getHintSet(hint);
        return this;
    }

    /**
     * Parses the {@code options} into a {@code Set<Option>} and set it to the {@code Card} that we are building.
     */
    public CardBuilder withOptions(String ... options) {
        this.options = SampleDataUtil.getOptionSet(options);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code Card} that we are building.
     */
    public CardBuilder withAnswer(String answer) {
        this.answer = new Answer(answer);
        return this;
    }

    /**
     * Sets the {@code Score} of the {@code Card} that we are building.
     */
    public CardBuilder withScore(String score) {
        this.score = new Score(score);
        return this;
    }

    public Card build() {
        return new Card(question, answer, score, options, hints);
    }

}
