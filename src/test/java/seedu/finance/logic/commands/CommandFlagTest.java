package seedu.finance.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandFlagTest {
    @Test
    public void equals() {
        CommandFlag commandFlag = new CommandFlag("-flag");

        // same values -> returns true
        assertTrue(commandFlag.equals(new CommandFlag("-flag")));

        // same object -> returns true
        assertTrue(commandFlag.equals(commandFlag));

        // null -> returns false
        assertFalse(commandFlag.equals(null));

        // different types -> returns false
        assertFalse(commandFlag.equals(0.5f));

        // different flag value -> returns false
        assertFalse(commandFlag.equals(new CommandFlag("-different")));

    }

    @Test
    public void hashcode() {
        CommandFlag commandFlag = new CommandFlag("-flag");

        // same values -> returns same hashcode
        assertEquals(commandFlag.hashCode(), new CommandFlag("-flag").hashCode());

        // different flag value -> returns different hashcode
        assertNotEquals(commandFlag.hashCode(), new CommandFlag("-different").hashCode());

    }
}
