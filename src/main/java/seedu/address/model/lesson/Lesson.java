package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.card.Card.MIN_CORE_COUNT;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.MissingCoreException;

/**
 * Represents a lesson which stores multiple {@link Card} objects.
 */
public class Lesson {
    // Static fields
    public static final String EXCEPTION_INVALID_CORE_SIZE = "Invalid coreHeaders size.";
    public static final String EXCEPTION_INVALID_NAME = "Invalid name. Name must be specified.";
    public static final String EXCEPTION_INVALID_INDEX = "Invalid index.";
    public static final String EXCEPTION_CORE_SIZE_MISMATCH =
            "card's core size does not match lesson's coreHeaders size.";

    // Identity fields
    private String name;

    // Data fields
    /**
     * The headers for {@link Card} objects' cores in this lesson (e.g. Question and Answer).
     * All {@link Card} objects added to this lesson must have the same number of cores as the number of core headers.
     */
    private List<String> coreHeaders;
    /**
     * The headers for {@link Card} objects' isVisibleOptionals in this lesson (e.g. Hint).
     * {@link Card} objects added to this lesson need not have isVisibleOptionals which correspond to these headers.
     */
    private List<String> optionalHeaders;
    /**
     * The index of the Question in {@link Card} objects' cores.
     */
    private int questionCoreIndex;
    /**
     * The index of the Answer in {@link Card} objects' cores.
     */
    private int answerCoreIndex;
    private List<Card> cards;
    /**
     * Controls the visibility of optionals. If {@code isVisibleOptionals[i]} is set to {@code true}, it is shown.
     * If set to {@code false}, it is not shown.
     */
    private boolean[] isVisibleOptionals;

    /**
     * Creates a {@code Lesson} containing {@link Card} objects.
     *
     * @param name name of the {@code lesson}
     * @param coreHeaders all {@link Card} objects added to this lesson must have cores corresponding to
     * these headers.
     * @param optionalHeaders {@link Card} objects added to this lesson can have isVisibleOptionals corresponding to
     * these headers.
     */
    public Lesson(String name, List<String> coreHeaders, List<String> optionalHeaders) {
        requireAllNonNull(coreHeaders);

        setName(name);
        setCoreHeaders(coreHeaders);
        setOptionalHeaders(optionalHeaders);

        if (optionalHeaders.size() > 0) {
            isVisibleOptionals = new boolean[optionalHeaders.size()];
        }

        cards = new ArrayList<>();
    }

    /**
     * Creates a {@code Lesson} containing {@link Card} objects.
     *
     * @param name name of the {@code lesson}
     * @param coreCount specifies the number of cores in {@code cardFields}. Used to separate cores and optionals.
     * @param cardFields {@link Card} objects added to this lesson must have cores and optionals corresponding to
     * these headers.
     */
    public Lesson(String name, int coreCount, List<String> cardFields) {
        requireAllNonNull(cardFields);

        List<String> cores;
        List<String> optionals;

        try {
            cores = cardFields.subList(0, coreCount);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(EXCEPTION_CORE_SIZE_MISMATCH);
        }

        int optionalCount = cardFields.size() - coreCount;

        if (optionalCount == 0) {
            optionals = new ArrayList<>();
        } else {
            optionals = cardFields.subList(optionalCount, cardFields.size());
        }

        setName(name);
        setCoreHeaders(cores);
        setOptionalHeaders(optionals);

        if (optionals.size() > 0) {
            isVisibleOptionals = new boolean[optionals.size()];
        }

        cards = new ArrayList<>();
    }

    /**
     * Creates a {@code Lesson} containing {@link Card} objects.
     *
     * @param name name of the {@code lesson}
     * @param coreHeaders all {@link Card} objects added to this lesson must have cores corresponding to
     * these headers.
     * @param optionalHeaders {@link Card} objects added to this lesson can have isVisibleOptionals corresponding to
     * these headers.
     */
    public Lesson(String name, List<String> coreHeaders, List<String> optionalHeaders, int question, int answer,
                  List<Card> cards) throws MissingCoreException {
        this(name, coreHeaders, optionalHeaders);
        setQuestionAnswerIndices(question, answer);
        addCards(cards);
    }

