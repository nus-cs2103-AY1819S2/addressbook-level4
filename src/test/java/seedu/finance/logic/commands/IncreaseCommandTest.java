package seedu.finance.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.finance.logic.commands.IncreaseCommand.MESSAGE_ARGUMENTS;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import org.junit.Test;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;
import seedu.finance.model.record.Amount;

/**
 * Contains integration tests (interaction with Model) and unit test for IncreaseCommand
 */
public class IncreaseCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute() {
        final Amount amount = new Amount("$123.30");
        assertCommandFailure(new IncreaseCommand(amount), model, new CommandHistory(),
                String.format(MESSAGE_ARGUMENTS, amount));
    }

    @Test
    public void equals() {
        final IncreaseCommand standardCommand = new IncreaseCommand(new Amount(VALID_AMOUNT_AMY));

        // same values -> returns true
        IncreaseCommand commandWithSameValues = new IncreaseCommand(new Amount("$312.00"));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> return false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different amount -> returns false
        assertFalse(standardCommand.equals(new IncreaseCommand(new Amount(VALID_AMOUNT_BOB))));
    }
}
