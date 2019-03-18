package seedu.address.model.modelmanager.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Lessons;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;

/**
 * Represents the in-memory management of BrainTrain data.
 */
public class ManagementModelManager implements ManagementModel {
    private static final Logger logger = LogsCenter.getLogger(ManagementModelManager.class);

    private final Lessons lessons;
    private final UserPrefs userPrefs;
    /**
     * Initializes a ManagementModelManager with the given userPrefs.
     */
    public ManagementModelManager(ReadOnlyUserPrefs userPrefs, Lessons lessons) {
        super();
        requireAllNonNull(userPrefs);

        logger.fine("Initializing with user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        this.lessons = lessons;
    }

    public ManagementModelManager() {
        this(new UserPrefs(), new Lessons());
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

    //=========== Lessons ==================================================================================

    @Override
    public List<Lesson> getLessons() {
        return lessons.getLessons();
    }

    @Override
    public Lesson getLesson(int index) {
        Lesson lesson = lessons.getLesson(index);
        return lesson;
    }

    @Override
    public void addLesson(Lesson lesson) {
        lessons.addLesson(lesson);
    }

    @Override
    public void setLesson(int index, Lesson updatedLesson) {
        lessons.setLesson(index, updatedLesson);
    }

    @Override
    public void deleteLesson(int index) {
        lessons.deleteLesson(index);
    }

    //=========== Undo/Redo =================================================================================

    //    @Override
    //    public boolean canUndoAddressBook() {
    //        return versionedAddressBook.canUndo();
    //    }
    //
    //    @Override
    //    public boolean canRedoAddressBook() {
    //        return versionedAddressBook.canRedo();
    //    }
    //
    //    @Override
    //    public void undoAddressBook() {
    //        versionedAddressBook.undo();
    //    }
    //
    //    @Override
    //    public void redoAddressBook() {
    //        versionedAddressBook.redo();
    //    }
    //
    //    @Override
    //    public void commitAddressBook() {
    //        versionedAddressBook.commit();
    //    }

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
        return userPrefs.equals(other.userPrefs) && lessons.equals(other.lessons);
    }

}
