package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelfWithAllReviews;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BOOK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class ListReviewCommandTest {
    private Model model = new ModelManager(getTypicalBookShelfWithAllReviews(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalBookShelfWithAllReviews(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastBookIndex = Index.fromOneBased(model.getFilteredBookList().size());

        assertExecutionSuccess(INDEX_FIRST_BOOK);
        assertExecutionSuccess(INDEX_THIRD_BOOK);
        assertExecutionSuccess(lastBookIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ListReviewCommand selectFirstCommand = new ListReviewCommand(INDEX_FIRST_BOOK);
        ListReviewCommand selectSecondCommand = new ListReviewCommand(INDEX_SECOND_BOOK);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        ListReviewCommand selectFirstCommandCopy = new ListReviewCommand(INDEX_FIRST_BOOK);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code ListReviewCommand} with the given {@code index},
     * and checks that the model's selected person is set to the person at {@code index} in the filtered person list.
     */
    private void assertExecutionSuccess(Index index) {
        ListReviewCommand selectCommand = new ListReviewCommand(index);
        String expectedMessage = String.format(ListReviewCommand.MESSAGE_SUCCESS, index.getOneBased());
        expectedModel.setSelectedBook(model.getFilteredBookList().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code ListReviewCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ListReviewCommand selectCommand = new ListReviewCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }
}
