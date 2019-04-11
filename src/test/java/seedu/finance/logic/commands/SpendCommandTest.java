package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.finance.testutil.TypicalRecords.APPLE;
import static seedu.finance.testutil.TypicalRecords.BANANA;
import static seedu.finance.testutil.TypicalRecords.CAP;
import static seedu.finance.testutil.TypicalRecords.DONUT;
import static seedu.finance.testutil.TypicalRecords.EARRINGS;
import static seedu.finance.testutil.TypicalRecords.FRUITS;
import static seedu.finance.testutil.TypicalRecords.GIFT;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.finance.commons.core.GuiSettings;
import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.SummaryCommand.SummaryPeriod;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.model.ReadOnlyUserPrefs;
import seedu.finance.model.UserPrefs;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.budget.TotalBudget;
import seedu.finance.model.exceptions.CategoryBudgetExceedTotalBudgetException;
import seedu.finance.model.record.Record;
import seedu.finance.testutil.RecordBuilder;

public class SpendCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SpendCommand(null);
    }

    @Test
    public void execute_recordAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRecordAdded modelStub = new ModelStubAcceptingRecordAdded();
        Record validRecord = new RecordBuilder().build();

        CommandResult commandResult = new SpendCommand(validRecord).execute(modelStub, commandHistory);

        assertEquals(String.format(SpendCommand.MESSAGE_SUCCESS, validRecord), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validRecord), modelStub.recordsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateRecord_addSuccessful() throws Exception {
        Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
        Record validRecord = model.getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased());
        String expectedMessage = String.format(SpendCommand.MESSAGE_SUCCESS, validRecord);

        CommandResult commandResult = new SpendCommand(validRecord).execute(model, commandHistory);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(APPLE, BANANA, CAP, DONUT, EARRINGS, FRUITS, GIFT, APPLE),
                model.getFilteredRecordList());
    }

    @Test
    public void equals() {
        Record alice = new RecordBuilder().withName("Alice").build();
        Record bob = new RecordBuilder().withName("Bob").build();
        SpendCommand addAliceCommand = new SpendCommand(alice);
        SpendCommand addBobCommand = new SpendCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        SpendCommand addAliceCommandCopy = new SpendCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different record -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getFinanceTrackerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinanceTrackerFilePath(Path financeTrackerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean addRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCategoryBudget(CategoryBudget budget) throws CategoryBudgetExceedTotalBudgetException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinanceTracker(ReadOnlyFinanceTracker financeTracker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TotalBudget getBudget() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public HashSet<CategoryBudget> getCatBudget() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFinanceTracker getFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecord(Record target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRecord(Record target, Record editedRecord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPreviousDataFile(Path path) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path removePreviousDataFile() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addUndoPreviousDataFile(Path path) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path removeUndoPreviousDataFile() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void changeFinanceTrackerFile(Path path) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void reverseFilteredRecordList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredRecordList(Comparator<Record> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Record> getFilteredRecordList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecordList(Predicate<Record> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitFinanceTracker(boolean isSetFile) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isSetFile() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Record> selectedRecordProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Record getSelectedRecord() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Record> getRecordSummary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getPeriodAmount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePeriodAmount(int periodAmount) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SummaryPeriod getSummaryPeriod() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSummaryPeriod(SummaryPeriod period) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRecordSummaryPredicate(Predicate<Record> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single record.
     */
    private class ModelStubWithRecord extends ModelStub {
        private final Record record;

        ModelStubWithRecord(Record record) {
            requireNonNull(record);
            this.record = record;
        }

        @Override
        public boolean hasRecord(Record record) {
            requireNonNull(record);
            return this.record.isSameRecord(record);
        }
    }

    /**
     * A Model stub that always accept the record being added.
     */
    private class ModelStubAcceptingRecordAdded extends ModelStub {
        final ArrayList<Record> recordsAdded = new ArrayList<>();

        @Override
        public boolean hasRecord(Record record) {
            requireNonNull(record);
            return recordsAdded.stream().anyMatch(record::isSameRecord);
        }

        @Override
        public boolean addRecord(Record record) {
            requireNonNull(record);
            return recordsAdded.add(record);
        }

        @Override
        public void commitFinanceTracker() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyFinanceTracker getFinanceTracker() {
            return new FinanceTracker();
        }
    }

}
