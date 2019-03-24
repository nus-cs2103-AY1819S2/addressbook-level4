package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.card.Card.MIN_CORE_COUNT;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.card.Card;

/**
 * Represents a lesson which contains multiple flash cards and can be loaded into Quiz Mode
 * for testing. It stores multiple {@link Card} objects, and also {@link #coreHeaders} and
 * {@link #optionalHeaders} which are headers for the core and optional fields in the {@link Card} objects.
 *
 * <br><br>It also stores {@link #questionCoreIndex} and {@link #answerCoreIndex} which identifies the indices
 * of the question and answer in the {@link Card} objects' list of cores respectively.
 *
 * <br><br>A basic {@code Lesson} will have {@link Card} objects, where every {@link Card} could have
 * 2 {@link java.lang.String} objects in their list of cores representing question and answer, and optionally
 * have 1 {@link java.lang.String} object in their list of optionals representing hint.
 *
 * <br><br>The lesson's {@link #coreHeaders} could store "Country" and "Capital" to identify what the
 * questions and answers of the {@link Card} objects are. The lesson's {@link #optionalHeaders} could store
 * "Starts with" to identify what the hints of the {@link Card} objects are.
 */
public class Lesson {
    // Static fields
    public static final int DEFAULT_INDEX_QUESTION = 0;
    public static final int DEFAULT_INDEX_ANSWER = 1;

    public static final String EXCEPTION_INVALID_NAME = "Invalid name supplied.";
    public static final String EXCEPTION_INVALID_INDEX = "Invalid index: %1$s";
    public static final String EXCEPTION_INVALID_CORE_SIZE = "Invalid number of core headers supplied.";
    public static final String EXCEPTION_INVALID_CORE = "Invalid core header supplied.";
    public static final String EXCEPTION_INVALID_OPT = "Invalid optional header supplied.";
    public static final String EXCEPTION_CORE_SIZE_MISMATCH =
        "The cores of the card to be added do not match the core headers of this lesson.";

    // Identity fields
    /**
     * The name of the lesson which is displayed to the user. It is not used by {@link #equals(Object)}.
     */
    private String name;

    // Data fields
    /**
     * The headers for the cores of every {@link Card} object in this {@code lesson}.
     * These headers describe the core values in the {@link Card} objects.
     * All {@link Card} objects added to this lesson must have the same number of cores as the number of
     * core headers.
     */
    private List<String> coreHeaders;
    /**
     * The headers for the optionals of every {@link Card} object in this {@code lesson}.
     * These headers describe the optional values in the {@link Card} objects.
     */
    private List<String> optionalHeaders;
    /**
     * The index of the question in {@link Card} objects' cores.
     */
    private int questionCoreIndex = DEFAULT_INDEX_QUESTION;
    /**
     * The index of the answer in {@link Card} objects' cores.
     */
    private int answerCoreIndex = DEFAULT_INDEX_ANSWER;
    /**
     * The list of {@link Card} objects.
     */
    private List<Card> cards;
    /**
     * Controls the visibility of optionals. If {@code isVisibleOptionals[i]} is set to {@code true},
     * the optional at index {@code i} of every {@see Card} object's list of optionals is shown.
     * If set to {@code false}, all {@see Card} object's optionals at index {@code i} is hidden.
     */
    private boolean[] isVisibleOptionals;

    /**
     * Creates a {@code Lesson}.
     *
     * @param name name of the {@code lesson}
     * @param coreHeaders all {@link Card} objects added to this lesson must have cores corresponding to
     * these headers.
     * @param optionalHeaders if the {@code i}th element of {@link #isVisibleOptionals} is set to true,
     *                        the {@code i}th element of optionalHeaders is shown alongside the {@code i}th
     *                        element of every {@link Card} objects' list of optionals, if it is available.
     */
    public Lesson(String name, List<String> coreHeaders, List<String> optionalHeaders) {
        requireAllNonNull(coreHeaders);

        setName(name);
        setCoreHeaders(coreHeaders);
        setOptionalHeaders(optionalHeaders);

        if (optionalHeaders != null && optionalHeaders.size() > 0) {
            isVisibleOptionals = new boolean[optionalHeaders.size()];
        }

        cards = new ArrayList<>();
    }

