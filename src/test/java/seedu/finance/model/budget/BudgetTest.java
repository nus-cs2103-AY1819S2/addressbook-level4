package seedu.finance.model.budget;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.testutil.TypicalRecords.APPLE;
import static seedu.finance.testutil.TypicalRecords.getTypicalRecords;

import org.junit.Test;

import seedu.finance.model.FinanceTracker;
import seedu.finance.model.record.Record;
import seedu.finance.testutil.Assert;
import seedu.finance.testutil.RecordBuilder;

public class BudgetTest {

    public static final Budget TYPICAL_BUDGET_ONE = new Budget(100, 90);
    public static final Budget TYPICAL_BUDGET_TWO = new Budget(150, 90);

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void constructor_invalidBudget_throwsIllegalArgumentException() {
        // Current Budget more than total budget
        double currentBudget = 100;
        double totalBudget = 90;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Budget(totalBudget, currentBudget));
    }

    @Test
    public void isValidBudget() {
        // invalid budget
        assertFalse(Budget.isValidBudget(90.0, 100.0)); // total budget less than current budget
        assertFalse(Budget.isValidBudget(-10.0, -15.0)); // negative total budget
        // valid amounts
        assertTrue(Budget.isValidBudget(100.0, 100.0)); // same total and current budget
        assertTrue(Budget.isValidBudget(100, 100)); // integer budget
        assertTrue(Budget.isValidBudget(120.0, 90.0)); // total budget more than current budget
        assertTrue(Budget.isValidBudget(2147483648.5, 2147483648.0)); // large budget
    }

    @Test
    public void updateBudget() {
        Budget budget = new Budget(100);
        FinanceTracker financeTracker = new FinanceTracker();
        // Empty record list
        budget.updateBudget(financeTracker.getRecordList());
        Budget expectedBudget = new Budget(100);

        assertTrue(budget.equals(expectedBudget));

        // Filled record list
        financeTracker.setRecords(getTypicalRecords());
        budget.updateBudget(financeTracker.getRecordList());
        double expectedCurrentBudget = 100.0;
        for (Record r: getTypicalRecords()) {
            expectedCurrentBudget -= r.getAmount().getValue();
        }
        expectedBudget.set(100, expectedCurrentBudget);

        assertTrue(budget.equals(expectedBudget));

        // Current budget exceeds total budget
        budget = new Budget(10);
        budget.updateBudget(financeTracker.getRecordList());
        expectedCurrentBudget = 10.0;
        for (Record r: getTypicalRecords()) {
            expectedCurrentBudget -= r.getAmount().getValue();
        }
        expectedBudget = new Budget(10, expectedCurrentBudget);

        assertTrue(budget.equals(expectedBudget));
    }

    @Test
    public void addRecord_validRecord_addSuccessful() {
        Budget budget = new Budget(100);
        budget.addRecord(APPLE);
        double expectedCurrentBudget = 100 - APPLE.getAmount().getValue();
        Budget expectedBudget = new Budget(100, expectedCurrentBudget);

        assertTrue(budget.equals(expectedBudget));
    }

    @Test
    public void editRecord_changedAmount_editSuccessful() {
        Budget budget = new Budget(100);
        budget.addRecord(APPLE);
        Record editedApple = new RecordBuilder(APPLE).withAmount("5").build();
        budget.editRecord(APPLE, editedApple);
        double expectedCurrentBudget = 100 - editedApple.getAmount().getValue();
        Budget expectedBudget = new Budget(100, expectedCurrentBudget);

        assertTrue(budget.equals(expectedBudget));
    }

    @Test
    public void editRecord_changedOtherFields_editSuccessful() {
        Budget budget = new Budget(100);
        budget.addRecord(APPLE);
        Record editedApple = new RecordBuilder(APPLE).withName("PEAR").build();
        budget.editRecord(APPLE, editedApple);
        double expectedCurrentBudget = 100 - editedApple.getAmount().getValue();
        Budget expectedBudget = new Budget(100, expectedCurrentBudget);

        assertTrue(budget.equals(expectedBudget));
    }

    @Test
    public void removeRecord_validRecord_removeSuccessful() {
        Budget budget = new Budget(100);
        budget.addRecord(APPLE);
        budget.removeRecord(APPLE);
        Budget expectedBudget = new Budget(100);

        assertTrue(budget.equals(expectedBudget));
    }

    @Test
    public void isSet() {
        // Budget not set
        Budget budget = new Budget();
        assertFalse(budget.isSet()); // Newly created budget
        budget = new Budget(100);

        budget.set(0, 0); // Total budget is 0
        assertFalse(budget.isSet());

        // Budget is set
        budget = new Budget(100);
        assertTrue(budget.isSet()); // Budget constructed with initialBudget

        budget = new Budget();
        budget.set(100, 0);
        assertTrue(budget.isSet()); // Budget set through set method
    }

    @Test
    public void equals() {
        // same values -> returns true
        Budget budgetCopy = new Budget(TYPICAL_BUDGET_ONE);
        assertTrue(budgetCopy.equals(TYPICAL_BUDGET_ONE));

        // same object -> returns true
        assertTrue(TYPICAL_BUDGET_ONE.equals(TYPICAL_BUDGET_ONE));

        // null -> returns false
        assertFalse(TYPICAL_BUDGET_ONE.equals(null));

        // different type -> returns false
        assertFalse(TYPICAL_BUDGET_ONE.equals(5));

        // different budget -> returns false
        assertFalse(TYPICAL_BUDGET_ONE.equals(TYPICAL_BUDGET_TWO));

        // different totalBudget -> returns false
        Budget editedBudget = new Budget(TYPICAL_BUDGET_ONE);
        editedBudget.set(100, 80);
        assertFalse(editedBudget.equals(TYPICAL_BUDGET_ONE));

        // different currentBudget -> returns false
        editedBudget = new Budget(TYPICAL_BUDGET_ONE);
        editedBudget.set(110, 90);
        assertFalse(editedBudget.equals(TYPICAL_BUDGET_ONE));
    }
}
