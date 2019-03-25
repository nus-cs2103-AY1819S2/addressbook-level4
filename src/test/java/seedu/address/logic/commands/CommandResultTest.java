package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.AnswerCommandResultType;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");
        Card sampleTestCard = new CardBuilder().build();

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", CommandResult.TYPE.NONE)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));
        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", CommandResult.TYPE.SHOW_HELP)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", CommandResult.TYPE.IS_EXIT)));

        // different testSessionCard value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", CommandResult.TYPE.TEST_SESSION_CARD)));

        // different endTestSession value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", CommandResult.TYPE.END_TEST_SESSION)));

        // different AnswerCommandResult value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", CommandResult.TYPE.ANSWER_CORRECT)));
        assertFalse(commandResult.equals(new CommandResult("feedback", CommandResult.TYPE.ANSWER_WRONG)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");
        Card sampleTestCard = new CardBuilder().build();

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", CommandResult.TYPE.SHOW_HELP).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", CommandResult.TYPE.IS_EXIT).hashCode());

        // different testSessionCard value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", CommandResult.TYPE.TEST_SESSION_CARD).hashCode());

        // different endTestSession value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", CommandResult.TYPE.END_TEST_SESSION).hashCode());

        // different AnswerCommandResult value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", CommandResult.TYPE.ANSWER_CORRECT).hashCode());
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", CommandResult.TYPE.ANSWER_WRONG).hashCode());
    }
}
