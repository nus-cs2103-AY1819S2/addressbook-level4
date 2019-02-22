package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the card folder data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedCardFolder versionedCardFolder;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given cardFolder and userPrefs.
     */
    public ModelManager(ReadOnlyCardFolder cardFolder, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(cardFolder, userPrefs);

        logger.fine("Initializing with card folder: " + cardFolder + " and user prefs " + userPrefs);

        versionedCardFolder = new VersionedCardFolder(cardFolder);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(versionedCardFolder.getPersonList());
        filteredPersons.addListener(this::ensureSelectedPersonIsValid);
    }

    public ModelManager() {
        this(new CardFolder(), new UserPrefs());
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

    @Override
    public Path getCardFolderFilePath() {
        return userPrefs.getCardFolderFilePath();
    }

    @Override
    public void setCardFolderFilePath(Path cardFolderFilePath) {
        requireNonNull(cardFolderFilePath);
        userPrefs.setCardFolderFilePath(cardFolderFilePath);
    }

    //=========== CardFolder ================================================================================

    @Override
    public void setCardFolder(ReadOnlyCardFolder cardFolder) {
        versionedCardFolder.resetData(cardFolder);
    }

    @Override
    public ReadOnlyCardFolder getCardFolder() {
        return versionedCardFolder;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedCardFolder.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedCardFolder.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        versionedCardFolder.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedCardFolder.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedCardFolder}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoCardFolder() {
        return versionedCardFolder.canUndo();
    }

    @Override
    public boolean canRedoCardFolder() {
        return versionedCardFolder.canRedo();
    }

    @Override
    public void undoCardFolder() {
        versionedCardFolder.undo();
    }

    @Override
    public void redoCardFolder() {
        versionedCardFolder.redo();
    }

    @Override
    public void commitCardFolder() {
        versionedCardFolder.commit();
    }

    //=========== Selected person ===========================================================================

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return selectedPerson;
    }

    @Override
    public Person getSelectedPerson() {
        return selectedPerson.getValue();
    }

    @Override
    public void setSelectedPerson(Person person) {
        if (person != null && !filteredPersons.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

    /**
     * Ensures {@code selectedPerson} is a valid person in {@code filteredPersons}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPersonReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPerson.getValue());
            if (wasSelectedPersonReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedPerson.getValue());
                selectedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

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
        return versionedCardFolder.equals(other.versionedCardFolder)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

}
