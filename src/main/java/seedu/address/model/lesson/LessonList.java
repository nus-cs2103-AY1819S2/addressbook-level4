package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.card.Card;

/**
 * Represents a list of {@link Lesson} objects. It has helper functions to assist with the management
 * of {@link Lesson} objects.
 */
public class LessonList {
    //Static fields
    public static final String EXCEPTION_INVALID_INDEX = "Invalid index: %1$s.";

    // Data fields
    /**
     * The list of {@link Lesson} objects.
     */
    private List<Lesson> lessons;
    /**
     * The {@link Lesson} object currently in focus. All lesson-editing-related commands will apply
     * to this lesson.
     */
    private Lesson openedLesson = null; // The lesson currently being edited

    /**
     * The index of the  {@link #openedLesson} object. Used when {@link #deleteLesson(int)} is
     * called to determine if {@link #openedLesson} should be set to null.
     */
    private int openedLessonIndex = -1;

    /**
     * Flag for whether there is an opened lesson. Determines if there is an opened lesson which can
     * be edited.
     */
    private boolean isThereOpenedLesson = false;

    /**
     * Creates a new {@link LessonList} which is used to store a list of {@link Lesson} objects.
     */
    public LessonList() {
        lessons = new ArrayList<>();
    }

    // General lesson commands
    /**
     * Returns the list of {@link #lessons}.
     *
     * @return the list of {@link #lessons}
     */
    public List<Lesson> getLessons() {
        return lessons;
    }

    /**
     * Returns the {@link Lesson} at the specified position in {@link #lessons}.
     *
     * @param index index of the lesson to return
     * @return the lesson at the specified position in {@link #lessons}
     */
    public Lesson getLesson(int index) {
        try {
            return lessons.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(EXCEPTION_INVALID_INDEX + index);
        }
    }

    /**
     * Checks if there already is a lesson with the name specified.
     *
     * @param name the lesson name to look for in LessonList
     * @return returns true if there is a lesson in LessonList with the specified name; false otherwise
     */
    public boolean hasLessonWithName(String name) {
        for (Lesson lesson : lessons) {
            if (lesson.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a card is already in {@link #openedLesson}.
     *
     * @param card the {@link Card} to look for in {@link #openedLesson}
     * @return returns true if the {@link Card} is already in {@link #openedLesson}
     */
    public boolean openLessonHasCard(Card card) {
        requireAllNonNull(openedLesson, card);
        return openedLesson.hasCard(card);
    }

    /**
     * Adds a {@link Lesson} object to {@link #lessons}.
     *
     * @param lesson {@link Lesson} to be added to {@link #lessons}
     */
    public void addLesson(Lesson lesson) {
        requireNonNull(lesson);
        lessons.add(lesson);
    }


    /**
     * Deletes a {@link Lesson} object from {@link #lessons}.
     *
     * @param index index of the lesson to delete
     */
    public void deleteLesson(int index) {
        try {
            if (openedLesson != null && openedLessonIndex == index) {
                closeLesson();
            }

            lessons.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(EXCEPTION_INVALID_INDEX + index);
        }
    }

    /**
     * Sets the {@link Lesson} object at index of {@link #lessons}.
     * @param index index of the lesson to set
     * @param newLesson set the old lesson to this lesson
     */
    public void setLesson(int index, Lesson newLesson) {
        requireNonNull(newLesson);
        lessons.set(index, newLesson);
    }

    // Opened lesson commands
    /**
     * Returns the {@link #openedLesson}. A lesson is opened by calling {@link #openLesson(int)} and
     * closed by calling {@link #closeLesson()}. If there is no lesson currently opened, this returns
     * null.
     *
     * @return the {@link Lesson} object in {@link #openedLesson}. Null if there is no opened lesson.
     */
    public Lesson getOpenedLesson() {
        return openedLesson;
    }

    /**
     * @return all {@code Card} objects in the {@link #openedLesson}; null if there are none
     */
    public List<Card> getOpenedLessonCards() {
        return openedLesson.getCards();
    }

    /**
     * @return the list of core headers in the {@link #openedLesson}
     */
    public List<String> getOpenedLessonCoreHeaders() {
        return openedLesson.getCoreHeaders();
    }

    /**
     * @return the list of optional headers in the {@link #openedLesson}
     */
    public List<String> getOpenedLessonOptionalHeaders() {
        return openedLesson.getOptionalHeaders();
    }

    /**
     * @param questionCoreIndex the index of the question in the {link Card} objects' list of cores.
     * @param answerCodeIndex the index of the answer in the {link Card} objects' list of cores.
     */
    public void setOpenedLessonQuestionAnswer(int questionCoreIndex, int answerCodeIndex) {
        requireNonNull(openedLesson);
        openedLesson.setQuestionAnswerIndices(questionCoreIndex, answerCodeIndex);
    }

    /**
     * Adds a {@link Card} to the {@link #openedLesson}.
     *
     * @param card {@link Card} to be added to the {@link #openedLesson}
     */
    public void addCardToOpenedLesson(Card card) {
        requireAllNonNull(openedLesson, card);
        openedLesson.addCard(card);
    }

    /**
     * Deletes the {@link Card} at the specified index from the {@link #openedLesson}.
     *
     * @param index the index of the {@link Card} to be deleted from the {@link #openedLesson}
     */
    public void deleteCardFromOpenedLesson(int index) {
        try {
            openedLesson.deleteCard(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(EXCEPTION_INVALID_INDEX + index);
        }
    }

    /**
     * Sets {@link #openedLesson} to the lesson at the specified index.
     * All lesson-editing commands will apply to this {@link #openedLesson}.
     *
     * @param index index of the lesson to be assigned to {@link #openedLesson}
     * @return the name of the lesson assigned as {@link #openedLesson}
     */
    public String openLesson(int index) {
        try {
            openedLesson = lessons.get(index);
            openedLessonIndex = index;
            isThereOpenedLesson = true;
            return openedLesson.getName();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(EXCEPTION_INVALID_INDEX + index);
        }
    }

    /**
     * Sets {@link #openedLesson} to null.
     */
    public String closeLesson() {
        requireNonNull(openedLesson);
        String lessonName = openedLesson.getName();
        setLesson(openedLessonIndex, openedLesson); // Save lesson to in-memory lesson list
        openedLesson = null;
        openedLessonIndex = -1;
        isThereOpenedLesson = false;
        return lessonName;
    }

    /**
     * Updated automatically by {@link #openLesson(int)} and {@link #closeLesson()} commands.
     *
     * @return true if there is an opened lesson; false otherwise
     */
    public boolean isThereOpenedLesson() {
        return isThereOpenedLesson;
    }

    // Other general functions
    /**
     * @param other the object to compare this LessonList against
     * @return true if the given object represents a LessonList equivalent to this string, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LessonList)) {
            return false;
        }

        LessonList otherLesson = (LessonList) other;
        return otherLesson.hashCode() == this.hashCode();
    }

    /**
     * Returns a hash code for this LessonList. The hash code for a LessonList object is the hash code of
     * the {@link #lessons} in this LessonList.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return lessons.hashCode();
    }
}
