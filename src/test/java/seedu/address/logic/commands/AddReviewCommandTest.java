package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWMESSAGE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWTITLE_ALICE;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelfWithReview;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewTitle;
import seedu.address.testutil.ReviewBuilder;


public class AddReviewCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullReview_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReviewCommand(null);
    }

    @Test
    public void execute_reviewAcceptedByModel_addSuccessful() throws Exception {
        Book bookToAddReview = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Review toAdd = new Review(new ReviewTitle(VALID_REVIEWTITLE_ALICE),
                bookToAddReview.getBookName(), VALID_REVIEWMESSAGE_ALICE);
        AddReviewCommand addReviewCommand = new AddReviewCommand(toAdd);

        String expectedMessage = String.format(AddReviewCommand.MESSAGE_SUCCESS, toAdd.getTitle().fullName);
        ModelManager expectedModel = new ModelManager(getTypicalBookShelfWithReview(toAdd), new UserPrefs());
        expectedModel.commitBookShelf();

        CommandResult commandResult = new AddReviewCommand(toAdd).execute(model, commandHistory);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateReview_throwsCommandException() throws Exception {
        Book bookToAddReview = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Review toAdd = new Review(new ReviewTitle(VALID_REVIEWTITLE_ALICE),
                bookToAddReview.getBookName(), VALID_REVIEWMESSAGE_ALICE);

        AddReviewCommand addReviewCommand = new AddReviewCommand(toAdd);
        addReviewCommand.execute(model, commandHistory);


        thrown.expect(CommandException.class);
        thrown.expectMessage(AddReviewCommand.MESSAGE_DUPLICATE_REVIEW);
        addReviewCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        Review alice = new ReviewBuilder().withBookName("Alice").build();
        Review bob = new ReviewBuilder().withBookName("Bob").build();
        AddReviewCommand addAliceCommand = new AddReviewCommand(alice);
        AddReviewCommand addBobCommand = new AddReviewCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddReviewCommand addAliceCommandCopy = new AddReviewCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different book -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

}
