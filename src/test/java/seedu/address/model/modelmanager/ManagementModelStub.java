package seedu.address.model.modelmanager;

import java.util.List;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.user.CardSrsData;
import seedu.address.model.user.User;

/**
 * A default management model stub that has all of the methods failing.
 */
public class ManagementModelStub implements ManagementModel {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Gets the lesson by index.
     */
    @Override
    public Lesson getLesson(int index) {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Opens the {@link Lesson} object at the specified index.
     *
     * @param index the index of the {@link Lesson} object to open
     * @return the name of the {@link Lesson} object
     */
    @Override
    public String openLesson(int index) {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Returns the opened lesson. A lesson is opened by calling {@link #openLesson(int)} and
     * closed by calling {@link #closeLesson()}. If there is no lesson currently opened,
     * this returns null.
     *
     * @return the opened {@link Lesson}. Null if there is no opened lesson.
     */
    @Override
    public Lesson getOpenedLesson() {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Gets the {@code Card} objects from the opened {@link Lesson} object.
     *
     * @return {@code Card} objects from the opened {@link Lesson} object.
     * Returns null if there are no cards found.
     */
    @Override
    public List<Card> getOpenedLessonCards() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<String> getOpenedLessonCoreHeaders() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<String> getOpenedLessonOptionalHeaders() {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Closes the opened {@link Lesson} object.
     * @return the name of the closed {@link Lesson} object
     */
    @Override
    public String closeLesson() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public LessonList getLessonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Lesson> getLessons() {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Adds the lesson.
     */
    @Override
    public void addLesson(Lesson lesson) {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Updates the lesson at the given index.
     */
    @Override
    public void setLesson(int index, Lesson updatedLesson) {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Removes the lesson at the given index from memory, and deletes its corresponding file.
     * @param index w
     */
    @Override
    public void deleteLesson(int index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public User getUser() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CardSrsData getCardSrsData(int hashCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addCardSrsData(CardSrsData card) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCardSrsData(CardSrsData card) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteCardSrsData(CardSrsData card) {
        throw new AssertionError("This method should not be called.");
    }
}
