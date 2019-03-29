package seedu.address.model.modelmanager;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.user.CardSrsData;
import seedu.address.model.user.User;

/**
 * Represents the in-memory management of BrainTrain data.
 */
public class ManagementModelManager implements ManagementModel {
    private static final Logger logger = LogsCenter.getLogger(ManagementModelManager.class);

    private final LessonList lessonList;
    private final UserPrefs userPrefs;
    private final User user;
    /**
     * Initializes a ManagementModelManager with the given userPrefs.
     */
    public ManagementModelManager(ReadOnlyUserPrefs userPrefs, LessonList lessonList, User user) {
        super();
        requireAllNonNull(userPrefs);

        logger.fine("Initializing with user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        this.lessonList = lessonList;
        this.user = user;
    }

    public ManagementModelManager() {
        this(new UserPrefs(), new LessonList(), new User());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    //=========== LessonList ==================================================================================

    @Override
    public LessonList getLessonList() {
        return lessonList;
    }

    @Override
    public List<Lesson> getLessons() {
        return lessonList.getLessons();
    }

    @Override
    public Lesson getLesson(int index) {
        Lesson lesson = lessonList.getLesson(index);
        return lesson;
    }

    /**
     * Opens the {@link Lesson} object at the specified index of {@link #lessonList}.
     *
     * @param index the index of the {@link Lesson} object in {@link #lessonList} to open
     * @return the name of the {@link Lesson} object
     */
    @Override
    public String openLesson(int index) {
        return lessonList.openLesson(index);
    }

    /**
     * Gets the lesson which is opened by calling {@link #openLesson(int)}.
     *
     * @return the opened {@link Lesson}. Returns null if there is no opened lesson.
     */
    @Override
    public Lesson getOpenedLesson() {
        return lessonList.getOpenedLesson();
    }

    /**
     * Gets the {@code Card} objects from the opened {@link Lesson} object.
     *
     * @return {@code Card} objects from the opened {@link Lesson} object.
     * Returns null if there are no cards found.
     */
    @Override
    public List<Card> getOpenedLessonCards() {
        return lessonList.getOpenedLessonCards();
    }

    @Override
    public List<String> getOpenedLessonCoreHeaders() {
        return lessonList.getOpenedLessonCoreHeaders();
    }

    @Override
    public List<String> getOpenedLessonOptionalHeaders() {
        return lessonList.getOpenedLessonOptionalHeaders();
    }

    @Override
    public void addCardToOpenedLesson(Card card) {
        lessonList.addCardToOpenedLesson(card);
    }

    @Override
    public void deleteCardFromOpenedLesson(int index) {
        lessonList.deleteCardFromOpenedLesson(index);
    }

    /**
     * Closes the opened {@link Lesson} object.
     * @return the name of the closed {@link Lesson} object
     */
    @Override
    public String closeLesson() {
        return lessonList.closeLesson();
    }

    @Override
    public void addLesson(Lesson lesson) {
        lessonList.addLesson(lesson);
    }

    @Override
    public void setLesson(int index, Lesson updatedLesson) {
        lessonList.setLesson(index, updatedLesson);
    }

    @Override
    public void deleteLesson(int index) {
        lessonList.deleteLesson(index);
    }

    //=========== User ==================================================================================
    @Override
    public User getUser() {
        return user;
    }

    @Override
    public CardSrsData getCardSrsData(int hashCode) {
        return user.getCard(hashCode);
    }

    @Override
    public void addCardSrsData(CardSrsData card) {
        user.addCard(card);
    }

    @Override
    public void setCardSrsData(CardSrsData card) {
        user.setCard(card);
    }

    @Override
    public void deleteCardSrsData(CardSrsData card) {
        user.deleteCard(card);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ManagementModelManager)) {
            return false;
        }

        // state check
        ManagementModelManager other = (ManagementModelManager) obj;
        return userPrefs.equals(other.userPrefs)
            && lessonList.equals(other.lessonList)
            && user.equals(other.user);
    }

}
