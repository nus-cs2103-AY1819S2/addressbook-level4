package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookOfExactName;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteBookCommand}.
 */
public class DeleteBookCommandTest {

    private Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Book bookToDelete = model.getFilteredBookList().get(0);
        DeleteBookCommand deleteCommand =
                new DeleteBookCommand(new BookNameContainsExactKeywordsPredicate(bookToDelete.getBookName()));

        String expectedMessage = String.format(DeleteBookCommand.MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete);

        ModelManager expectedModel = new ModelManager(model.getBookShelf(), new UserPrefs());
        expectedModel.deleteBook(bookToDelete);
        expectedModel.commitBookShelf();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidBookNameUnfilteredList_throwsCommandException() {
        DeleteBookCommand deleteCommand =
                new DeleteBookCommand(new BookNameContainsExactKeywordsPredicate(new BookName("Invalid")));

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK);
    }

    @Test
    public void execute_validBookNameFilteredList_success() {
        Book bookToDelete = model.getFilteredBookList().get(0);
        showBookOfExactName(model, bookToDelete.getBookName());
        DeleteBookCommand deleteCommand =
                new DeleteBookCommand(new BookNameContainsExactKeywordsPredicate(bookToDelete.getBookName()));

        String expectedMessage = String.format(DeleteBookCommand.MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete);

        Model expectedModel = new ModelManager(model.getBookShelf(), new UserPrefs());
        expectedModel.deleteBook(bookToDelete);
        expectedModel.commitBookShelf();
        showNoBook(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidBookNameFilteredList_throwsCommandException() {
        BookName invalidBook = new BookName("invalid");
        showBookOfExactName(model, invalidBook);

        DeleteBookCommand deleteCommand =
                new DeleteBookCommand(new BookNameContainsExactKeywordsPredicate(invalidBook));

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK);
    }

    /**

     @Test
     public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
     Book bookToDelete = model.getFilteredBookList().get(0);
     DeleteBookCommand deleteCommand = new DeleteBookCommand(INDEX_FIRST_BOOK);
     Model expectedModel = new ModelManager(model.getBookShelf(), new UserPrefs());
     expectedModel.deleteBook(bookToDelete);
     expectedModel.commitAddressBook();

     // delete -> first book deleted
     deleteCommand.execute(model, commandHistory);

     // undo -> reverts addressbook back to previous state and filtered book list to show all books
     expectedModel.undoAddressBook();
     assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

     // redo -> same first book deleted again
     expectedModel.redoAddressBook();
     assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
     }

     @Test
     public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
     Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
     DeleteBookCommand deleteCommand = new DeleteBookCommand(outOfBoundIndex);

     // execution failed -> address book state not added into model
     assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK);

     // single address book state in model -> undoCommand and redoCommand fail
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
    /**
     @Test
     public void executeUndoRedo_validIndexFilteredList_sameBookDeleted() throws Exception {
     DeleteBookCommand deleteCommand = new DeleteBookCommand(INDEX_FIRST_BOOK);
     Model expectedModel = new ModelManager(model.getBookShelf(), new UserPrefs());

     showBookAtIndex(model, INDEX_SECOND_BOOK);
     Book bookToDelete = model.getFilteredBookList().get(0);
     expectedModel.deleteBook(bookToDelete);
     expectedModel.commitAddressBook();

     // delete -> deletes second book in unfiltered book list / first book in filtered book list
     deleteCommand.execute(model, commandHistory);

     // undo -> reverts addressbook back to previous state and filtered book list to show all books
     expectedModel.undoAddressBook();
     assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

     assertNotEquals(bookToDelete, model.getFilteredBookList().get(0));
     // redo -> deletes same second book in unfiltered book list
     expectedModel.redoAddressBook();
     assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
     }
     **/

    @Test
    public void equals() {
        Book firstBookToDelete = model.getFilteredBookList().get(0);
        Book secondBookToDelete = model.getFilteredBookList().get(1);
        DeleteBookCommand deleteFirstCommand =
                new DeleteBookCommand(new BookNameContainsExactKeywordsPredicate(firstBookToDelete.getBookName()));
        DeleteBookCommand deleteSecondCommand =
                new DeleteBookCommand(new BookNameContainsExactKeywordsPredicate(secondBookToDelete.getBookName()));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteBookCommand deleteFirstCommandCopy =
                new DeleteBookCommand(new BookNameContainsExactKeywordsPredicate(firstBookToDelete.getBookName()));
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
