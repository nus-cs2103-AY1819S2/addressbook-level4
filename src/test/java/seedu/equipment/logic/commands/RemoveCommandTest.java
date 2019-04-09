package seedu.equipment.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.equipment.model.WorkListId;
import seedu.equipment.model.equipment.SerialNumber;

public class RemoveCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullWorkListIdNullSerialNumber_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveCommand(null, null);
    }

    @Test
    public void equals() {
        SerialNumber sr1 = new SerialNumber("A090909");
        SerialNumber sr2 = new SerialNumber("A080808");
        WorkListId id1 = new WorkListId("1");
        WorkListId id2 = new WorkListId("2");

        RemoveCommand removeCommand1 = new RemoveCommand(id1, sr1);
        RemoveCommand removeCommand2 = new RemoveCommand(id2, sr2);

        // same object -> returns true
        assertTrue(removeCommand1.equals(removeCommand1));

        // same values -> returns true
        RemoveCommand removeCommand1Copy = new RemoveCommand(id1, sr1);
        assertTrue(removeCommand1.equals(removeCommand1Copy));

        // different types -> returns false
        assertFalse(removeCommand1.equals(1));

        // null -> returns false
        assertFalse(removeCommand1.equals(null));

        // different equipment -> returns false
        assertFalse(removeCommand1.equals(removeCommand2));
    }

}
