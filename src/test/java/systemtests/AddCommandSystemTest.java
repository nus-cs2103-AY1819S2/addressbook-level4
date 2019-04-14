package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.BACKFACE_DESC_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.BACKFACE_DESC_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.FRONTFACE_DESC_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.FRONTFACE_DESC_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_INDONESIAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_HITBAG;
import static seedu.address.testutil.TypicalFlashcards.GOOD;
import static seedu.address.testutil.TypicalFlashcards.HITBAG;
import static seedu.address.testutil.TypicalFlashcards.HOLA;
import static seedu.address.testutil.TypicalFlashcards.KEYWORD_MATCHING_GOOD;
import static seedu.address.testutil.TypicalFlashcards.NEWTON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.testutil.FlashcardBuilder;
import seedu.address.testutil.FlashcardUtil;

public class AddCommandSystemTest extends CardCollectionSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a flashcard without tags to a non-empty card collection, command with leading spaces and trailing
         * spaces
         * -> added
         */
        Flashcard toAdd = GOOD;
        String command =
            "   " + AddCommand.COMMAND_WORD + "  " + FRONTFACE_DESC_GOOD + "  " + BACKFACE_DESC_GOOD + " "
                + TAG_DESC_INDONESIAN + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Good to the list -> Good deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = String.format(UndoCommand.MESSAGE_SUCCESS, AddCommand.COMMAND_WORD);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Good to the list -> Good added again */
        command = RedoCommand.COMMAND_WORD;
        model.addFlashcard(toAdd);
        expectedResultMessage = String.format(RedoCommand.MESSAGE_SUCCESS, AddCommand.COMMAND_WORD);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a flashcard with all fields same as another flashcard in the card collection except name ->
        added */
        toAdd = new FlashcardBuilder(GOOD).withFrontFace(VALID_FRONTFACE_HITBAG).build();
        command = AddCommand.COMMAND_WORD + FRONTFACE_DESC_HITBAG + BACKFACE_DESC_GOOD + TAG_DESC_INDONESIAN;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty card collection -> added */
        deleteAllFlashcards();
        assertCommandSuccess(GOOD);

        /* Case: add a flashcard with tags, command with parameters in random order -> added */
        toAdd = HITBAG;
        command = AddCommand.COMMAND_WORD + TAG_DESC_CHINESE + BACKFACE_DESC_HITBAG + FRONTFACE_DESC_HITBAG;
        assertCommandSuccess(command, toAdd);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the flashcard list before adding -> added */
        showFlashcardsWithName(KEYWORD_MATCHING_GOOD);
        assertCommandSuccess(HOLA);

        /* ------------------------ Perform add operation while a flashcard card is selected
        --------------------------- */

        /* Case: selects first card in the flashcard list, add a flashcard -> added, card selection remains unchanged */
        selectFlashcard(Index.fromOneBased(1));
        assertCommandSuccess(NEWTON);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate flashcard -> rejected */
        command = FlashcardUtil.getAddCommand(GOOD);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_FLASHCARD);

    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code FlashcardListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code CardCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see CardCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Flashcard toAdd) {
        assertCommandSuccess(FlashcardUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Flashcard)}. Executes {@code command}
     * instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(Flashcard)
     */
    private void assertCommandSuccess(String command, Flashcard toAdd) {
        Model expectedModel = getModel();
        expectedModel.addFlashcard(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Flashcard)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code FlashcardListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddCommandSystemTest#assertCommandSuccess(String, Flashcard)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code FlashcardListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code CardCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see CardCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