    /**
     * Creates a {@code Lesson}.
     *
     * @param name name of the {@code lesson}
     * @param noOfCoreHeaders specifies the number of core headers in {@code headers}. Used to separate core
     *                        headers from optional headers.
     * @param headers contains core and optional headers. All {@link Card} objects added to this lesson must
     *                have cores corresponding to the core headers specified.
     */
    public Lesson(String name, int noOfCoreHeaders, List<String> headers) {
        requireAllNonNull(headers);

        List<String> cores;
        List<String> optionals;

        cores = headers.subList(0, noOfCoreHeaders);
        int optionalCount = headers.size() - noOfCoreHeaders;

        if (optionalCount == 0) {
            optionals = new ArrayList<>();
        } else {
            optionals = headers.subList(optionalCount, headers.size());
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
     * these headers
     * @param optionalHeaders if the {@code i}th element of {@link #isVisibleOptionals} is set to true,
     *                        the {@code i}th element of optionalHeaders is shown alongside the {@code i}th
     *                        element of every {@link Card} objects' list of optionals, if it is available
     * @param questionCoreIndex the index of the question in {@link Card} objects' cores.
     * @param answerCoreIndex the index of the answer in {@link Card} objects' cores.
     * @param cards list of {@link Card} objects to be added to this lesson
     */
    public Lesson(String name, List<String> coreHeaders, List<String> optionalHeaders,
                  int questionCoreIndex, int answerCoreIndex, List<Card> cards) {
        this(name, coreHeaders, optionalHeaders);
        setQuestionAnswerIndices(questionCoreIndex, answerCoreIndex);
        addCards(cards);
    }

    /**
     * Sets the name of the lesson. It is not used by {@link #equals(Object)}.
     *
     * @param name the name of the lesson which is displayed to the user.
     */
    public void setName(String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException(EXCEPTION_INVALID_NAME);
        }

        this.name = name;
    }

    /**
     * Gets the name of the lesson for displaying to the user.
     *
     * @return name of the lesson
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the core headers which describes what every {@link Card} object's cores are.
     * @param coreHeaders all {@link Card} objects added to this lesson must have cores corresponding to
     *      * these headers
     */
    public void setCoreHeaders(List<String> coreHeaders) {
        requireAllNonNull(coreHeaders);

        if (coreHeaders.size() < MIN_CORE_COUNT) {
            throw new IllegalArgumentException(EXCEPTION_INVALID_CORE_SIZE);
        }

        this.coreHeaders = coreHeaders;
    }

    /**
     * Returns the core headers which describes what every {@link Card} object's cores are.
     * @return the core headers of this lesson
     */
    public List<String> getCoreHeaders() {
        return coreHeaders;
    }


    /**
     * @return the number of core headers in this lesson
     */
    public int getCoreHeaderSize() {
        return coreHeaders.size();
    }

    /**
     * Sets the optional headers which describes what every {@link Card} object's optionals are.
     * @param optionalHeaders all {@link Card} objects added to this lesson can have optionals corresponding
     *                        to these headers
     */
    public void setOptionalHeaders(List<String> optionalHeaders) {
        if (optionalHeaders == null) {
            this.optionalHeaders = new ArrayList<>();
            return;
        }

        this.optionalHeaders = optionalHeaders;

        if (optionalHeaders.size() > 0) {
            isVisibleOptionals = new boolean[optionalHeaders.size()];
        }
    }

    /**
     * Returns the optional headers which describes what every {@link Card} object's optional are.
     * @return the optional headers of this lesson
     */
    public List<String> getOptionalHeaders() {
        return optionalHeaders;
    }

    /**
     * @param index the index of the question in the {link Card} objects' list of cores.
     */
    public void setQuestionCoreIndex(int index) {
        if (index < 0 || index >= coreHeaders.size()) {
            throw new IllegalArgumentException(String.format(EXCEPTION_INVALID_INDEX, index));
        }

        questionCoreIndex = index;
    }

    /**
     * @param index the index of the answer in the {link Card} objects' list of cores.
     */
    public void setAnswerCoreIndex(int index) {
        if (index < 0 || index >= coreHeaders.size()) {
            throw new IllegalArgumentException(String.format(EXCEPTION_INVALID_INDEX, index));
        }

        answerCoreIndex = index;
    }

    /**
     * @param questionCoreIndex the index of the question in the {link Card} objects' list of cores.
     * @param answerCodeIndex the index of the answer in the {link Card} objects' list of cores.
     */
    public void setQuestionAnswerIndices(int questionCoreIndex, int answerCodeIndex) {
        setQuestionCoreIndex(questionCoreIndex);
        setAnswerCoreIndex(answerCodeIndex);
    }

    /**
     * @return the index of the question in the {link Card} objects' list of cores.
     */
    public int getQuestionCoreIndex() {
        return questionCoreIndex;
    }

    /**
     * @return the index of the answer in the {link Card} objects' list of cores.
     */
    public int getAnswerCoreIndex() {
        return answerCoreIndex;
    }

    /**
     * Returns a {@link #isVisibleOptionals} which controls the visibility of optionals. If
     * {@code isVisibleOptionals[i]} is set to {@code true}, the optional at index {@code i} of every
     * {@see Card} object's list of optionals is shown. If set to {@code false}, all {@see Card} object's
     * optionals at index {@code i} is hidden.
     *
     * @return the list of booleans corresponding to the visibility of headers in {@link #optionalHeaders}
     */
    public boolean[] getIsVisibleOptionals() {
        return isVisibleOptionals;
    }

    /**
     * Sets the visibility of the {@code i}th element of {@link #isVisibleOptionals} to {@code isShown}.
     * If {@code isVisibleOptionals[i]} is set to {@code true}, the optional at index {@code i} of every
     * {@see Card} object's list of optionals is shown. If set to {@code false}, all {@see Card} object's
     * optionals at index {@code i} is hidden.
     *
     * @param index the index of the optional to change visibility of
     * @param isShown if set to true, the optional at {@code index} will be shown during quiz mode
     */
    public void setIsVisibleOptional(int index, boolean isShown) {
        if (index < 0 || index >= isVisibleOptionals.length) {
            throw new IllegalArgumentException(EXCEPTION_INVALID_INDEX + index);
        }

        isVisibleOptionals[index] = isShown;
    }

    /**
     * @param index the index of the {@link #isVisibleOptionals} element to get
     * @return the visibility of the optional at {@code index}
     */
    public boolean getIsVisibleOptional(int index) {
        return isVisibleOptionals[index];
    }

    /**
     * Adds a {@link Card} object to {@link #cards}.
     *
     * @param card {@link Card} to be added to {@link #cards}
     */
    public void addCard(Card card) {
        // All Card objects added must have the same number of cores as the number of core headers.
        if (card.getCores().size() != coreHeaders.size()) {
            throw new IllegalArgumentException(EXCEPTION_CORE_SIZE_MISMATCH);
        }

        for (String field: card.getCores()) {
            if (field.isEmpty()) {
                throw new IllegalArgumentException(EXCEPTION_CORE_SIZE_MISMATCH);
            }
        }

        cards.add(card);
    }

    /**
     * Adds a {@link Card} object to the lesson.
     * {@code fields} are separated into two separate sub-lists of cores and optionals based on
     * {@link #coreHeaders}'s {@code size()}.
     *
     * @param fields the {@link Card}'s cores and optionals in this order
     */
    public void addCard(List<String> fields) {
        requireAllNonNull(fields);

        List<String> cores = fields.subList(0, coreHeaders.size());

        int optionalCount = fields.size() - coreHeaders.size();

        if (optionalCount == 0) {
            addCard(new Card(cores));
        } else {
            List<String> optionals = fields.subList(optionalCount, fields.size());
            addCard(new Card(cores, optionals));
        }
    }

    /**
     * Adds {@link Card} objects to the lesson.
     *
     * @param cards a list of {@link Card} objects to be added to the lesson
     */
    public void addCards(List<Card> cards) {
        for (Card card: cards) {
            addCard(card);
        }
    }

    /**
     * @return the list of {@link Card} objects in this lesson
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * @return the number of {@link Card} objects in this lesson
     */
    public int getCardCount() {
        return cards.size();
    }

    /**
     * @return true if there is at least 1 {@link Card} in this lesson
     */
    public boolean hasCards() {
        return cards != null && cards.size() != 0;
    }

    /**
     * Two {@code Lesson} objects with the same hashcode are equivalent.
     * They need not have the same {@link #name}.
     *
     * @return the hashcode generated using {@link #coreHeaders}, {@link #optionalHeaders},
     * {@link #questionCoreIndex}, {@link #answerCoreIndex} and {@link #cards} as input.
     */
    @Override
    public int hashCode() {
        return Objects.hash(coreHeaders, optionalHeaders, questionCoreIndex, answerCoreIndex,
                cards);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name)
                .append(": Cores [").append(String.join(", ", coreHeaders))
                .append("], Optionals [");
        if (optionalHeaders.size() > 0) {
            builder.append(String.join(", ", optionalHeaders));
        }

        builder.append("], Cards [").append(getCardCount()).append("]");

        return builder.toString();
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
}
