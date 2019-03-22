package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ALI;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookOfExactName;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditBookCommand.EditBookDescriptor;
import seedu.address.model.BookShelf;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.EditBookDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditBookCommand.
 */
public class EditBookCommandTest {

    private Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Book editedBook = new BookBuilder().build();
        BookNameContainsExactKeywordsPredicate firstPredicate =
                new BookNameContainsExactKeywordsPredicate(model.getBookShelf().getBookList().get(0).getBookName());
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder(editedBook).build();
        EditBookCommand editCommand = new EditBookCommand(firstPredicate, descriptor);

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new BookShelf(model.getBookShelf()), new UserPrefs());
        expectedModel.setBook(model.getFilteredBookList().get(0), editedBook);
        expectedModel.commitBookShelf();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Book lastBook = model.getFilteredBookList().get(model.getFilteredBookList().size() - 1);
        BookNameContainsExactKeywordsPredicate firstPredicate =
                new BookNameContainsExactKeywordsPredicate(lastBook.getBookName());

        BookBuilder bookInList = new BookBuilder(lastBook);
        Book editedBook = bookInList.withBookName(VALID_BOOKNAME_CS).withAuthor(VALID_AUTHOR_CS)
                .withTags(VALID_TAG_TEXTBOOK).build();

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_CS)
                .withAuthor(VALID_AUTHOR_CS).withTags(VALID_TAG_TEXTBOOK).build();
        EditBookCommand editCommand = new EditBookCommand(firstPredicate, descriptor);

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new BookShelf(model.getBookShelf()), new UserPrefs());
        expectedModel.setBook(lastBook, editedBook);
        expectedModel.commitBookShelf();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        BookNameContainsExactKeywordsPredicate firstPredicate =
                new BookNameContainsExactKeywordsPredicate(model.getBookShelf().getBookList().get(0).getBookName());
        EditBookCommand editCommand = new EditBookCommand(firstPredicate, new EditBookDescriptor());
        Book editedBook = model.getFilteredBookList().get(0);

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new BookShelf(model.getBookShelf()), new UserPrefs());
        expectedModel.commitBookShelf();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Book bookInFilteredList = model.getFilteredBookList().get(0);
        Book editedBook = new BookBuilder(bookInFilteredList).withBookName(VALID_BOOKNAME_CS).build();
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate(bookInFilteredList.getBookName());
        EditBookCommand editCommand = new EditBookCommand(predicate,
                new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_CS).build());

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new BookShelf(model.getBookShelf()), new UserPrefs());
        expectedModel.setBook(model.getFilteredBookList().get(0), editedBook);
        expectedModel.commitBookShelf();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBookUnfilteredList_failure() {
        Book firstBook = model.getFilteredBookList().get(0);
        Book second = model.getFilteredBookList().get(1);
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate(firstBook.getBookName());
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder(second).build();
        EditBookCommand editCommand = new EditBookCommand(predicate, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditBookCommand.MESSAGE_DUPLICATE_BOOK);
    }

    @Test
    public void execute_duplicateBookFilteredList_failure() {
        // edit book in filtered list into a duplicate in address book
        Book firstBookInList = model.getBookShelf().getBookList().get(0);
        Book secondBookInList = model.getBookShelf().getBookList().get(1);
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate(firstBookInList.getBookName());
        EditBookCommand editCommand = new EditBookCommand(predicate,
                new EditBookDescriptorBuilder(secondBookInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditBookCommand.MESSAGE_DUPLICATE_BOOK);
    }


    @Test
    public void executeUndoRedo_validBook_success() throws Exception {
        Book editedBook = new BookBuilder().build();
        Book bookToEdit = model.getFilteredBookList().get(0);
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate(bookToEdit.getBookName());
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder(editedBook).build();
        EditBookCommand editCommand = new EditBookCommand(predicate, descriptor);
        Model expectedModel = new ModelManager(new BookShelf(model.getBookShelf()), new UserPrefs());
        expectedModel.setBook(bookToEdit, editedBook);
        expectedModel.commitBookShelf();

        // edit -> first book edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered book list to show all books
        expectedModel.undoBookShelf();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first book edited again
        expectedModel.redoBookShelf();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidBook_failure() {
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate(new BookName("Invalid"));
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_CS).build();
        EditBookCommand editCommand = new EditBookCommand(predicate, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Book} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited book in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the book object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameBookEdited() throws Exception {
        Book bookToEdit = model.getFilteredBookList().get(1);
        Book editedBook = new BookBuilder(bookToEdit).build();
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate(bookToEdit.getBookName());
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder(editedBook).build();
        EditBookCommand editCommand = new EditBookCommand(predicate, descriptor);
        Model expectedModel = new ModelManager(new BookShelf(model.getBookShelf()), new UserPrefs());

        showBookOfExactName(model, bookToEdit.getBookName());
        expectedModel.setBook(bookToEdit, editedBook);
        expectedModel.commitBookShelf();

        // edit -> edits second book in unfiltered book list / first book in filtered book list
        editCommand.execute(model, commandHistory);

        // undo -> reverts book shelf back to previous state and filtered book list to show all books
        expectedModel.undoBookShelf();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredBookList().get(0), bookToEdit);
        // redo -> edits same second book in unfiltered book list
        expectedModel.redoBookShelf();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate(new BookName(VALID_BOOKNAME_ALICE));
        final EditBookCommand standardCommand = new EditBookCommand(predicate, DESC_ALI);

        // same values -> returns true
        EditBookDescriptor copyDescriptor = new EditBookDescriptor(DESC_ALI);
        EditBookCommand commandWithSameValues = new EditBookCommand(predicate, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditBookCommand(predicate, DESC_CS)));
    }
}
