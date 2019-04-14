package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showApparelAtIndex;
import static seedu.address.testutil.TypicalApparels.getDirtyAddressBook;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPAREL;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPAREL;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FashionMatch;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.apparel.Apparel;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class WashCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model dirtyModel = new ModelManager(getDirtyAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_wash_success() {
        WashCommand washCommand = new WashCommand(INDEX_FIRST_APPAREL);

        Apparel apparelToWash = dirtyModel.getFilteredApparelList().get(0);
        Apparel apparelAfterWash = apparelToWash.setWashed();

        String expectedMessage = String.format(WashCommand.MESSAGE_CLEAN_APPAREL_SUCCESS,
                INDEX_FIRST_APPAREL.getOneBased(), apparelAfterWash);

        // reset
        apparelToWash.setWorn();

        Model expectedModel = new ModelManager(new FashionMatch(dirtyModel.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(apparelToWash, new Apparel(apparelToWash).setWorn());
        expectedModel.commitAddressBook();

        assertCommandSuccess(washCommand, dirtyModel, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_apparelAlreadyClean_failure() {
        showApparelAtIndex(model, INDEX_FIRST_APPAREL);

        Apparel apparelInFilteredList = model.getFilteredApparelList().get(INDEX_FIRST_APPAREL.getZeroBased());

        Apparel cleanApparelInFilteredList = new Apparel(apparelInFilteredList);
        cleanApparelInFilteredList.setWashed();
        model.setPerson(apparelInFilteredList, cleanApparelInFilteredList);

        WashCommand washCommand = new WashCommand(INDEX_FIRST_APPAREL);

        String expectedMessage = String.format(WashCommand.MESSAGE_APPAREL_ALREADY_CLEAN_OCD,
                INDEX_FIRST_APPAREL.getOneBased(), cleanApparelInFilteredList);

        assertCommandFailure(washCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApparelList().size() + 1);
        WashCommand washCommand = new WashCommand(outOfBoundIndex);

        assertCommandFailure(washCommand, model, commandHistory, Messages.MESSAGE_INVALID_APPAREL_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showApparelAtIndex(model, INDEX_FIRST_APPAREL);
        Index outOfBoundIndex = INDEX_SECOND_APPAREL;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getApparelList().size());

        WashCommand washCommand = new WashCommand(outOfBoundIndex);

        try {
            washCommand.execute(model, commandHistory);
            Assert.fail("CommandException should be thrown.");
        } catch (CommandException ce) {
            // do nothing
        }
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApparelList().size() + 1);
        WashCommand washCommand = new WashCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(washCommand, model, commandHistory, Messages.MESSAGE_INVALID_APPAREL_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

}
