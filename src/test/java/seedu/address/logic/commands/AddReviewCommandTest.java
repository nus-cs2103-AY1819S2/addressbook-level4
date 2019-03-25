package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import java.util.HashSet;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.BookShelf;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;
import seedu.address.model.book.Review;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.ReviewBuilder;


public class AddReviewCommandTest {

    private Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Review validReview = ReviewBuilder.build();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //    @Test
    //    public void constructor_nullReview_throwsNullPointerException() {
    //        thrown.expect(NullPointerException.class);
    //        new AddReviewCommand(null, validPredicate);
    //    }
    //
    //    @Test
    //    public void constructor_nullPredicate_throwsNullPointerException() {
    //        thrown.expect(NullPointerException.class);
    //        new AddReviewCommand(validReview, null);
    //    }
    //
    @Test
    public void equals() {
        BookNameContainsExactKeywordsPredicate alicePredicate =
                new BookNameContainsExactKeywordsPredicate(new BookName("Alice"));
        BookNameContainsExactKeywordsPredicate bobPredicate =
                new BookNameContainsExactKeywordsPredicate(new BookName("Bob"));
        AddReviewCommand addAliceCommand = new AddReviewCommand(validReview, alicePredicate);
        AddReviewCommand addBobCommand = new AddReviewCommand(validReview, bobPredicate);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddReviewCommand addAliceCommandCopy = new AddReviewCommand(validReview, alicePredicate);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different book -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void execute_success() {
        HashSet<Review> reviewSet = new HashSet<>();
        reviewSet.add(validReview);
        Book addedReviewBook = new BookBuilder().withReview(reviewSet).buildLifePi();
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate(addedReviewBook.getBookName());
        AddReviewCommand addReviewCommand = new AddReviewCommand(validReview, predicate);

        String expectedMessage = String.format(AddReviewCommand.MESSAGE_SUCCESS, validReview.getTitle());

        Model expectedModel = new ModelManager(new BookShelf(model.getBookShelf()), new UserPrefs());
        expectedModel.updateFilteredBookList(predicate);
        expectedModel.setBook(expectedModel.getFilteredBookList().get(0), addedReviewBook);
        expectedModel.commitBookShelf();

        assertCommandSuccess(addReviewCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
