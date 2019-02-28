package braintrain.model;

import static braintrain.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import braintrain.commons.core.GuiSettings;
import braintrain.commons.core.LogsCenter;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    /**
     * Initializes a ModelManager with the given userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(userPrefs);

        logger.fine("Initializing with user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
    }

    public ModelManager() {
        this(new UserPrefs());
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
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs);
    }

}
