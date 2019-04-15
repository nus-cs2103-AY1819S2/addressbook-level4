package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteBookCommand.MESSAGE_DELETE_BOOK_SUCCESS;
import static seedu.address.testutil.TestUtil.getBook;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_LIFE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteBookCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

public class DeleteBookCommandSystemTest extends BookShelfSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteBookCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first book in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteBookCommand.COMMAND_WORD + "      " + INDEX_FIRST_BOOK.getOneBased() + "     ";
        Book deletedBook = removeBook(expectedModel, INDEX_FIRST_BOOK);
        String expectedResultMessage = String.format(MESSAGE_DELETE_BOOK_SUCCESS, deletedBook);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last book in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastBookIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastBookIndex);

        /* Case: undo deleting the last book in the list -> last book restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last book in the list -> last book deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeBook(modelBeforeDeletingLast, lastBookIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle book in the list -> deleted */
        Index middleBookIndex = getMidIndex(getModel());
        assertCommandSuccess(middleBookIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered book list, delete index within bounds of book shelf and book list -> deleted */
        showBooksWithName(KEYWORD_MATCHING_LIFE);
        Index index = INDEX_FIRST_BOOK;
        assertTrue(index.getZeroBased() < getModel().getFilteredBookList().size());
        assertCommandSuccess(index);

        /* Case: filtered book list, delete index within bounds of book shelf but out of bounds of book list
         * -> rejected
         */
        showBooksWithName(KEYWORD_MATCHING_LIFE);
        int invalidIndex = getModel().getBookShelf().getBookList().size();
        command = DeleteBookCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a book card is selected ------------------------ */

        /* Case: delete the selected book -> book list panel selects the book before the deleted book */
        showAllBooks();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectBook(selectedIndex);
        command = DeleteBookCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedBook = removeBook(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_BOOK_SUCCESS, deletedBook);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteBookCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteBookCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getBookShelf().getBookList().size() + 1);
        command = DeleteBookCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteBookCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteBookCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Book} at the specified {@code index} in {@code model}'s book shelf.
     * @return the removed book
     */
    private Book removeBook(Model model, Index index) {
        Book targetBook = getBook(model, index);
        model.deleteBook(targetBook);
        return targetBook;
    }

    /**
     * Deletes the book at {@code toDelete} by creating a default {@code DeleteBookCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteBookCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Book deletedBook = removeBook(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_BOOK_SUCCESS, deletedBook);

        assertCommandSuccess(
                DeleteBookCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteBookCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see BookShelfSystemTest#assertSelectedBookCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedBookCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedBookCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedBookCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
