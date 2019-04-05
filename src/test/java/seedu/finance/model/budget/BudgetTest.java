package seedu.finance.model.budget;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.finance.testutil.Assert;

public class BudgetTest {

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
    public void addRecord() {

    }

    @Test
    public void removeRecord() {

    }

    @Test
    public void isSet() {

    }
}
