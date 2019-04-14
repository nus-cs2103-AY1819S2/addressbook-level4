package seedu.address.logic.commands;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showApparelAtIndex;
import static seedu.address.testutil.TypicalApparels.getDirtyAddressBook;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPAREL;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPAREL;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.FashionMatch;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.apparel.Apparel;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class WearCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_wear_success() {
        Apparel apparelToWear = model.getFilteredApparelList().get(INDEX_SECOND_APPAREL.getZeroBased());
        Apparel wornApparel = new Apparel(apparelToWear);
        wornApparel.use();

        WearCommand wearCommand = new WearCommand(INDEX_SECOND_APPAREL);

        String expectedMessage = String.format(WearCommand.MESSAGE_WEAR_APPAREL_SUCCESS,
                INDEX_SECOND_APPAREL.getOneBased(), wornApparel);

        Model expectedModel = new ModelManager(new FashionMatch(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredApparelList().get(INDEX_SECOND_APPAREL.getZeroBased()),
                wornApparel);
        expectedModel.commitAddressBook();

        System.out.println(expectedMessage);
        assertCommandSuccess(wearCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_wearAgain_success() {
        for (int i = 0; i < 100; i++) {
            Model dirtyModel = new ModelManager(getDirtyAddressBook(), new UserPrefs());
            showApparelAtIndex(dirtyModel, INDEX_FIRST_APPAREL);

            Apparel wornApparelInFilteredList = dirtyModel.getFilteredApparelList()
                    .get(INDEX_FIRST_APPAREL.getZeroBased());
            Apparel wornAgainApparel = new Apparel(wornApparelInFilteredList);
            wornAgainApparel.use();

            WearCommand wearCommand = new WearCommand(INDEX_FIRST_APPAREL);

            String[] randomMessages = generateRandomResponses(INDEX_FIRST_APPAREL.getOneBased(), wornAgainApparel);

            Model expectedModel = new ModelManager(new FashionMatch(dirtyModel.getAddressBook()), new UserPrefs());
            expectedModel.setPerson(dirtyModel.getFilteredApparelList()
                    .get(INDEX_FIRST_APPAREL.getZeroBased()), wornAgainApparel);
            expectedModel.commitAddressBook();

            assertCommandSuccess(wearCommand, dirtyModel, commandHistory, randomMessages, expectedModel);
        }
    }

    /**
     * Takes all the random global string response messages from wear command and stores them in a
     * {@code String} array
     */
    public String[] generateRandomResponses(int index, Apparel wornApparel) {

        // for future versions can replace randomMessage array init
        // with global array of messages from unavailable class
        String[] randomMessages = new String[7];

        randomMessages[0] = String.format(WearCommand.MESSAGE_WEAR_APPAREL_THOUGH_WORN_SUCCESS_1,
                index, wornApparel);
        randomMessages[1] = String.format(WearCommand.MESSAGE_WEAR_APPAREL_THOUGH_WORN_SUCCESS_2,
                index, wornApparel);
        randomMessages[2] = String.format(WearCommand.MESSAGE_WEAR_APPAREL_THOUGH_WORN_SUCCESS_3,
                index, wornApparel);
        randomMessages[3] = String.format(WearCommand.MESSAGE_WEAR_APPAREL_THOUGH_WORN_SUCCESS_4,
                index, wornApparel);
        randomMessages[4] = String.format(WearCommand.MESSAGE_WEAR_APPAREL_THOUGH_WORN_SUCCESS_5,
                index, wornApparel);
        randomMessages[5] = String.format(WearCommand.MESSAGE_WEAR_APPAREL_THOUGH_WORN_SUCCESS_6,
                index, wornApparel);
        randomMessages[6] = String.format(WearCommand.MESSAGE_WEAR_APPAREL_THOUGH_WORN_SUCCESS_7,
                index, wornApparel);

        return randomMessages;
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApparelList().size() + 1);

        WearCommand wearCommand = new WearCommand(outOfBoundIndex);

        assertCommandFailure(wearCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_APPAREL_DISPLAYED_INDEX);
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

        WearCommand wearCommand = new WearCommand(outOfBoundIndex);

        assertCommandFailure(wearCommand, model, commandHistory,
                                Messages.MESSAGE_INVALID_APPAREL_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Apparel apparelToEdit = model.getFilteredApparelList().get(INDEX_FIRST_APPAREL.getZeroBased());
        Apparel wornApparel = new Apparel(apparelToEdit.use());
        WearCommand wearCommand = new WearCommand(INDEX_FIRST_APPAREL);
        Model expectedModel = new ModelManager(new FashionMatch(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(apparelToEdit, wornApparel);
        expectedModel.commitAddressBook();

        // edit -> first apparel edited
        wearCommand.execute(model, commandHistory);

        // undo -> reverts address book back to previous state and filtered apparel list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first apparel edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApparelList().size() + 1);
        WearCommand wearCommand = new WearCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(wearCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_APPAREL_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Apparel} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited apparel in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the apparel object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        WearCommand wearCommand = new WearCommand(INDEX_FIRST_APPAREL);
        Model expectedModel = new ModelManager(new FashionMatch(model.getAddressBook()), new UserPrefs());

        showApparelAtIndex(model, INDEX_SECOND_APPAREL);
        Apparel apparelToEdit = model.getFilteredApparelList().get(INDEX_FIRST_APPAREL.getZeroBased());
        Apparel wornApparel = new Apparel(apparelToEdit.use());
        expectedModel.setPerson(apparelToEdit, wornApparel);
        expectedModel.commitAddressBook();

        // edit -> edits second apparel in unfiltered apparel list / first apparel in filtered apparel list
        wearCommand.execute(model, commandHistory);

        // undo -> reverts address book back to previous state and filtered apparel list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredApparelList().get(INDEX_FIRST_APPAREL.getZeroBased()), apparelToEdit);
        // redo -> edits same second apparel in unfiltered apparel list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
