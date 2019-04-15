package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.BILL1;
import static seedu.address.testutil.TypicalRestOrRant.BILL2;

import org.junit.Test;

import seedu.address.testutil.BillBuilder;

public class BillTest {

    @Test
    public void equals() {
        // same values -> returns true
        Bill billCopy = new BillBuilder(BILL1).build();
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
        Bill editedBill = new BillBuilder(BILL1).withTableNumber("4").build();
        assertFalse(BILL1.equals(editedBill));

        // different day -> returns false
        editedBill = new BillBuilder(BILL1).withDay("4").build();
        assertFalse(BILL1.equals(editedBill));

        // different month -> returns false
        editedBill = new BillBuilder(BILL1).withMonth("4").build();
        assertFalse(BILL1.equals(editedBill));

        // different year -> returns false
        editedBill = new BillBuilder(BILL1).withYear("2014").build();
        assertFalse(BILL1.equals(editedBill));

        // different total bill -> returns false
        editedBill = new BillBuilder(BILL1).withTotalBill("4025.36").build();
        assertFalse(BILL1.equals(editedBill));
    }
}
