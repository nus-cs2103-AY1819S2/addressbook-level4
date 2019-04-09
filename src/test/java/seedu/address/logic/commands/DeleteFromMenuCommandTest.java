package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.DeleteFromMenuCommand.INVALID_RESTAURANT_STATE;
import static seedu.address.logic.commands.DeleteFromOrderCommand.MESSAGE_INVALID_ITEM_CODE;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.menu.Code;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteFromMenuCommand}.
 */
public class DeleteFromMenuCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullItemCode_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteFromMenuCommand(null);
    }

    @Test
    public void execute_invalidRestaurantOccupancy_failure() {
        Code itemCode = new Code("W09");
        DeleteFromMenuCommand command = new DeleteFromMenuCommand(itemCode);
        assertCommandFailure(Mode.MENU_MODE, command, model, commandHistory, INVALID_RESTAURANT_STATE);
    }

    @Test
    public void execute_invalidItemCode_failure() {
        DeleteFromMenuCommand command = new DeleteFromMenuCommand(new Code("W13"));
        assertCommandFailure(Mode.MENU_MODE, command, model, commandHistory,
                String.format(MESSAGE_INVALID_ITEM_CODE, new Code("W13")));
    }

    // TODO: successful deleteFromMenuCommand test

    @Test
    public void equals() {
        DeleteFromMenuCommand firstCommand = new DeleteFromMenuCommand(new Code("W09"));
        DeleteFromMenuCommand secondCommand = new DeleteFromMenuCommand(new Code("W12"));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        DeleteFromMenuCommand firstCommandCopy = new DeleteFromMenuCommand(new Code("W09"));
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different command -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

}
