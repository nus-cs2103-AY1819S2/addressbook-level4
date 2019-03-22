package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddBookCommand}.
 */
public class AddBookCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    }

    @Test
    public void execute_newBook_success() {
        Book validBook = new BookBuilder().build();

        Model expectedModel = new ModelManager(model.getBookShelf(), new UserPrefs());
        expectedModel.addBook(validBook);
        expectedModel.commitBookShelf();

        assertCommandSuccess(new AddBookCommand(validBook), model, commandHistory,
                String.format(AddBookCommand.MESSAGE_SUCCESS, validBook), expectedModel);
    }

    @Test
    public void execute_duplicateBook_throwsCommandException() {
        Book bookInList = model.getBookShelf().getBookList().get(0);
        assertCommandFailure(new AddBookCommand(bookInList), model, commandHistory,
                AddBookCommand.MESSAGE_DUPLICATE_BOOK);
    }
}
