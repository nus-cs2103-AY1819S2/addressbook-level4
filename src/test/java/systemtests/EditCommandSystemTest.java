package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.BACKFACE_DESC_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.BACKFACE_DESC_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.FRONTFACE_DESC_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.FRONTFACE_DESC_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BACKFACE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FRONTFACE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_INDONESIAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BACKFACE_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static seedu.address.testutil.TypicalFlashcards.GOOD;
import static seedu.address.testutil.TypicalFlashcards.KEYWORD_MATCHING_GOOD;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Face;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.FlashcardBuilder;
import seedu.address.testutil.FlashcardUtil;

public class EditCommandSystemTest extends CardCollectionSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_FLASHCARD;
        String command =
            " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + FRONTFACE_DESC_GOOD + "  "
                + BACKFACE_DESC_GOOD + " " + TAG_DESC_CHINESE + " ";
        Flashcard editedFlashcard = new FlashcardBuilder(GOOD).withTags(VALID_TAG_CHINESE).build();
        assertCommandSuccess(command, index, editedFlashcard);

        /* Case: undo editing the last flashcard in the list -> last flashcard restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last flashcard in the list -> last flashcard edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setFlashcard(getModel().getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased()),
            editedFlashcard);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a flashcard with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD
            + TAG_DESC_INDONESIAN;
        assertCommandSuccess(command, index, GOOD);

        /* Case: edit a flashcard with new values same as another flashcard's values but with different name ->
        edited */
        assertTrue(getModel().getCardCollection().getFlashcardList().contains(GOOD));
        index = INDEX_SECOND_FLASHCARD;
        assertNotEquals(getModel().getFilteredFlashcardList().get(index.getZeroBased()), GOOD);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + FRONTFACE_DESC_HITBAG + BACKFACE_DESC_GOOD
            + TAG_DESC_INDONESIAN;
        editedFlashcard = new FlashcardBuilder(GOOD).withFrontFace(VALID_FRONTFACE_HITBAG).build();
        assertCommandSuccess(command, index, editedFlashcard);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_FLASHCARD;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Flashcard flashcardToEdit = getModel().getFilteredFlashcardList().get(index.getZeroBased());
        editedFlashcard = new FlashcardBuilder(flashcardToEdit).withTags().build();
        assertCommandSuccess(command, index, editedFlashcard);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered flashcard list, edit index within bounds of card collection and flashcard list -> edited */
        for (Flashcard i : getModel().getCardCollection().getFlashcardList()) {
            System.out.println(i);
        }
        showFlashcardsWithName(KEYWORD_MATCHING_GOOD);
        index = INDEX_FIRST_FLASHCARD;
        assertTrue(index.getZeroBased() < getModel().getFilteredFlashcardList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + BACKFACE_DESC_HITBAG;
        flashcardToEdit = getModel().getFilteredFlashcardList().get(index.getZeroBased());
        editedFlashcard = new FlashcardBuilder(flashcardToEdit).withBackFace(VALID_BACKFACE_HITBAG).build();
        assertCommandSuccess(command, index, editedFlashcard);

        /* Case: filtered flashcard list, edit index within bounds of card collection but out of bounds of flashcard
         * list -> rejected
         */
        showFlashcardsWithName(KEYWORD_MATCHING_GOOD);
        int invalidIndex = getModel().getCardCollection().getFlashcardList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + FRONTFACE_DESC_GOOD,
            Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a flashcard card is selected ----------------------- */

        /* Case: selects first card in the flashcard list, edit a flashcard -> edited, card selection remains unchanged
         */
        showAllFlashcards();
        index = INDEX_FIRST_FLASHCARD;
        selectFlashcard(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD
            + TAG_DESC_INDONESIAN;
        assertCommandSuccess(command, index, GOOD, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + FRONTFACE_DESC_GOOD,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + FRONTFACE_DESC_GOOD,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredFlashcardList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + FRONTFACE_DESC_GOOD,
            Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + FRONTFACE_DESC_GOOD,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased(),
            EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid frontFace -> rejected */
        assertCommandFailure(
            EditCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased() + INVALID_FRONTFACE_DESC,
            Face.MESSAGE_CONSTRAINTS);

        /* Case: invalid backFace -> rejected */
        assertCommandFailure(
            EditCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased() + INVALID_BACKFACE_DESC,
            Face.MESSAGE_CONSTRAINTS);


        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased() + INVALID_TAG_DESC,
            Tag.MESSAGE_CONSTRAINTS);

        /* Case: edit a flashcard with new values same as another flashcard's values -> rejected */
        executeCommand(FlashcardUtil.getAddCommand(GOOD));
        assertTrue(getModel().getCardCollection().getFlashcardList().contains(GOOD));
        index = INDEX_FIRST_FLASHCARD;
        assertFalse(getModel().getFilteredFlashcardList().get(index.getZeroBased() + 1).equals(GOOD));
        command = EditCommand.COMMAND_WORD + " " + (index.getOneBased() + 1) + FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Flashcard, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Flashcard, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Flashcard editedFlashcard) {
        assertCommandSuccess(command, toEdit, editedFlashcard, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the flashcard at index {@code toEdit} being
     * updated to values specified {@code editedFlashcard}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Flashcard editedFlashcard,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setFlashcard(expectedModel.getFilteredFlashcardList().get(toEdit.getZeroBased()),
            editedFlashcard);
        expectedModel.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        assertCommandSuccess(command, expectedModel,
            String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code CardCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see CardCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see CardCollectionSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
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
