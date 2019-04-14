package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRestaurantAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RESTAURANT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_RESTAURANT;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PostalDataSet;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Weblink;

/**
 * Contains integration tests (interaction with the Model) for {@code VisitWebCommand}.
 */
public class VisitWebCommandTest {
    private Model model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
    private Model expectedModel = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastRestaurantIndex = Index.fromOneBased(model.getFilteredRestaurantList().size());

        assertExecutionSuccess(INDEX_FIRST_RESTAURANT);
        assertExecutionSuccess(INDEX_THIRD_RESTAURANT);
        assertExecutionSuccess(lastRestaurantIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredRestaurantList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRestaurantAtIndex(model, INDEX_FIRST_RESTAURANT);
        showRestaurantAtIndex(expectedModel, INDEX_FIRST_RESTAURANT);

        assertExecutionSuccess(INDEX_FIRST_RESTAURANT);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showRestaurantAtIndex(model, INDEX_FIRST_RESTAURANT);
        showRestaurantAtIndex(expectedModel, INDEX_FIRST_RESTAURANT);

        Index outOfBoundsIndex = INDEX_SECOND_RESTAURANT;
        // ensures that outOfBoundIndex is still in bounds of food diary list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getFoodDiary().getRestaurantList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validUrl_success() {
        Weblink validWeblink = new Weblink("https://www.kfc.com.sg");
        assertExecutionSuccess(validWeblink);
    }

    @Test
    public void equals() {
        VisitWebCommand visitWebFirstCommand = new VisitWebCommand(INDEX_FIRST_RESTAURANT);
        VisitWebCommand visitWebSecondCommand = new VisitWebCommand(INDEX_SECOND_RESTAURANT);

        // same object -> returns true
        assertTrue(visitWebFirstCommand.equals(visitWebFirstCommand));

        // same values -> returns true
        VisitWebCommand visitWebFirstCommandCopy = new VisitWebCommand(INDEX_FIRST_RESTAURANT);
        assertTrue(visitWebFirstCommand.equals(visitWebFirstCommandCopy));

        // different types -> returns false
        assertFalse(visitWebFirstCommand.equals(1));

        // null -> returns false
        assertFalse(visitWebFirstCommand.equals(null));

        // different restaurant -> returns false
        assertFalse(visitWebFirstCommand.equals(visitWebSecondCommand));
    }

    /**
     * Executes a {@code VisitWebCommand} with the given {@code index},
     * and checks that the invoking visitWeb index does not do any changes to the model
     */
    private void assertExecutionSuccess(Index index) {
        VisitWebCommand visitWebCommand = new VisitWebCommand(index);
        String expectedMessage = String.format(VisitWebCommand.MESSAGE_VISIT_RESTAURANT_SUCCESS, index.getOneBased());

        assertCommandSuccess(visitWebCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code VisitWebCommand} with the given {@code url},
     * and checks that the model's VisitWebed restaurant is set to the restaurant at {@code index} in the filtered
     * restaurant list.
     */
    private void assertExecutionSuccess(Weblink validWeblink) {
        VisitWebCommand visitWebCommand = new VisitWebCommand(validWeblink);
        String expectedMessage = String.format(VisitWebCommand.MESSAGE_VISIT_WEBLINK, validWeblink.value);

        assertCommandSuccess(visitWebCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code VisitWebCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        VisitWebCommand visitWebCommand = new VisitWebCommand(index);
        assertCommandFailure(visitWebCommand, model, commandHistory, expectedMessage);
    }
}
