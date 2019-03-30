package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEWTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditBookCommand.EditBookDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BookShelf;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;
import seedu.address.testutil.EditBookDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_BOOKNAME_ALICE = "Alice in Wonderland";
    public static final String VALID_BOOKNAME_CS = "Structure and Interpretation of Computer Programs";
    public static final String VALID_BOOKNAME_LIFE = "The Secret Life of Bees";
    public static final String VALID_BOOKNAME_TEST = "Test book";
    public static final String VALID_BOOKNAME_PART_ALICE = "Alice";
    public static final String VALID_BOOKNAME_PART_CS = "Computer";
    public static final String VALID_AUTHOR_ALICE = "Lewis Carroll";
    public static final String VALID_AUTHOR_CS = "Hal Abelson Jerry Sussman";
    public static final String VALID_AUTHOR_LIFE = "Sue Monk Kidd";
    public static final String VALID_AUTHOR_PART_ALICE = "Carroll";
    public static final String VALID_AUTHOR_PART_CS = "Sussman";
    public static final String VALID_RATING_ALICE = "5";
    public static final String VALID_RATING_CS = "4";
    public static final String VALID_RATING_LIFE = "3";
    public static final String VALID_TAG_FANTASY = "fantasy";
    public static final String VALID_TAG_TEXTBOOK = "textbook";
    public static final String VALID_TAG_INTERESTING = "interesting";
    public static final String VALID_REVIEWTITLE_ALICE = "Carroll has depicted a unique world I had not seen before";
    public static final String VALID_REVIEWTITLE_CS = "The book was awesome";
    public static final String VALID_REVIEWMESSAGE_ALICE = "Alice's Adventures in Wonderland by Lewis Carroll is "
            + "about Alice who falls down a rabbit hole and lands into a fantasy world that is full of weird, "
            + "wonderful people and animals.";
    public static final String VALID_REVIEWMESSAGE_CS = "It provides a great introduction to computer science. "
            + "The legendary SICP reminded me of the the Bible in many respects.";
    public static final String VALID_DATE = "2019/03/16 18:20:00";
    public static final String VALID_ALTERNATIVE_DATE = "2019/03/26 18:20:00";

    public static final String NAME_DESC_ALICE = " " + PREFIX_NAME + VALID_BOOKNAME_ALICE;
    public static final String NAME_DESC_CS = " " + PREFIX_NAME + VALID_BOOKNAME_CS;
    public static final String NAME_DESC_LIFE = " " + PREFIX_NAME + VALID_BOOKNAME_LIFE;
    public static final String NAME_DESC_TEST = " " + PREFIX_NAME + VALID_BOOKNAME_TEST;
    public static final String NAME_PART_DESC_ALICE = " " + PREFIX_NAME + VALID_BOOKNAME_PART_ALICE;
    public static final String NAME_PART_DESC_CS = " " + PREFIX_NAME + VALID_BOOKNAME_PART_CS;
    public static final String AUTHOR_DESC_ALICE = " " + PREFIX_AUTHOR + VALID_AUTHOR_ALICE;
    public static final String AUTHOR_DESC_CS = " " + PREFIX_AUTHOR + VALID_AUTHOR_CS;
    public static final String AUTHOR_DESC_LIFE = " " + PREFIX_AUTHOR + VALID_AUTHOR_LIFE;
    public static final String AUTHOR_PART_DESC_ALICE = " " + PREFIX_AUTHOR + VALID_AUTHOR_PART_ALICE;
    public static final String AUTHOR_PART_DESC_CS = " " + PREFIX_AUTHOR + VALID_AUTHOR_PART_CS;
    public static final String RATING_DESC_ALICE = " " + PREFIX_RATING + VALID_RATING_ALICE;
    public static final String RATING_DESC_CS = " " + PREFIX_RATING + VALID_RATING_CS;
    public static final String RATING_DESC_LIFE = " " + PREFIX_RATING + VALID_RATING_LIFE;
    public static final String TAG_DESC_FANTASY = " " + PREFIX_TAG + VALID_TAG_FANTASY;
    public static final String TAG_DESC_TEXTBOOK = " " + PREFIX_TAG + VALID_TAG_TEXTBOOK;
    public static final String TAG_DESC_INTERESTING = " " + PREFIX_TAG + VALID_TAG_INTERESTING;
    public static final String REVIEWTITLE_DESC_ALICE = " " + PREFIX_REVIEWTITLE + VALID_REVIEWTITLE_ALICE;
    public static final String REVIEWTITLE_DESC_CS = " " + PREFIX_REVIEWTITLE + VALID_REVIEWTITLE_CS;
    public static final String REVIEWMESSAGE_DESC_ALICE = " " + PREFIX_REVIEW + VALID_REVIEWMESSAGE_ALICE;
    public static final String REVIEWMESSAGE_DESC_CS = " " + PREFIX_REVIEW + VALID_REVIEWMESSAGE_CS;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_BOOKNAME_DESC = " " + PREFIX_NAME + "The K&d"; // '&' not allowed in names
    public static final String INVALID_AUTHOR_DESC = " " + PREFIX_AUTHOR + "J.K"; // '.' not allowed in authors
    public static final String INVALID_RATING_DESC = " " + PREFIX_RATING + "11"; // rating should be [1,10]
    public static final String INVALID_REVIEWTITLE_DESC = " " + PREFIX_RATING + "100%"; // '%' not allowed in reviews

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditBookDescriptor DESC_ALI;
    public static final EditBookDescriptor DESC_CS;

    public static final String SORT_NAME_WITH_PREFIX = " st/name";
    public static final String SORT_AUTHOR_WITH_PREFIX = " st/author";
    public static final String SORT_RATING_WITH_PREFIX = " st/rating";
    public static final String INVALID_SORT_TYPE_WITH_PREFIX = " st/type";
    public static final String ORDER_ASC_WITH_PREFIX = " o/asc";
    public static final String ORDER_DES_WITH_PREFIX = " o/des";
    public static final String INVALID_ORDER_WITH_PREFIX = "o/abc";
    public static final String SORT_NAME_WITHOUT_PREFIX = "name";
    public static final String SORT_AUTHOR_WITHOUT_PREFIX = "author";
    public static final String SORT_RATING_WITHOUT_PREFIX = "rating";
    public static final String ORDER_ASC_WITHOUT_PREFIX = "asc";
    public static final String ORDER_DES_WITHOUT_PREFIX = "des";
    public static final String EMPTY_STR = "      ";
    public static final String SORT_EXCEPTION = "The list is not sorted correctly";

    static {
        DESC_ALI = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_ALICE)
                .withAuthor(VALID_AUTHOR_ALICE).withRating(VALID_RATING_ALICE)
                .withTags(VALID_TAG_FANTASY).build();
        DESC_CS = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_CS)
                .withAuthor(VALID_AUTHOR_CS).withRating(VALID_RATING_CS)
                .withTags(VALID_TAG_TEXTBOOK).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the book shelf, filtered book list and selected book in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        BookShelf expectedBookShelf = new BookShelf(actualModel.getBookShelf());
        List<Book> expectedFilteredList = new ArrayList<>(actualModel.getFilteredBookList());
        Book expectedSelectedBook = actualModel.getSelectedBook();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedBookShelf, actualModel.getBookShelf());
            assertEquals(expectedFilteredList, actualModel.getFilteredBookList());
            assertEquals(expectedSelectedBook, actualModel.getSelectedBook());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the book at the given {@code targetIndex} in the
     * {@code model}'s book shelf.
     */
    public static void showBookAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredBookList().size());

        Book book = model.getFilteredBookList().get(targetIndex.getZeroBased());
        model.updateFilteredBookList(new BookNameContainsExactKeywordsPredicate(book.getBookName()));

        assertEquals(1, model.getFilteredBookList().size());

    }

    /**
     * Updates {@code model}'s filtered list to show only the book of the given {@code name} in the
     * {@code model}'s book shelf. The book should be present in the book shelf.
     */
    public static void showBookOfExactName(Model model, BookName name) {
        model.updateFilteredBookList(new BookNameContainsExactKeywordsPredicate(name));
    }

    /**
     * Deletes the first book in {@code model}'s filtered list from {@code model}'s book shelf.
     */
    public static void deleteFirstBook(Model model) {
        Book firstBook = model.getFilteredBookList().get(0);
        model.deleteBook(firstBook);
        model.commitBookShelf();
    }
}
