package seedu.finance.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static seedu.finance.testutil.TypicalRecords.APPLE;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.budget.TotalBudget;
import seedu.finance.model.record.Record;
import seedu.finance.testutil.RecordBuilder;

public class FinanceTrackerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FinanceTracker financeTracker = new FinanceTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), financeTracker.getRecordList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financeTracker.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyFinanceTracker_replacesData() {
        FinanceTracker newData = getTypicalFinanceTracker();
        financeTracker.resetData(newData);
        assertEquals(newData, financeTracker);
    }

    @Test
    public void resetData_withDuplicateRecords_replacesData() {
        // Two records with the same identity fields
        Record editedApple = new RecordBuilder(APPLE).withAmount(VALID_AMOUNT_BOB)
                .withCategory(VALID_CATEGORY_HUSBAND)
                .build();
        List<Record> newRecords = Arrays.asList(APPLE, editedApple);
        FinanceTrackerStub newData = new FinanceTrackerStub(newRecords);

        financeTracker.resetData(newData);
        assertEquals(newRecords, financeTracker.getRecordList());
    }

    @Test
    public void hasRecord_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financeTracker.hasRecord(null);
    }

    @Test
    public void hasRecord_recordNotInFinanceTracker_returnsFalse() {
        assertFalse(financeTracker.hasRecord(APPLE));
    }

    @Test
    public void hasRecord_recordInFinanceTracker_returnsTrue() {
        financeTracker.addRecord(APPLE);
        assertTrue(financeTracker.hasRecord(APPLE));
    }

    @Test
    public void hasRecord_recordWithSameIdentityFieldsInFinanceTracker_returnsTrue() {
        financeTracker.addRecord(APPLE);
        Record editedApple = new RecordBuilder(APPLE).withAmount(VALID_AMOUNT_BOB)
                .withCategory(VALID_CATEGORY_HUSBAND).build();
        assertTrue(financeTracker.hasRecord(editedApple));
    }

    @Test
    public void getRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        financeTracker.getRecordList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        financeTracker.addListener(listener);
        financeTracker.addRecord(APPLE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        financeTracker.addListener(listener);
        financeTracker.removeListener(listener);
        financeTracker.addRecord(APPLE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyFinanceTracker whose records list can violate interface constraints.
     */
    private static class FinanceTrackerStub implements ReadOnlyFinanceTracker {
        private final ObservableList<Record> records = FXCollections.observableArrayList();
        private TotalBudget budget = new TotalBudget(123.00);

        FinanceTrackerStub(Collection<Record> records) {
            this.records.setAll(records);
        }

        @Override
        public ObservableList<Record> getRecordList() {
            return records;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError(
                    "This method should not be called.");
        }


        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TotalBudget getBudget() {
            return this.budget;
        }

        // Stub
        @Override
        public HashSet<CategoryBudget> getCategoryBudget() {
            return null;
        }

        @Override
        public boolean isSetFile() {
            return false;
        }
    }

}
