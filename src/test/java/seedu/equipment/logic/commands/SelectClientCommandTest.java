package seedu.equipment.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalEquipmentManager;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import org.junit.Test;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;
import seedu.equipment.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectClientCommand}.
 */
public class SelectClientCommandTest {
    private Model model = new ModelManager(getTypicalEquipmentManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectClientCommand selectFirstCommand = new SelectClientCommand(INDEX_FIRST_CLIENT);
        SelectClientCommand selectSecondCommand = new SelectClientCommand(INDEX_SECOND_CLIENT);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectClientCommand selectFirstCommandCopy = new SelectClientCommand(INDEX_FIRST_CLIENT);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different equipment -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectClientCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectClientCommand selectClientCommand = new SelectClientCommand(index);
        assertCommandFailure(selectClientCommand, model, commandHistory, expectedMessage);
    }
}
