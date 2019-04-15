package systemtests;

import static seedu.address.logic.commands.SummaryCommand.MESSAGE_NO_AUTHOR_PREFERED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.testutil.TypicalBooks.ANOTHER_ERIKA_WORK;

import org.junit.Test;

import seedu.address.logic.commands.AddBookCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SummaryCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

public class SummaryCommandSystemTest extends BookShelfSystemTest {
    private static final String command = SummaryCommand.COMMAND_WORD;
    @Test
    public void summary() {
        Model expectedModel = getModel();
        String expectedResultMessage = "You've read 8 books.\n"
            + MESSAGE_NO_AUTHOR_PREFERED
            + "Book(s) receive a rating of 10 from you: To Kill a Mocking Bird\n"
            + "You prefer books that you labeled as fantasy(including The Hunger Games, Life of Pi)\n";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);

        /* Case: undo previous summary command -> rejected */
        String undoCommand = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(undoCommand, expectedResultMessage);

        /* Case: redo previous summary command -> rejected */
        String redoCommand = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(redoCommand, expectedResultMessage);

        /* Case: get the summary of the book shelf after 1 book deleted -> summary changes */
        Book toAdd = ANOTHER_ERIKA_WORK;
        String addCommand = AddBookCommand.COMMAND_WORD + " " + PREFIX_NAME + toAdd.getBookName().fullName
                + " " + PREFIX_AUTHOR + toAdd.getAuthor().fullName
                + " " + PREFIX_RATING + toAdd.getRating().value;
        executeCommand(addCommand);
        expectedModel.addBook(toAdd);
        assert(expectedModel.getBookShelf().getBookList().size() == 9);
        expectedResultMessage = "You've read 9 books.\n"
                + "You prefer books by Erika Leonard, as you've read: "
                + "Fifty Shades of Grey, Fifty Shades Darker\n"
                + "Book(s) receive a rating of 10 from you: To Kill a Mocking Bird\n"
                + "You prefer books that you labeled as fantasy(including The Hunger Games, Life of Pi)\n";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays with a summary of the book in the book shelf
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class.
     * @see BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
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
        assertSelectedBookCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
