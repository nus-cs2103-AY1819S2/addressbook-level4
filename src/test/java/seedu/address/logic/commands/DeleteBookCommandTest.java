package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteBookCommand}.
 */
public class DeleteBookCommandTest {

    private Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Book bookToDelete = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        DeleteBookCommand deleteCommand =
                new DeleteBookCommand(INDEX_FIRST_BOOK);

        String expectedMessage = String.format(DeleteBookCommand.MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete);

        ModelManager expectedModel = new ModelManager(model.getBookShelf(), new UserPrefs());
        expectedModel.deleteBook(bookToDelete);
        expectedModel.commitBookShelf();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidBookNameUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        DeleteBookCommand deleteCommand =
                new DeleteBookCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Book bookToDelete = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        DeleteBookCommand deleteCommand = new DeleteBookCommand(INDEX_FIRST_BOOK);

        String expectedMessage = String.format(DeleteBookCommand.MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete);

        Model expectedModel = new ModelManager(model.getBookShelf(), new UserPrefs());
        expectedModel.deleteBook(bookToDelete);
        expectedModel.commitBookShelf();
        showNoBook(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of book shelf list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBookShelf().getBookList().size());

        DeleteBookCommand deleteCommand = new DeleteBookCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Book bookToDelete = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        DeleteBookCommand deleteCommand = new DeleteBookCommand(INDEX_FIRST_BOOK);
        Model expectedModel = new ModelManager(model.getBookShelf(), new UserPrefs());
        expectedModel.deleteBook(bookToDelete);
        expectedModel.commitBookShelf();

        // delete -> first book deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts bookShelf back to previous state and filtered book list to show all books
        expectedModel.undoBookShelf();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first book deleted again
        expectedModel.redoBookShelf();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        DeleteBookCommand deleteCommand = new DeleteBookCommand(outOfBoundIndex);

        // execution failed -> book shelf state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        // single book shelf state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Book} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted book in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the book object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameBookDeleted() throws Exception {
        DeleteBookCommand deleteCommand = new DeleteBookCommand(INDEX_FIRST_BOOK);
        Model expectedModel = new ModelManager(model.getBookShelf(), new UserPrefs());

        showBookAtIndex(model, INDEX_SECOND_BOOK);
        Book bookToDelete = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        expectedModel.deleteBook(bookToDelete);
        expectedModel.commitBookShelf();

        // delete -> deletes second book in unfiltered book list / first book in filtered book list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts bookShelf back to previous state and filtered book list to show all books
        expectedModel.undoBookShelf();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(bookToDelete, model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased()));
        // redo -> deletes same second book in unfiltered book list
        expectedModel.redoBookShelf();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteBookCommand deleteFirstCommand = new DeleteBookCommand(INDEX_FIRST_BOOK);
        DeleteBookCommand deleteSecondCommand = new DeleteBookCommand(INDEX_SECOND_BOOK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteBookCommand deleteFirstCommandCopy = new DeleteBookCommand(INDEX_FIRST_BOOK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different book -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoBook(Model model) {
        model.updateFilteredBookList(p -> false);

        assertTrue(model.getFilteredBookList().isEmpty());
    }
}
