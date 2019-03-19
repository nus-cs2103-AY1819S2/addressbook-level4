package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.BILL1;
import static seedu.address.testutil.TypicalRestOrRant.BILL2;

import org.junit.Test;

import seedu.address.testutil.StatisticsBuilder;

public class BillTest {

    @Test
    public void equals() {
        // same values -> returns true
        Bill billCopy = new StatisticsBuilder(BILL1).buildBill();
        assertTrue(BILL1.equals(billCopy));

        // same object -> returns true
        assertTrue(BILL1.equals(BILL1));

        // null -> returns false
        assertFalse(BILL1.equals(null));

        // different type -> returns false
        assertFalse(BILL1.equals(5));

        // different bill -> returns false
        assertFalse(BILL1.equals(BILL2));

        // different table number -> returns false
        Bill editedBill = new StatisticsBuilder(BILL1).withTableNumber("4").buildBill();
        assertFalse(BILL1.equals(editedBill));

        // different day -> returns false
        editedBill = new StatisticsBuilder(BILL1).withDay("4").buildBill();
        assertFalse(BILL1.equals(editedBill));

        // different month -> returns false
        editedBill = new StatisticsBuilder(BILL1).withMonth("4").buildBill();
        assertFalse(BILL1.equals(editedBill));

        // different year -> returns false
        editedBill = new StatisticsBuilder(BILL1).withYear("2014").buildBill();
        assertFalse(BILL1.equals(editedBill));

        // different total bill -> returns false
        editedBill = new StatisticsBuilder(BILL1).withTotalBill("4025.36").buildBill();
        assertFalse(BILL1.equals(editedBill));
    }
}
