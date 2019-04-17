package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.exceptions.ApparelNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedFashionMatch versionedFashionMatch;
    private final UserPrefs userPrefs;
    private final FilteredList<Apparel> filteredApparels;
    private final SimpleObjectProperty<Apparel> selectedPerson = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given Fashion Match and userPrefs.
     */
    public ModelManager(ReadOnlyFashionMatch addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with fashion match: " + addressBook + " and user prefs " + userPrefs);

        versionedFashionMatch = new VersionedFashionMatch(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredApparels = new FilteredList<>(versionedFashionMatch.getApparelList());
        filteredApparels.addListener(this::ensureSelectedPersonIsValid);
    }

    public ModelManager() {
        this(new FashionMatch(), new UserPrefs());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== FashionMatch ================================================================================

    @Override
    public void setAddressBook(ReadOnlyFashionMatch addressBook) {
        versionedFashionMatch.resetData(addressBook);
    }

    @Override
    public ReadOnlyFashionMatch getAddressBook() {
        return versionedFashionMatch;
    }

    @Override
    public boolean hasApparel(Apparel apparel) {
        requireNonNull(apparel);
        return versionedFashionMatch.hasApparel(apparel);
    }

    @Override
    public void deleteApparel(Apparel target) {
        versionedFashionMatch.removeApparel(target);
    }

    @Override
    public void addApparel(Apparel apparel) {
        versionedFashionMatch.addApparel(apparel);
        updateFilteredApparelList(PREDICATE_SHOW_ALL_APPARELS);
    }

    @Override
    public void setPerson(Apparel target, Apparel editedApparel) {
        requireAllNonNull(target, editedApparel);

        versionedFashionMatch.setApparel(target, editedApparel);
    }

    //=========== Filtered Apparel List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Apparel} backed by the internal list of
     * {@code versionedFashionMatch}
     */
    @Override
    public ObservableList<Apparel> getFilteredApparelList() {
        return filteredApparels;
    }

    @Override
    public void updateFilteredApparelList(Predicate<Apparel> predicate) {
        requireNonNull(predicate);
        filteredApparels.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedFashionMatch.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedFashionMatch.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedFashionMatch.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedFashionMatch.redo();
    }

    @Override
    public void commitAddressBook() {
        versionedFashionMatch.commit();
    }

    //=========== Statistics ===============================================================================
    @Override
    public String getTotalColor() {
        ArrayList<String> outputTotalColor = new ArrayList<>();
        for (int i = 0; i <= filteredApparels.size() - 1; i++) {
            if (!outputTotalColor.contains(filteredApparels.get(i).getColor().toString())) {
                outputTotalColor.add(filteredApparels.get(i).getColor().toString());
            }
        }
        return "Total Different Colors : " + outputTotalColor.size();
    }

    @Override
    public String getFavApparel() {
        Map<String, Integer> hm = new HashMap();
        for (int i = 0; i < filteredApparels.size(); i++) {
            String key = filteredApparels.get(i).getName().toString();
            int value = filteredApparels.get(i).getUsageCount();
            hm.put(key, value);
        }
        int maxCount = -1;
        String output = null;
        for (Map.Entry<String, Integer> value : hm.entrySet()) {
            if (maxCount < value.getValue()) {
                maxCount = value.getValue();
                output = value.getKey();
            }
        }
        if (filteredApparels.size() == 0) {
            return "Your favorite apparel is none!";
        } else {
            return "Your favorite apparel is " + output;
        }
    }

    @Override
    public String getFavColor() {
        ArrayList<String> favColor = new ArrayList<>();
        for (int i = 0; i < filteredApparels.size(); i++) {
            favColor.add(filteredApparels.get(i).getColor().toString());
        }

        Map<String, Integer> hm = new HashMap();
        for (int i = 0; i < favColor.size(); i++) {
            String key = favColor.get(i);
            if (hm.containsKey(key)) {
                int value = hm.get(key);
                hm.put(key, value + 1);
            } else {
                hm.put(key, 1);
            }
        }
        int maxCount = -1;
        String output = null;
        for (Map.Entry<String, Integer> value : hm.entrySet()) {
            if (maxCount < value.getValue()) {
                maxCount = value.getValue();
                output = value.getKey();
            }
        }
        if (filteredApparels.size() == 0) {
            return "Your favorite color is none!";
        } else {
            return "Your favorite color is " + output;
        }
    }

    @Override
    public String getLeastFavApparel() {
        Map<String, Integer> hm = new HashMap();
        for (int i = 0; i < filteredApparels.size(); i++) {
            String key = filteredApparels.get(i).getName().toString();
            int value = filteredApparels.get(i).getUsageCount();
            hm.put(key, value);
        }
        int minCount = 10000;
        String output = null;
        for (Map.Entry<String, Integer> value : hm.entrySet()) {
            if (minCount > value.getValue()) {
                minCount = value.getValue();
                output = value.getKey();
            }
        }
        if (filteredApparels.size() == 0) {
            return "You should wear more of none of your apparels! :(";
        } else {
            return "You should wear more of " + output + " :(";
        }
    }

    @Override
    public String getCleanOrDirty() {
        float cleanCount = 0;
        float dirtyCount = 0;
        for (int i = 0; i < filteredApparels.size(); i++) {
            if (filteredApparels.get(i).isAvailable()) {
                cleanCount++;
            } else {
                dirtyCount++;
            }
        }
        if (cleanCount > dirtyCount) {
            return "Your wardrobe is quite clean! ("
                    + Math.round(cleanCount / (filteredApparels.size()) * 100) + "% clean)";
        } else if (dirtyCount > cleanCount) {
            return "Your wardrobe is getting dirty... ("
                    + Math.round(dirtyCount / (filteredApparels.size()) * 100) + "% dirty)";
        } else if (cleanCount == 0 && dirtyCount == 0) {
            return "Your wardrobe is empty!";
        } else if (cleanCount == dirtyCount) {
            return "Your wardrobe is equally clean and dirty :O";
        } else {
            return null;
        }
    }
    //=========== Selected apparel ===========================================================================

    @Override
    public ReadOnlyProperty<Apparel> selectedPersonProperty() {
        return selectedPerson;
    }

    @Override
    public Apparel getSelectedApparel() {
        return selectedPerson.getValue();
    }

    @Override
    public void setSelectedPerson(Apparel apparel) {
        if (apparel != null && !filteredApparels.contains(apparel)) {
            throw new ApparelNotFoundException();
        }
        selectedPerson.setValue(apparel);
    }

    /**
     * Ensures {@code selectedPerson} is a valid apparel in {@code filteredApparels}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Apparel> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected apparel, so we do not need to check that it is valid anymore.
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
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSameApparel(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the apparel that came before it in the list,
                // or clear the selection if there is no such apparel.
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
        return versionedFashionMatch.equals(other.versionedFashionMatch)
                && userPrefs.equals(other.userPrefs)
                && filteredApparels.equals(other.filteredApparels)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

    /**
     * Debugging tool to determine and output the property that is different.
     */
    public void validateEquality(ModelManager other) {
        if (!versionedFashionMatch.equals(other.versionedFashionMatch)) {
            System.out.println("versionedFashionMatch is different");
        }

        if (!userPrefs.equals(other.userPrefs)) {
            System.out.println("userPrefs is different");
        }

        if (!filteredApparels.equals(other.filteredApparels)) {
            System.out.println("filteredApparels is different");
        }

        if (!Objects.equals(selectedPerson.get(), other.selectedPerson.get())) {
            System.out.println("selectedPerson is different");
        }
    }

    @Override
    public String toString() {
        return "ModelManager{"
                + "versionedFashionMatch=" + versionedFashionMatch
                + ", userPrefs=" + userPrefs
                + ", filteredApparels=" + filteredApparels
                + ", selectedPerson=" + selectedPerson
                + '}';
    }
}
