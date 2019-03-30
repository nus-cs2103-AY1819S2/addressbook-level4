package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_BOOKS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.BOOKTHIEF;
import static seedu.address.testutil.TypicalBooks.HUNGERGAME;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.BookListFilterPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code listBookCommand}.
 */
public class ListBookCommandTest {
    private Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        BookListFilterPredicate firstPredicate =
                new BookListFilterPredicate(Collections.singletonList("first"),
                        Collections.singletonList("first"),
                        Collections.singletonList("first"),
                        Collections.singletonList("first"));
        BookListFilterPredicate secondPredicate =
                new BookListFilterPredicate(Collections.singletonList("second"),
                        Collections.singletonList("second"),
                        Collections.singletonList("second"),
                        Collections.singletonList("second"));

        ListBookCommand listFirstCommand = new ListBookCommand(firstPredicate);
        ListBookCommand listSecondCommand = new ListBookCommand(secondPredicate);

        // same object -> returns true
        assertTrue(listFirstCommand.equals(listFirstCommand));

        // same values -> returns true
        ListBookCommand listFirstCommandCopy = new ListBookCommand(firstPredicate);
        assertTrue(listFirstCommand.equals(listFirstCommandCopy));

        // different types -> returns false
        assertFalse(listFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listFirstCommand.equals(null));

        // different book -> returns false
        assertFalse(listFirstCommand.equals(listSecondCommand));
    }

    @Test
    public void execute_zeroKeywords() {
        String expectedMessage = String.format(MESSAGE_BOOKS_LISTED_OVERVIEW, 8);
        List<String> names = new ArrayList<String>();
        List<String> authors = new ArrayList<String>();
        List<String> tags = new ArrayList<String>();
        List<String> ratings = new ArrayList<String>();
        BookListFilterPredicate predicate = preparePredicate(names, authors, tags, ratings);
        ListBookCommand command = new ListBookCommand(predicate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleKeyWords() {
        String expectedMessage = String.format(MESSAGE_BOOKS_LISTED_OVERVIEW, 1);
        String[] arr1 = {"Thief"};
        String[] arr2 = {"Zusak"};
        String[] arr3 = {"popular"};
        String[] arr4 = {"7"};
        BookListFilterPredicate predicate = preparePredicate(Arrays.asList(arr1),
                Arrays.asList(arr2),
                Arrays.asList(arr3),
                Arrays.asList(arr4));
        ListBookCommand command = new ListBookCommand(predicate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BOOKTHIEF), model.getFilteredBookList());
    }

    @Test
    public void execute_multipleBooksFound() {
        String expectedMessage = String.format(MESSAGE_BOOKS_LISTED_OVERVIEW, 2);
        String[] arr1 = {"The"};
        String[] arr2 = {"Zusak", "Collins"};
        String[] arr3 = {"popular", "fantasy"};
        String[] arr4 = {"6", "7"};
        BookListFilterPredicate predicate = preparePredicate(Arrays.asList(arr1),
                Arrays.asList(arr2),
                Arrays.asList(arr3),
                Arrays.asList(arr4));
        ListBookCommand command = new ListBookCommand(predicate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BOOKTHIEF, HUNGERGAME), model.getFilteredBookList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private BookListFilterPredicate preparePredicate(List<String> names, List<String> authors, List<String> tags,
            List<String> ratings) {
        return new BookListFilterPredicate(names, authors, tags, ratings);
    }

}