    public void setName(String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException(EXCEPTION_INVALID_NAME);
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCoreHeaders(List<String> coreHeaders) {
        requireAllNonNull(coreHeaders);

        if (coreHeaders.size() < MIN_CORE_COUNT) {
            throw new IllegalArgumentException(EXCEPTION_INVALID_CORE_SIZE);
        }

        this.coreHeaders = coreHeaders;
    }

    public List<String> getCoreHeaders() {
        return coreHeaders;
    }

    public void setOptionalHeaders(List<String> coreHeaders) {
        this.optionalHeaders = optionalHeaders;
    }

    public List<String> getOptionalHeaders() {
        return optionalHeaders;
    }

    public boolean[] getIsVisibleOptionals() {
        return isVisibleOptionals;
    }

    public void setIsVisibleOptional(int index, boolean isShown) {
        try {
            if (index < 0 || index >= optionalHeaders.size()) {
                throw new IllegalArgumentException(EXCEPTION_INVALID_INDEX);
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(EXCEPTION_INVALID_INDEX);
        }

        isVisibleOptionals[index] = isShown;
    }

    public boolean getIsVisibleOptional(int index) {
        return isVisibleOptionals[index];
    }

    public void setQuestionAnswerIndices(int question, int answer) throws IllegalArgumentException {
        if (question < 0 || question >= coreHeaders.size()) {
            throw new IllegalArgumentException("Question index: " + question + " out of bounds");
        }

        if (answer < 0 || answer >= coreHeaders.size()) {
            throw new IllegalArgumentException("Answer index: " + answer + " out of bounds");
        }

        questionCoreIndex = question;
        answerCoreIndex = answer;
    }

    /**
     * Adds a {@link Card} object to the lesson.
     *
     * @param card card to add to the lesson
     */
    public void addCard(Card card) throws MissingCoreException {
        // All Card objects added must have the same number of cores as the number of core headers.
        if (card.getCores().size() != coreHeaders.size()) {
            throw new IllegalArgumentException(EXCEPTION_CORE_SIZE_MISMATCH);
        }

        int i = 0;
        for (String field: card.getCores()) {
            if (field.isEmpty()) {
                throw new MissingCoreException(i);
            }

            i++;
        }

        cards.add(card);
    }

    /**
     * Adds a {@link Card} object to the lesson.
     *
     * @param cardFields the Card's cores and optionals in this order. cardFields is separated into two separate
     * sub-lists of cores and optionals based on {@link #coreHeaders}'s {@code size()}.
     *
     * @throws MissingCoreException
     * @throws IllegalArgumentException
     */
    public void addCard(List<String> cardFields) throws MissingCoreException,
            IllegalArgumentException {
        requireAllNonNull(cardFields);

        List<String> cores;

        try {
            cores = cardFields.subList(0, coreHeaders.size());
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(EXCEPTION_CORE_SIZE_MISMATCH);
        }

        int optionalCount = cardFields.size() - coreHeaders.size();

        if (optionalCount == 0) {
            addCard(new Card(cores));
        } else {
            List<String> optionals = cardFields.subList(optionalCount, cardFields.size());
            addCard(new Card(cores, optionals));
        }
    }

    /**
     * Adds {@link Card} objects to the lesson.
     *
     * @param cards cards to add to the lesson
     */
    public void addCards(List<Card> cards) throws MissingCoreException {
        for (Card card: cards) {
            addCard(card);
        }
    }

    public int getCoreCount() {
        return coreHeaders.size();
    }

    public int getQuestionCoreIndex() {
        return questionCoreIndex;
    }

    public int getAnswerCoreIndex() {
        return answerCoreIndex;
    }

    public List<Card> getCards() {
        return cards;
    }

    public boolean isInitialized() {
        return cards != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append(name);
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return otherLesson.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coreHeaders, optionalHeaders, cards);
    }
}
