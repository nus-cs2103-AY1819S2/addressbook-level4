package seedu.equipment.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.equipment.model.WorkListId;
import seedu.equipment.model.equipment.SerialNumber;

public class PutCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullWorkListIdNullSerialNumber_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new PutCommand(null, null);
    }

    @Test
    public void equals() {
        SerialNumber sr1 = new SerialNumber("A090909");
        SerialNumber sr2 = new SerialNumber("A080808");
        WorkListId id1 = new WorkListId("1");
        WorkListId id2 = new WorkListId("2");

        PutCommand putCommand1 = new PutCommand(id1, sr1);
        PutCommand putCommand2 = new PutCommand(id2, sr2);

        // same object -> returns true
        assertTrue(putCommand1.equals(putCommand1));

        // same values -> returns true
        PutCommand putCommand1Copy = new PutCommand(id1, sr1);
        assertTrue(putCommand1.equals(putCommand1Copy));

        // different types -> returns false
        assertFalse(putCommand1.equals(1));

        // null -> returns false
        assertFalse(putCommand1.equals(null));

        // different equipment -> returns false
        assertFalse(putCommand1.equals(putCommand2));
    }

}
