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
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showBookOfExactName;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder(editedBook).build();
        EditBookCommand editCommand = new EditBookCommand(INDEX_FIRST_BOOK, descriptor);

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new BookShelf(model.getBookShelf()), new UserPrefs());
        expectedModel.setBook(model.getFilteredBookList().get(0), editedBook);
        expectedModel.commitBookShelf();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastBook = Index.fromOneBased(model.getFilteredBookList().size());
        Book lastBook = model.getFilteredBookList().get(indexLastBook.getZeroBased());

        BookBuilder bookInList = new BookBuilder(lastBook);
        Book editedBook = bookInList.withBookName(VALID_BOOKNAME_CS).withAuthor(VALID_AUTHOR_CS)
                .withTags(VALID_TAG_TEXTBOOK).build();

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_CS)
                .withAuthor(VALID_AUTHOR_CS).withTags(VALID_TAG_TEXTBOOK).build();
        EditBookCommand editCommand = new EditBookCommand(indexLastBook, descriptor);

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new BookShelf(model.getBookShelf()), new UserPrefs());
        expectedModel.setBook(lastBook, editedBook);
        expectedModel.commitBookShelf();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditBookCommand editCommand = new EditBookCommand(INDEX_FIRST_BOOK, new EditBookDescriptor());
        Book editedBook = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new BookShelf(model.getBookShelf()), new UserPrefs());
        expectedModel.commitBookShelf();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Book bookInFilteredList = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Book editedBook = new BookBuilder(bookInFilteredList).withBookName(VALID_BOOKNAME_CS).build();
        EditBookCommand editCommand = new EditBookCommand(INDEX_FIRST_BOOK,
                new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_CS).build());

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new BookShelf(model.getBookShelf()), new UserPrefs());
        expectedModel.setBook(model.getFilteredBookList().get(0), editedBook);
        expectedModel.commitBookShelf();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBookUnfilteredList_failure() {
        Book firstBook = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder(firstBook).build();
        EditBookCommand editCommand = new EditBookCommand(INDEX_SECOND_BOOK, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditBookCommand.MESSAGE_DUPLICATE_BOOK);
    }

    @Test
    public void execute_duplicateBookFilteredList_failure() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        // edit book in filtered list into a duplicate in address book
        Book bookInList = model.getBookShelf().getBookList().get(INDEX_SECOND_BOOK.getZeroBased());
        EditBookCommand editCommand = new EditBookCommand(INDEX_FIRST_BOOK,
                new EditBookDescriptorBuilder(bookInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditBookCommand.MESSAGE_DUPLICATE_BOOK);
    }

    @Test
    public void execute_invalidBookIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_CS).build();
        EditBookCommand editCommand = new EditBookCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidBookIndexFilteredList_failure() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);
        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBookShelf().getBookList().size());

        EditBookCommand editCommand = new EditBookCommand(outOfBoundIndex,
                new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_CS).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Book editedBook = new BookBuilder().build();
        Book bookToEdit = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder(editedBook).build();
        EditBookCommand editCommand = new EditBookCommand(INDEX_FIRST_BOOK, descriptor);
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
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_CS).build();
        EditBookCommand editCommand = new EditBookCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

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
        Book editedBook = new BookBuilder().build();
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder(editedBook).build();
        EditBookCommand editCommand = new EditBookCommand(INDEX_FIRST_BOOK, descriptor);
        Model expectedModel = new ModelManager(new BookShelf(model.getBookShelf()), new UserPrefs());

        showBookAtIndex(model, INDEX_SECOND_BOOK);
        Book bookToEdit = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        expectedModel.setBook(bookToEdit, editedBook);
        expectedModel.commitBookShelf();

        // edit -> edits second book in unfiltered book list / first book in filtered book list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered book list to show all books
        expectedModel.undoBookShelf();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased()), bookToEdit);
        // redo -> edits same second book in unfiltered book list
        expectedModel.redoBookShelf();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditBookCommand standardCommand = new EditBookCommand(INDEX_FIRST_BOOK, DESC_ALI);

        // same values -> returns true
        EditBookDescriptor copyDescriptor = new EditBookDescriptor(DESC_ALI);
        EditBookCommand commandWithSameValues = new EditBookCommand(INDEX_FIRST_BOOK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditBookCommand(INDEX_SECOND_BOOK, DESC_ALI)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditBookCommand(INDEX_FIRST_BOOK, DESC_CS)));
    }
}