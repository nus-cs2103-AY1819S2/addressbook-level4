package seedu.address.model.modelmanager;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
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
    public List<Lesson> getLessonList() {
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
