package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_CS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalBooks.ALI;
import static seedu.address.testutil.TypicalBooks.CS;
import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_LIFE;
import static seedu.address.testutil.TypicalBooks.SECRETLIFE;
import static seedu.address.testutil.TypicalBooks.TWILIGHT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddBookCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Author;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BookUtil;

public class AddBookCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a book without tags to a non-empty book shelf, command with leading spaces and trailing spaces
         * -> added
         */
        Book toAdd = ALI;
        String command = "   " + AddBookCommand.COMMAND_WORD + "  " + NAME_DESC_ALICE + "  " + AUTHOR_DESC_ALICE + " "
                + RATING_DESC_ALICE + "   " + TAG_DESC_FANTASY + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addBook(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a book with all fields same as another book in the book shelf except name -> added */
        toAdd = new BookBuilder(ALI).withBookName(VALID_BOOKNAME_CS).build();
        command = AddBookCommand.COMMAND_WORD + NAME_DESC_CS + AUTHOR_DESC_ALICE + RATING_DESC_ALICE
                + TAG_DESC_FANTASY;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty book shelf -> added */
        deleteAllBooks();
        assertCommandSuccess(ALI);

        /* Case: add a book with tags, command with parameters in random order -> added */
        toAdd = CS;
        command = AddBookCommand.COMMAND_WORD + TAG_DESC_FANTASY + AUTHOR_DESC_CS + NAME_DESC_CS
                + TAG_DESC_TEXTBOOK + RATING_DESC_CS;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book, missing tags -> added */
        assertCommandSuccess(SECRETLIFE);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the book list before adding -> added */
        showBooksWithName(KEYWORD_MATCHING_LIFE);
        assertCommandSuccess(TWILIGHT);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate book -> rejected */
        command = BookUtil.getAddBookCommand(SECRETLIFE);
        assertCommandFailure(command, AddBookCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: add a duplicate book except with different author -> rejected */
        toAdd = new BookBuilder(SECRETLIFE).withAuthor(VALID_AUTHOR_CS).build();
        command = BookUtil.getAddBookCommand(toAdd);
        assertCommandFailure(command, AddBookCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: add a duplicate book except with different rating -> rejected */
        toAdd = new BookBuilder(SECRETLIFE).withRating(VALID_RATING_CS).build();
        command = BookUtil.getAddBookCommand(toAdd);
        assertCommandFailure(command, AddBookCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: add a duplicate book except with different tags -> rejected */
        command = BookUtil.getAddBookCommand(SECRETLIFE) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddBookCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: missing name -> rejected */
        command = AddBookCommand.COMMAND_WORD + AUTHOR_DESC_ALICE + RATING_DESC_ALICE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookCommand.MESSAGE_USAGE));

        /* Case: missing author -> rejected */
        command = AddBookCommand.COMMAND_WORD + NAME_DESC_ALICE + RATING_DESC_ALICE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookCommand.MESSAGE_USAGE));

        /* Case: missing rating -> rejected */
        command = AddBookCommand.COMMAND_WORD + NAME_DESC_ALICE + AUTHOR_DESC_ALICE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + BookUtil.getBookDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddBookCommand.COMMAND_WORD + INVALID_NAME_DESC + AUTHOR_DESC_ALICE + RATING_DESC_ALICE;
        assertCommandFailure(command, BookName.MESSAGE_CONSTRAINTS);

        /* Case: invalid author -> rejected */
        command = AddBookCommand.COMMAND_WORD + NAME_DESC_ALICE + INVALID_AUTHOR_DESC + RATING_DESC_ALICE;
        assertCommandFailure(command, Author.MESSAGE_CONSTRAINTS);

        /* Case: invalid rating -> rejected */
        command = AddBookCommand.COMMAND_WORD + NAME_DESC_ALICE + AUTHOR_DESC_ALICE + INVALID_RATING_DESC;
        assertCommandFailure(command, Rating.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddBookCommand.COMMAND_WORD + NAME_DESC_ALICE + AUTHOR_DESC_ALICE + RATING_DESC_ALICE
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddBookCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddBookCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code BookListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Book toAdd) {
        assertCommandSuccess(BookUtil.getAddBookCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Book)}. Executes {@code command}
     * instead.
     * @see AddBookCommandSystemTest#assertCommandSuccess(Book)
     */
    private void assertCommandSuccess(String command, Book toAdd) {
        Model expectedModel = getModel();
        expectedModel.addBook(toAdd);
        String expectedResultMessage = String.format(AddBookCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Book)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code BookListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddBookCommandSystemTest#assertCommandSuccess(String, Book)
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
     * 4. {@code Storage} and {@code BookListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
