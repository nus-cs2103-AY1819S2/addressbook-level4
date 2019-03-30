package seedu.finance.model;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.finance.commons.core.GuiSettings;
import seedu.finance.commons.core.LogsCenter;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Record;
import seedu.finance.model.record.exceptions.RecordNotFoundException;

/**
 * Represents the in-memory model of the finance tracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedFinanceTracker versionedFinanceTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Record> filteredRecords;
    private final SimpleObjectProperty<Record> selectedRecord = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given financeTracker and userPrefs.
     */
    public ModelManager(ReadOnlyFinanceTracker financeTracker, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(financeTracker, userPrefs);

        logger.fine("Initializing with finance tracker: " + financeTracker + " and user prefs " + userPrefs);

        versionedFinanceTracker = new VersionedFinanceTracker(financeTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredRecords = new FilteredList<>(versionedFinanceTracker.getRecordList());
        filteredRecords.addListener(this::ensureSelectedRecordIsValid);
    }

    public ModelManager() {
        this(new FinanceTracker(), new UserPrefs());
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
    public Path getFinanceTrackerFilePath() {
        return userPrefs.getFinanceTrackerFilePath();
    }

    @Override
    public void setFinanceTrackerFilePath(Path financeTrackerFilePath) {
        requireNonNull(financeTrackerFilePath);
        userPrefs.setFinanceTrackerFilePath(financeTrackerFilePath);
    }

    //=========== FinanceTracker ================================================================================

    @Override
    public void setFinanceTracker(ReadOnlyFinanceTracker financeTracker) {
        versionedFinanceTracker.resetData(financeTracker);
    }

    @Override
    public ReadOnlyFinanceTracker getFinanceTracker() {
        return versionedFinanceTracker;
    }

    @Override
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return versionedFinanceTracker.hasRecord(record);
    }

    @Override
    public void deleteRecord(Record target) {
        versionedFinanceTracker.removeRecord(target);
    }

    @Override
    public void addRecord(Record record) {
        versionedFinanceTracker.addRecord(record);
        updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORD);
    }

    @Override
    public void setRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        versionedFinanceTracker.setRecord(target, editedRecord);
    }

    @Override
    public boolean hasBudget() {
        return versionedFinanceTracker.hasBudget();
    }

    @Override
    public void addBudget(Amount amount) {
        requireNonNull(amount);

        versionedFinanceTracker.addBudget(amount);
    }

    @Override
    public void reverseFilteredRecordList() {
        versionedFinanceTracker.reverseRecordList();
    }

    //=========== Filtered Record List Accessors =============================================================

    /**
     * Returns the amount value of {@code budget} in an ObjectProperty wrapper
     */
    @Override
    public ObjectProperty<Amount> getBudget() {
        return versionedFinanceTracker.getBudget();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Record} backed by the internal list of
     * {@code versionedFinanceTracker}
     */
    @Override
    public ObservableList<Record> getFilteredRecordList() {
        return filteredRecords;
    }

    @Override
    public void updateFilteredRecordList(Predicate<Record> predicate) {
        requireNonNull(predicate);
        filteredRecords.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoFinanceTracker() {
        return versionedFinanceTracker.canUndo();
    }

    @Override
    public boolean canRedoFinanceTracker() {
        return versionedFinanceTracker.canRedo();
    }

    @Override
    public void undoFinanceTracker() {
        versionedFinanceTracker.undo();
    }

    @Override
    public void redoFinanceTracker() {
        versionedFinanceTracker.redo();
    }

    @Override
    public void commitFinanceTracker() {
        versionedFinanceTracker.commit();
    }

    //=========== Selected record ===========================================================================

    @Override
    public ReadOnlyProperty<Record> selectedRecordProperty() {
        return selectedRecord;
    }

    @Override
    public Record getSelectedRecord() {
        return selectedRecord.getValue();
    }

    @Override
    public void setSelectedRecord(Record record) {
        if (record != null && !filteredRecords.contains(record)) {
            throw new RecordNotFoundException();
        }
        selectedRecord.setValue(record);
    }

    /**
     * Ensures {@code selectedRecord} is a valid record in {@code filteredRecords}.
     * @param change
     */
    private void ensureSelectedRecordIsValid(ListChangeListener.Change<? extends Record> change) {
        while (change.next()) {
            if (selectedRecord.getValue() == null) {
                // null is always a valid selected record, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedRecordReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedRecord.getValue());
            if (wasSelectedRecordReplaced) {
                // Update selectedRecord to its new value.
                int index = change.getRemoved().indexOf(selectedRecord.getValue());
                selectedRecord.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedRecordRemoved = change.getRemoved().stream()
                    .anyMatch(removedRecord -> selectedRecord.getValue().isSameRecord(removedRecord));
            if (wasSelectedRecordRemoved) {
                // Select the record that came before it in the list,
                // or clear the selection if there is no such record.
                selectedRecord.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
        return versionedFinanceTracker.equals(other.versionedFinanceTracker)
                && userPrefs.equals(other.userPrefs)
                && filteredRecords.equals(other.filteredRecords)
                && Objects.equals(selectedRecord.get(), other.selectedRecord.get());
    }

}
