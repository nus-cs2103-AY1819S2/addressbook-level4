package seedu.finance.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static seedu.finance.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.finance.logic.commands.AllocateCommand.MESSAGE_ARGUMENTS;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import java.util.HashSet;
import java.util.Set;

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
        final Amount amount = new Amount("123");
        final Set<Category> categories = new HashSet<>();
        categories.add(new Category("Food"));

        assertCommandFailure(new AllocateCommand(amount, categories), model,
                new CommandHistory(), String.format(MESSAGE_ARGUMENTS, amount, categories));
    }

    @Test
    public void equals() {
        final Set<Category> categories = new HashSet<>();
        categories.add(new Category(VALID_CATEGORY_FRIEND));
        final AllocateCommand standardCommand = new AllocateCommand(new Amount(VALID_AMOUNT_AMY),
                categories);

        final Set<Category> otherCategories = new HashSet<>();
        otherCategories.add(new Category(VALID_CATEGORY_FRIEND));
        otherCategories.add(new Category(VALID_CATEGORY_HUSBAND));

        // same values -> returns true
        AllocateCommand commandWithSameValues = new AllocateCommand(new Amount ("312"), categories);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new IncreaseCommand(new Amount ("312"))));

        // different amount -> returns false
        assertFalse(standardCommand.equals(new AllocateCommand(new Amount(VALID_AMOUNT_BOB), categories)));

        // different categories -> returns false
        assertFalse(standardCommand.equals(new AllocateCommand(new Amount(VALID_AMOUNT_AMY), otherCategories)));

    }
}
