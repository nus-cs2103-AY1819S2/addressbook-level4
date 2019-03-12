package seedu.address.testutil;

import static seedu.address.testutil.TypicalCards.CARD_BELGIUM;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;

/**
 * A utility class to help with building {@code Lesson} objects.
 */
public class LessonBuilder {
    public static final String DEFAULT_NAME = "Capitals";
    public static final List<String> DEFAULT_CORE_HEADERS = List.of("Country", "Capital");
    public static final List<String> DEFAULT_OPT_HEADERS = List.of("Hint");
    public static final int DEFAULT_QUESTION_CORE_INDEX = 0;
    public static final int DEFAULT_ANSWER_CORE_INDEX = 1;
    public static final List<Card> DEFAULT_CARDS = List.of(CARD_BELGIUM, CARD_JAPAN);

    private String name;
    private List<String> coreHeaders;
    private List<String> optionalHeaders;
    private int questionCoreIndex;
    private int answerCoreIndex;
    private List<Card> cards;

    public LessonBuilder() {
        name = DEFAULT_NAME;
        coreHeaders = DEFAULT_CORE_HEADERS;
        optionalHeaders = DEFAULT_OPT_HEADERS;
        questionCoreIndex = DEFAULT_QUESTION_CORE_INDEX;
        answerCoreIndex = DEFAULT_ANSWER_CORE_INDEX;
        cards = DEFAULT_CARDS;
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        name = lessonToCopy.getName();
        coreHeaders = lessonToCopy.getCoreHeaders();
        optionalHeaders = lessonToCopy.getOptionalHeaders();
        questionCoreIndex = lessonToCopy.getQuestionCoreIndex();
        answerCoreIndex = lessonToCopy.getAnswerCoreIndex();
        cards = lessonToCopy.getCards();
    }

    /**
     * Sets the {@code Lesson}'s name.
     */
    public LessonBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Parses the {@code coreHeaders} into a {@code List<Cards>} and set it to the {@code Lesson}'s
     * {@link #coreHeaders}.
     */
    public LessonBuilder withCoreHeaders(String ... coreHeaders) {
        this.coreHeaders = Arrays.asList(coreHeaders);
        return this;
    }

    /**
     * Parses the {@code optionalHeaders} into a {@code List<Cards>} and set it to the {@code Lesson}'s
     * {@link #optionalHeaders}.
     */
    public LessonBuilder withOptionalHeaders(String ... optionalHeaders) {
        this.optionalHeaders = Arrays.asList(optionalHeaders);
        return this;
    }

    /**
     * Parses the {@code cards} into a {@code List<Cards>} and set it to the {@code Lesson}'s {@link #cards}.
     */
    public LessonBuilder withCards(Card ... cards) {
        this.cards = Arrays.asList(cards);
        return this;
    }

    /**
     * @param index the index of the question in the {link Card} objects' list of cores.
     */
    public LessonBuilder withQuestionCoreIndex(int index) {
        questionCoreIndex = index;
        return this;
    }

    /**
     * @param index the index of the answer in the {link Card} objects' list of cores.
     */
    public LessonBuilder withAnswerCoreIndex(int index) {
        answerCoreIndex = index;
        return this;
    }

    /**
     * Builds and returns a lesson.
     *
     * @return a lesson
     */
    public Lesson build() {
        return new Lesson(name, coreHeaders, optionalHeaders, questionCoreIndex, answerCoreIndex, cards);
    }
}
