package seedu.address.model.table;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W09;
import static seedu.address.testutil.TypicalRestOrRant.TABLE2;

import org.junit.Test;

import seedu.address.testutil.TableBuilder;

public class TableTest {
    @Test
    public void isSameTable() {
        // same object -> returns true
        assertTrue(TABLE1.isSameTable(TABLE1));

        // null -> returns false
        assertFalse(TABLE1.isSameTable(null));

        // different table number -> returns false
        assertFalse(TABLE1.isSameTable(TABLE2));

        // same table number, different table status -> returns true
        Table editedTable = new TableBuilder(TABLE1).withTableStatus("0/4").build();
        assertTrue(TABLE1.isSameTable(editedTable));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Table tableCopy = new TableBuilder(TABLE1).build();
        assertTrue(TABLE1.equals(tableCopy));

        // same object -> returns true
        assertTrue(TABLE1.equals(TABLE1));

        // null -> returns false
        assertFalse(TABLE1.equals(null));

        // different type -> returns false
        assertFalse(TABLE1.equals(5));

        // different table -> returns false
        assertFalse(TABLE1.equals(TABLE2));

        // different table number -> returns false
        Table editedTable = new TableBuilder(TABLE1).withTableStatus("0/2").build();
        assertFalse(TABLE1.equals(editedTable));

        // different table status -> returns false
        editedTable = new TableBuilder(TABLE1).withTableStatus("0/4").build();
        assertFalse(TABLE1_W09.equals(editedTable));
    }
}
