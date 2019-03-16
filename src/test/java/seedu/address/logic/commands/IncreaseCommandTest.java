package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.IncreaseCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalRecords.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.record.Amount;

/**
 * Contains integration tests (interaction with Model) and unit test for IncreaseCommand
 */
public class IncreaseCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final Amount amount = new Amount("123");
        assertCommandFailure(new IncreaseCommand(amount), model, new CommandHistory(),
                String.format(MESSAGE_ARGUMENTS, amount));
    }

    @Test
    public void equals() {
        final IncreaseCommand standardCommand = new IncreaseCommand(new Amount(VALID_AMOUNT_AMY));

        // same values -> returns true
        IncreaseCommand commandWithSameValues = new IncreaseCommand(new Amount("312"));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> return false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different amount -> returns false
        assertFalse(standardCommand.equals(new Amount(VALID_AMOUNT_BOB)));
    }
}
