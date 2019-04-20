package seedu.address.model.modelmanager;

import java.util.List;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
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
     * Opens the {@link Lesson} object at the specified index.
     *
     * @param index the index of the {@link Lesson} object to open
     * @return the name of the {@link Lesson} object
     */
    String openLesson(int index);

    /**
     * Closes the opened {@link Lesson} object.
     * @return the name of the closed {@link Lesson} object
     */
    String closeLesson();

    /**
     * Returns the opened lesson. A lesson is opened by calling {@link #openLesson(int)} and
     * closed by calling {@link #closeLesson()}. If there is no lesson currently opened,
     * this returns null.
     *
     * @return the opened {@link Lesson}. Null if there is no opened lesson.
     */
    Lesson getOpenedLesson();

    /**
     * Gets the {@code Card} objects from the opened {@link Lesson} object.
     *
     * @return {@code Card} objects from the opened {@link Lesson} object.
     * Returns null if there are no cards found.
     */
    List<Card> getOpenedLessonCards();

    /**
     * @return the list of core headers in the opened {@link Lesson} object
     */
    List<String> getOpenedLessonCoreHeaders();

    /**
     * @return the list of optional headers in the opened {@link Lesson} object
     */
    List<String> getOpenedLessonOptionalHeaders();

    /**
     * Sets the opened lesson's question and answer indices.
     *
     * @param questionIndex the index of the core to set as question
     * @param answerIndex the index of the core to set as answer
     */
    void setOpenedLessonTestValues(int questionIndex, int answerIndex);

    /**
     * Adds a {@link Card} to the opened {@link Lesson} object.
     *
     * @param card {@link Card} to be added to the opened {@link Lesson} object
     */
    void addCardToOpenedLesson(Card card);

    /**
     * Checks if a card is already in the opened {@link Lesson} object.
     *
     * @param card the {@link Card} to look for in the opened {@link Lesson} object
     * @return returns true if the {@link Card} is already in the opened {@link Lesson} object
     */
    boolean openLessonHasCard(Card card);

    /**
     * Deletes the {@link Card} at the specified index from the opened {@link Lesson} object.
     *
     * @param index the index of the {@link Card} to be deleted from the opened {@link Lesson} object
     */
    void deleteCardFromOpenedLesson(int index);

    /**
     * Updated automatically by {@link #openLesson(int)} and {@link #closeLesson()} commands.
     *
     * @return true if there is an opened lesson; false otherwise
     */
    boolean isThereOpenedLesson();

    /**
     * Checks if there already is a lesson with the name specified in {@link LessonList}.
     *
     * @param name the {@link Lesson} name to look for in {@link LessonList}
     * @return returns true if there is a {@link Lesson} in {@link LessonList} with the
     * specified name; false otherwise
     */
    boolean hasLessonWithName(String name);

    /**
     * Toggles theme from light to dark scheme or vice versa, and returns the new theme.
     */
    String changeTheme();

    /**
     * Returns current theme from {@link UserPrefs} object.
     */
    String getTheme();

    /**
     * @return the {@link LessonList} object.
     */
    LessonList getLessonList();

    /**
     * Updates the {@link LessonList} object with a new copy.
     */
    void setLessonList(LessonList lessonList);

    /**
     * @return a {@link List} of {@link Lesson} objects in {@link LessonList}.
     */
    List<Lesson> getLessons();

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
