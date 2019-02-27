package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // HelpCommandResult -> returns false
        assertFalse(commandResult.equals(new HelpCommandResult("feedback")));

        // ExitCommandResult -> returns false
        assertFalse(commandResult.equals(new ExitCommandResult("feedback")));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // HelpCommandResult -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new HelpCommandResult("feedback").hashCode());

        // ExitCommandResult -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new ExitCommandResult("feedback").hashCode());
    }
}
