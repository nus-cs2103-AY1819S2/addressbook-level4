package seedu.address.model.modelmanager.management;

import java.util.List;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.lesson.Lesson;
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
     * Gets the entire list of lessons.
     */
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
