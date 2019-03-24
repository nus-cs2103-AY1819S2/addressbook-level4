package seedu.address.model.modelmanager;

import java.util.List;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.user.CardSrsData;
import seedu.address.model.user.User;

/**
 * The API of the ManagementModel component.
 */
public interface ManagementModel extends Model {
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);


    /**
     * Gets the lesson by index.
     */
    Lesson getLesson(int index);

    /**
     * Open the lesson by index.
     */
    String openLesson(int index);

    /**
     * Gets the entire list of lessons.
     */
    List<Lesson> getLessonList();

    /**
     * Adds the lesson.
     */
    void addLesson(Lesson lesson);

    /**
     * Updates the lesson at the given index.
     */
    void setLesson(int index, Lesson updatedLesson);

    /**
     * Removes the lesson at the given index from memory, and deletes its corresponding file.
     * @param index w
     */
    void deleteLesson(int index);

    User getUser();

    CardSrsData getCardSrsData(int hashCode);

    void addCardSrsData(CardSrsData card);

    void setCardSrsData(CardSrsData card);

    void deleteCardSrsData(CardSrsData card);
}
