package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBook;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBookSwappedIndexOneAndTwo;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Swap Command Test
 */
public class SwapCommandTest {

    // Setup
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_swap_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.setAddressBook(getTypicalAddressBookSwappedIndexOneAndTwo());
        expectedModel.commitAddressBook();

        SwapCommand sc = new SwapCommand(Index.fromOneBased(1), Index.fromOneBased(2));

        assertCommandSuccess(sc, model, commandHistory, SwapCommand.MESSAGE_SWAP_APPAREL_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_swapOutOfBound_failure() {
        SwapCommand sc = new SwapCommand(Index.fromOneBased(1000), Index.fromOneBased(3));

        assertCommandFailure(sc, model, commandHistory, SwapCommand.MESSAGE_FAILURE_INDEX_OUT_OF_BOUND);
    }
}
