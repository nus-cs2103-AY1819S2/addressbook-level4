package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelfWithAllReviews;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REVIEW;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Review;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteReviewCommand}.
 */
public class DeleteReviewCommandTest {
    private Model model = new ModelManager(getTypicalBookShelfWithAllReviews(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Review reviewToDelete = model.getFilteredReviewList().get(INDEX_FIRST_REVIEW.getZeroBased());
        DeleteReviewCommand deleteCommand =
                new DeleteReviewCommand(INDEX_FIRST_REVIEW);

        String expectedMessage = String.format(DeleteReviewCommand.MESSAGE_DELETE_REVIEW_SUCCESS,
                reviewToDelete.getTitle().fullName);

        ModelManager expectedModel = new ModelManager(model.getBookShelf(), new UserPrefs());
        expectedModel.deleteReview(reviewToDelete);
        expectedModel.commitBookShelf();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidReviewNameUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReviewList().size() + 1);
        DeleteReviewCommand deleteCommand =
                new DeleteReviewCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_REVIEW_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Review reviewToDelete = model.getFilteredReviewList().get(INDEX_FIRST_REVIEW.getZeroBased());
        DeleteReviewCommand deleteCommand = new DeleteReviewCommand(INDEX_FIRST_REVIEW);
        Model expectedModel = new ModelManager(model.getBookShelf(), new UserPrefs());
        expectedModel.deleteReview(reviewToDelete);
        expectedModel.commitBookShelf();

        // delete -> first review deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts reviewShelf back to previous state and filtered review list to show all reviews
        expectedModel.undoBookShelf();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first review deleted again
        expectedModel.redoBookShelf();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReviewList().size() + 1);
        DeleteReviewCommand deleteCommand = new DeleteReviewCommand(outOfBoundIndex);

        // execution failed -> review shelf state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_REVIEW_DISPLAYED_INDEX);

        // single review shelf state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }


    @Test
    public void equals() {
        DeleteReviewCommand deleteFirstCommand = new DeleteReviewCommand(INDEX_FIRST_REVIEW);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteReviewCommand deleteFirstCommandCopy = new DeleteReviewCommand(INDEX_FIRST_REVIEW);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoReview(Model model) {
        model.updateFilteredReviewList(p -> false);

        assertTrue(model.getFilteredReviewList().isEmpty());
    }
}
