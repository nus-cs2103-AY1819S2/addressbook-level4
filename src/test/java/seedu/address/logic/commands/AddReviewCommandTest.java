package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;
import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewTitle;

public class AddReviewCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final BookNameContainsExactKeywordsPredicate validPredicate =
            new BookNameContainsExactKeywordsPredicate(new BookName("Alice in Wonderland"));
    private static final Review validReview = new Review(
            new ReviewTitle("Carroll has depicted a unique world I hadn't seen before"),
            "Alice's Adventures in Wonderland by Lewis Carroll is a story "
                    + "about Alice who falls down a rabbit hole and lands into a fantasy world that is full of weird, "
                    + "wonderful people and animals.");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

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
    //    @Test
    //    public void equals() {
    //        BookNameContainsExactKeywordsPredicate alicePredicate =
    //                new BookNameContainsExactKeywordsPredicate(new BookName("Alice"));
    //        BookNameContainsExactKeywordsPredicate bobPredicate =
    //                new BookNameContainsExactKeywordsPredicate(new BookName("Bob"));
    //        AddReviewCommand addAliceCommand = new AddReviewCommand(validReview, alicePredicate);
    //        AddReviewCommand addBobCommand = new AddReviewCommand(validReview, bobPredicate);
    //
    //        // same object -> returns true
    //        assertTrue(addAliceCommand.equals(addAliceCommand));
    //
    //        // same values -> returns true
    //        AddReviewCommand addAliceCommandCopy = new AddReviewCommand(validReview, alicePredicate);
    //        assertTrue(addAliceCommand.equals(addAliceCommandCopy));
    //
    //        // different types -> returns false
    //        assertFalse(addAliceCommand.equals(1));
    //
    //        // null -> returns false
    //        assertFalse(addAliceCommand.equals(null));
    //
    //        // different book -> returns false
    //        assertFalse(addAliceCommand.equals(addBobCommand));
    //    }
}
