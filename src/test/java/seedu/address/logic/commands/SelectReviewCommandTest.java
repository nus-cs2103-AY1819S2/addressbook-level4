package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelfWithAllReviews;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REVIEW;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_REVIEW;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SelectReviewCommandTest {
    private Model model = new ModelManager(getTypicalBookShelfWithAllReviews(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalBookShelfWithAllReviews(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastReviewIndex = Index.fromOneBased(model.getFilteredReviewList().size());

        assertExecutionSuccess(INDEX_FIRST_REVIEW);
        assertExecutionSuccess(INDEX_SECOND_REVIEW);
        assertExecutionSuccess(lastReviewIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredReviewList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_REVIEW_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectReviewCommand selectFirstCommand = new SelectReviewCommand(INDEX_FIRST_REVIEW);
        SelectReviewCommand selectSecondCommand = new SelectReviewCommand(INDEX_SECOND_REVIEW);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectReviewCommand selectFirstCommandCopy = new SelectReviewCommand(INDEX_FIRST_REVIEW);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectReviewCommand} with the given {@code index},
     * and checks that the model's selected person is set to the person at {@code index} in the filtered person list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectReviewCommand selectCommand = new SelectReviewCommand(index);
        String expectedMessage = String.format(SelectReviewCommand.MESSAGE_SUCCESS, index.getOneBased());
        expectedModel.setSelectedReview(model.getFilteredReviewList().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectReviewCommand selectCommand = new SelectReviewCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }
}
