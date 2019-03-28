package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_LIFE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BOOKNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_LIFE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_TEST;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_LIFE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_TEST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEXTBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;
import static seedu.address.testutil.TypicalBooks.CS;
import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_LIFE;
import static seedu.address.testutil.TypicalBooks.SECRETLIFE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditBookCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Rating;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BookUtil;

public class EditBookCommandSystemTest extends BookShelfSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_BOOK;
        String command = " " + EditBookCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_CS + "  "
                + AUTHOR_DESC_CS + " " + RATING_DESC_CS + "  " + " " + TAG_DESC_TEXTBOOK + " ";
        Book editedBook = new BookBuilder(CS).withTags(VALID_TAG_TEXTBOOK).build();
        assertCommandSuccess(command, index, editedBook);

        /* Case: undo editing the last book in the list -> last book restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last book in the list -> last book edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setBook(getModel().getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased()), editedBook);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a book with new values same as existing values -> edited */
        command = EditBookCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_CS + AUTHOR_DESC_CS + RATING_DESC_CS
                + TAG_DESC_FANTASY + TAG_DESC_TEXTBOOK;
        assertCommandSuccess(command, index, CS);

        /* Case: edit a book with new values same as another book's values but with different name -> edited */
        assertTrue(getModel().getBookShelf().getBookList().contains(CS));
        index = INDEX_SECOND_BOOK;
        assertNotEquals(getModel().getFilteredBookList().get(index.getZeroBased()), CS);
        command = EditBookCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_ALICE
                + AUTHOR_DESC_CS + RATING_DESC_CS
                + TAG_DESC_FANTASY + TAG_DESC_TEXTBOOK;
        editedBook = new BookBuilder(CS).withBookName(VALID_BOOKNAME_ALICE).build();
        assertCommandSuccess(command, index, editedBook);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_BOOK;
        command = EditBookCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Book bookToEdit = getModel().getFilteredBookList().get(index.getZeroBased());
        editedBook = new BookBuilder(bookToEdit).withTags().build();
        assertCommandSuccess(command, index, editedBook);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered book list, edit index within bounds of book shelf and book list -> edited */
        showBooksWithName(KEYWORD_MATCHING_LIFE);
        index = INDEX_FIRST_BOOK;
        assertTrue(index.getZeroBased() < getModel().getFilteredBookList().size());
        command = EditBookCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_TEST;
        bookToEdit = getModel().getFilteredBookList().get(index.getZeroBased());
        editedBook = new BookBuilder(bookToEdit).withBookName(VALID_BOOKNAME_TEST).build();
        assertCommandSuccess(command, index, editedBook);

        /* Case: filtered book list, edit index within bounds of book shelf but out of bounds of book list
         * -> rejected
         */
        showBooksWithName(KEYWORD_MATCHING_LIFE);
        int invalidIndex = getModel().getBookShelf().getBookList().size();
        assertCommandFailure(EditBookCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_CS,
                Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a book card is selected -------------------------- */

        /* Case: selects first card in the book list, edit a book -> edited, card selection remains unchanged but
         * browser url changes
         */
        /*
        showAllBooks();
        index = INDEX_FIRST_BOOK;
        selectBook(index);
        command = EditBookCommand.COMMAND_WORD + " " + index.getOneBased()
        + NAME_DESC_ALICE + AUTHOR_DESC_ALICE + RATING_DESC_ALICE
                + TAG_DESC_FANTASY;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new book's name
        assertCommandSuccess(command, index, ALI, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditBookCommand.COMMAND_WORD + " 0" + NAME_DESC_CS,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditBookCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditBookCommand.COMMAND_WORD + " -1" + NAME_DESC_CS,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditBookCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredBookList().size() + 1;
        assertCommandFailure(EditBookCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_CS,
                Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditBookCommand.COMMAND_WORD + NAME_DESC_CS,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditBookCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditBookCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased(),
                EditBookCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditBookCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_BOOK.getOneBased() + INVALID_BOOKNAME_DESC,
                BookName.MESSAGE_CONSTRAINTS);

        /* Case: invalid author -> rejected */
        assertCommandFailure(EditBookCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_BOOK.getOneBased() + INVALID_AUTHOR_DESC,
                Author.MESSAGE_CONSTRAINTS);

        /* Case: invalid rating -> rejected */
        assertCommandFailure(EditBookCommand.COMMAND_WORD
                        + " " + INDEX_FIRST_BOOK.getOneBased() + INVALID_RATING_DESC,
                Rating.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditBookCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_BOOK.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS);

        /* Case: edit a book with new values same as another book's values -> rejected */
        executeCommand(BookUtil.getAddBookCommand(SECRETLIFE));
        assertTrue(getModel().getBookShelf().getBookList().contains(SECRETLIFE));
        index = INDEX_FIRST_BOOK;
        assertFalse(getModel().getFilteredBookList().get(index.getZeroBased()).equals(CS));
        command = EditBookCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_LIFE + AUTHOR_DESC_LIFE + RATING_DESC_LIFE;
        assertCommandFailure(command, EditBookCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: edit a book with new values same as another book's values but with different tags -> rejected */
        command = EditBookCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_LIFE + AUTHOR_DESC_LIFE + RATING_DESC_LIFE
                + TAG_DESC_TEXTBOOK;
        assertCommandFailure(command, EditBookCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: edit a book with new values same as another book's values but with different author -> rejected */
        command = EditBookCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_LIFE + AUTHOR_DESC_ALICE + RATING_DESC_LIFE
                + TAG_DESC_FANTASY + TAG_DESC_TEXTBOOK;
        assertCommandFailure(command, EditBookCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: edit a book with new values same as another book's values but with different rating -> rejected */
        command = EditBookCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_LIFE + AUTHOR_DESC_LIFE + RATING_DESC_ALICE
                + TAG_DESC_FANTASY
                + TAG_DESC_TEXTBOOK;
        assertCommandFailure(command, EditBookCommand.MESSAGE_DUPLICATE_BOOK);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Book, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditBookCommandSystemTest#assertCommandSuccess(String, Index, Book, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Book editedBook) {
        assertCommandSuccess(command, toEdit, editedBook, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditBookCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the book at index {@code toEdit} being
     * updated to values specified {@code editedBook}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditBookCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Book editedBook,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setBook(expectedModel.getFilteredBookList().get(toEdit.getZeroBased()), editedBook);
        expectedModel.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditBookCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
     * {@code BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see BookShelfSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
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
     * {@code BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
