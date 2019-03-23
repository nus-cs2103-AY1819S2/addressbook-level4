package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_BOOKS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalBooks.BOOKTHIEF;
import static seedu.address.testutil.TypicalBooks.HUNGERGAME;
import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_COLLINS;
import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_FANTASY;
import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_LIFE;
import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_SIX;
import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_ZUSAK;
import static seedu.address.testutil.TypicalBooks.LIFEPI;
import static seedu.address.testutil.TypicalBooks.LIFEWAO;
import static seedu.address.testutil.TypicalBooks.MIDDLESEX;

import org.junit.Test;

import seedu.address.logic.commands.DeleteBookCommand;
import seedu.address.logic.commands.ListBookCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class ListBookCommandSystemTest extends BookShelfSystemTest {

    @Test
    public void find() {
        /* Case: find multiple books in book shelf, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + ListBookCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_LIFE + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredBookList(expectedModel, LIFEPI, LIFEWAO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where book list is displaying the books we are finding
         * -> 2 books found
         */
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_LIFE;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book where book list is not displaying the person we are finding -> 1 book found */
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_AUTHOR + KEYWORD_MATCHING_COLLINS;
        ModelHelper.setFilteredBookList(expectedModel, HUNGERGAME);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in book shelf, 2 keywords -> 2 books found */
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_AUTHOR + KEYWORD_MATCHING_ZUSAK + "   ";
        command = command + PREFIX_AUTHOR + KEYWORD_MATCHING_COLLINS + "   ";
        ModelHelper.setFilteredBookList(expectedModel, BOOKTHIEF, HUNGERGAME);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in book shelf, 2 keywords in reversed order -> 2 books found */
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_AUTHOR + KEYWORD_MATCHING_COLLINS + "   ";
        command = command + PREFIX_AUTHOR + KEYWORD_MATCHING_ZUSAK + "   ";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in book shelf, 2 keywords with 1 repeat -> 2 books found */
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_AUTHOR + KEYWORD_MATCHING_ZUSAK + "   ";
        command = command + PREFIX_AUTHOR + KEYWORD_MATCHING_COLLINS + "   ";
        command = command + PREFIX_AUTHOR + KEYWORD_MATCHING_ZUSAK;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_AUTHOR + KEYWORD_MATCHING_ZUSAK + "   ";
        command = command + PREFIX_AUTHOR + KEYWORD_MATCHING_COLLINS + "   ";
        command = command + PREFIX_AUTHOR + "NoKeywordMatching";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same books in book shelf after deleting 1 of them -> 1 person found */
        command = DeleteBookCommand.COMMAND_WORD + " " + PREFIX_NAME + BOOKTHIEF.getBookName().fullName;
        executeCommand(command);
        assertFalse(getModel().getBookShelf().getBookList().contains(BOOKTHIEF));
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_AUTHOR + KEYWORD_MATCHING_COLLINS + "   ";
        expectedModel = getModel();
        ModelHelper.setFilteredBookList(expectedModel, HUNGERGAME);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in book shelf, keyword is same as name but of different case -> 1 book found */
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_AUTHOR + "cOLLIns";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in book shelf, keyword is substring of name -> 0 books found */
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_NAME + "Hun";
        ModelHelper.setFilteredBookList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in address book, author name is substring of keyword -> 0 books found */
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_AUTHOR + "CollinsMoreLetters";
        ModelHelper.setFilteredBookList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book not in book shelf -> 0 books found */
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_NAME + "NoBooksFound";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find rating of book in book shelf -> 2 books found */
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_RATING + KEYWORD_MATCHING_SIX;
        ModelHelper.setFilteredBookList(expectedModel, HUNGERGAME, MIDDLESEX);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tag of BOOK in book shelf -> 2 books found */
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_TAG + KEYWORD_MATCHING_FANTASY;
        ModelHelper.setFilteredBookList(expectedModel, HUNGERGAME, LIFEPI);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find BOOK in empty book shelf -> 0 books found */
        deleteAllBooks();
        command = ListBookCommand.COMMAND_WORD + " " + PREFIX_TAG + KEYWORD_MATCHING_FANTASY;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "LIstBOOk";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_BOOKS_LISTED_OVERVIEW, expectedModel.getFilteredBookList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
