package seedu.finance.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.finance.logic.commands.AllocateCommand.MESSAGE_ARGUMENTS;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static seedu.finance.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import org.junit.Test;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;
import seedu.finance.model.category.Category;
import seedu.finance.model.record.Amount;

/**
 * Contains integration tests (interaction with Model) and unit test for AllocateCommand
 */
public class AllocateCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute() {
        final Amount amount = new Amount("123.00");
        final Category category = new Category("Food");

        assertCommandFailure(new AllocateCommand(amount, category), model,
                new CommandHistory(), String.format(MESSAGE_ARGUMENTS, amount, category));
    }

    @Test
    public void equals() {

        final AllocateCommand standardCommand = new AllocateCommand(new Amount(VALID_AMOUNT_AMY),
                new Category(VALID_CATEGORY_FRIEND));

        // same values -> returns true
        AllocateCommand commandWithSameValues = new AllocateCommand(new Amount ("312.00"),
                new Category("friend"));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new IncreaseCommand(new Amount ("312.00"))));

        // different amount -> returns false
        assertFalse(standardCommand.equals(new AllocateCommand(new Amount(VALID_AMOUNT_BOB),
                new Category(VALID_CATEGORY_FRIEND))));

        // different category -> returns false
        assertFalse(standardCommand.equals(new AllocateCommand(new Amount(VALID_AMOUNT_AMY),
                new Category(VALID_CATEGORY_HUSBAND))));

    }
}
