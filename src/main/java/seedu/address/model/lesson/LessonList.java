package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.card.Card;

/**
 * Represents a list of {@link Lesson} objects. It has helper functions to assist with the management
 * of {@link Lesson} objects.
 */
public class LessonList {
    //Static fields
    public static final String EXCEPTION_INVALID_INDEX = "Invalid index: %1$s";

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
     * Creates a new {@link LessonList} which is used to store a list of {@link Lesson} objects.
     */
    public LessonList() {
        lessons = new ArrayList<>();
    }

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
     * Returns all {@code Card} objects in the {@link #openedLesson}.
     *
     * @return all {@code Card} objects in the {@link #openedLesson}; null if there are none
     */
    public List<Card> getOpenedLessonCards() {
        return openedLesson.getCards();
    }

    public List<String> getOpenedLessonCoreHeaders() {
        return openedLesson.getCoreHeaders();
    }

    public List<String> getOpenedLessonOptionalHeaders() {
        return openedLesson.getOptionalHeaders();
    }

    public void addCardToOpenedLesson(Card card) {
        openedLesson.addCard(card);
    }

    public void deleteCardFromOpenedLesson(int index) {
        openedLesson.deleteCard(index);
    }

    /**
     * Sets {@link #openedLesson} to the lesson at the specified index.
     * All lesson-editing-related commands will apply to this lesson.
     *
     * @param index index of the lesson to be assigned to {@link #openedLesson}
     * @return the name of the lesson
     */
    public String openLesson(int index) {
        try {
            openedLesson = lessons.get(index);
            openedLessonIndex = index;
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
        setLesson(openedLessonIndex, openedLesson); // Save
        openedLesson = null;
        openedLessonIndex = -1;
        return lessonName;
    }

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

    @Override
    public int hashCode() {
        return lessons.hashCode();
    }
}
