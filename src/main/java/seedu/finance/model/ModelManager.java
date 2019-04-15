package seedu.finance.model;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.finance.commons.core.GuiSettings;
import seedu.finance.commons.core.LogsCenter;
import seedu.finance.commons.exceptions.DataConversionException;
import seedu.finance.logic.commands.SummaryCommand.SummaryPeriod;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.budget.TotalBudget;
import seedu.finance.model.exceptions.CategoryBudgetExceedTotalBudgetException;
import seedu.finance.model.exceptions.SpendingInCategoryBudgetExceededException;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.Record;
import seedu.finance.model.record.exceptions.RecordNotFoundException;
import seedu.finance.storage.JsonFinanceTrackerStorage;
import seedu.finance.storage.StorageManager;

/**
 * Represents the in-memory model of the finance tracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedFinanceTracker versionedFinanceTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Record> filteredRecords;
    private final SimpleObjectProperty<Record> selectedRecord = new SimpleObjectProperty<>();

    private SummaryPeriod summaryPeriod;
    private Predicate<Record> recordSummaryPredicate;
    private int periodAmount;

    private Stack<Path> prevDataFiles = new Stack<>();
    private Stack<Path> undoPrevDataFiles = new Stack<>();

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

        this.summaryPeriod = defaultSummaryPeriod();
        this.recordSummaryPredicate = defaultRecordPredicate();
        this.periodAmount = defaultPeriodAmount();
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
    public boolean addRecord(Record record) {
        boolean budgetNotExceeded = versionedFinanceTracker.addRecord(record);
        updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORD);
        return budgetNotExceeded;
    }

    @Override
    public void setRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        versionedFinanceTracker.setRecord(target, editedRecord);
    }

    @Override
    public void addBudget(Budget budget) throws CategoryBudgetExceedTotalBudgetException {
        requireNonNull(budget);

        versionedFinanceTracker.addBudget(budget);
    }

    //================ Set File ================================================================================
    @Override
    public void addPreviousDataFile(Path path) {
        prevDataFiles.push(path);
    }

    @Override
    public Path removePreviousDataFile() {
        return prevDataFiles.pop();
    }

    @Override
    public void addUndoPreviousDataFile(Path path) {
        undoPrevDataFiles.push(path);
    }

    @Override
    public Path removeUndoPreviousDataFile() {
        return undoPrevDataFiles.pop();
    }

    @Override
    public void changeFinanceTrackerFile(Path path) {
        logger.fine("Change file path triggered: " + path);
        JsonFinanceTrackerStorage newStorage = new JsonFinanceTrackerStorage(path);
        Optional<ReadOnlyFinanceTracker> financeTrackerOptional;
        ReadOnlyFinanceTracker initialData;
        try {
            financeTrackerOptional = newStorage.readFinanceTracker();
            if (!financeTrackerOptional.isPresent()) {
                logger.info("Data file not found. A new empty FinanceTracker will be created with file name.");
                initialData = new FinanceTracker();
            } else {
                initialData = financeTrackerOptional.get();
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty FinanceTracker");
            initialData = new FinanceTracker();
        }
        setFinanceTrackerFilePath(path);
        setFinanceTracker(initialData);
        StorageManager.setFinanceTrackerStorage(newStorage);
    }

    //================ Category Budget =========================================================================
    //@author Jackimaru96
    public void addCategoryBudget(CategoryBudget budget) throws CategoryBudgetExceedTotalBudgetException,
            SpendingInCategoryBudgetExceededException {
        this.versionedFinanceTracker.addCategoryBudget(budget);
    }

    public HashSet<CategoryBudget> getCatBudget() {
        return this.versionedFinanceTracker.getCategoryBudget();
    }

    @Override
    public void reverseFilteredRecordList() {
        versionedFinanceTracker.reverseRecordList();
    }

    @Override
    public void sortFilteredRecordList(Comparator<Record> comparator) {
        requireNonNull(comparator);
        versionedFinanceTracker.sortRecordList(comparator);

    }

    //=========== Summary =================================================================================
    /**
     * Returns an unmodifiable view of expenses, filtered by {@code recordSummaryPredicate} and sorted by date.
     *
     * @return {@code ObservableList<Record>} of expenses which fulfill summary filter
     */
    @Override
    public ObservableList<Record> getRecordSummary() {
        FilteredList<Record> filteredList = new FilteredList<>(versionedFinanceTracker.getRecordList());
        filteredList.setPredicate(recordSummaryPredicate);

        SortedList<Record> sortedList = new SortedList<>(filteredList);
        Comparator<Record> byDate = (Record a, Record b) -> (-1 * Date.compare(a.getDate(), b.getDate()));
        sortedList.setComparator(byDate);

        return FXCollections.unmodifiableObservableList(sortedList);
    }

    @Override
    public void updateRecordSummaryPredicate (Predicate<Record> predicate) {
        recordSummaryPredicate = predicate;
    }

    @Override
    public void updateSummaryPeriod(SummaryPeriod period) {
        this.summaryPeriod = period;
    }

    @Override
    public SummaryPeriod getSummaryPeriod() {
        return this.summaryPeriod;
    }

    @Override
    public void updatePeriodAmount(int periodAmount) {
        this.periodAmount = periodAmount;
    }

    @Override
    public int getPeriodAmount() {
        return this.periodAmount;
    }

    private SummaryPeriod defaultSummaryPeriod() {
        return SummaryPeriod.DAY;
    }

    private int defaultPeriodAmount() {
        return 7;
    }

    private Predicate <Record> defaultRecordPredicate() {
        LocalDate now = LocalDate.now().minusDays(7);
        return e -> e.getDate().isAfter(now);
    }


    //=========== Filtered Record List Accessors =============================================================

    /**
     * Returns the amount value of {@code budget} in an ObjectProperty wrapper
     */
    @Override
    public TotalBudget getBudget() {
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
        commitFinanceTracker(false);
    }

    @Override
    public void commitFinanceTracker(boolean isSetFile) {
        versionedFinanceTracker.commit(isSetFile);
    }

    @Override
    public boolean isSetFile() {
        return versionedFinanceTracker.isSetFile();
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
