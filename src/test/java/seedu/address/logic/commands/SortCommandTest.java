package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.SortCommand.Limit;
import seedu.address.logic.commands.SortCommand.Order;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PostalDataSet;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class SortCommandTest {

    private static final Order ORDER_ASC = new Order("ASC");
    private static final Order ORDER_DES = new Order("DES");
    private static final Optional<Limit> NO_LIMIT = Optional.empty();
    private static final Optional<Limit> SMALL_LIMIT = Optional.ofNullable(new Limit("2"));
    private static final Optional<Limit> BIG_LIMIT = Optional.ofNullable(new Limit("2000"));

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    public void setUp() {
        model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
        expectedModel = new ModelManager(model.getFoodDiary(), new UserPrefs(), new PostalDataSet());
    }

    @Test
    public void execute_sortEmptyFoodDiary_success() {
        // Create empty Food Diary
        model = new ModelManager();

        // Check Sorting in descending order with no limit
        SortCommand command = new SortCommand(ORDER_DES, NO_LIMIT);
        CommandResult result = command.execute(model, new CommandHistory());
        assertEquals(result.getFeedbackToUser(),
                String.format(SortCommand.MESSAGE_SUCCESS_ALL, SortCommand.FEEDBACK_DES));

        // Check Sorting in ascending order with no limit
        command = new SortCommand(ORDER_ASC, NO_LIMIT);
        result = command.execute(model, new CommandHistory());
        assertEquals(result.getFeedbackToUser(),
                String.format(SortCommand.MESSAGE_SUCCESS_ALL, SortCommand.FEEDBACK_ASC));

        // Check Sorting in descending order with limit
        command = new SortCommand(ORDER_DES, SMALL_LIMIT);
        result = command.execute(model, new CommandHistory());
        assertEquals(result.getFeedbackToUser(),
                String.format(SortCommand.MESSAGE_SUCCESS_LIMIT, "the top " + SMALL_LIMIT.get().toInteger(),
                SortCommand.FEEDBACK_DES));
    }

    @Test
    public void execute_sortNonEmptyFoodDiary_success() {
        setUp();

        // Check Sorting in descending order with no limit
        SortCommand command = new SortCommand(ORDER_DES, NO_LIMIT);
        CommandResult resultActual = command.execute(model, new CommandHistory());
        CommandResult resultExpected = command.execute(expectedModel, new CommandHistory());
        assertEquals(resultActual.getFeedbackToUser(), resultExpected.getFeedbackToUser());

        // Check Sorting in ascending order with no limit
        command = new SortCommand(ORDER_ASC, NO_LIMIT);
        resultActual = command.execute(model, new CommandHistory());
        resultExpected = command.execute(expectedModel, new CommandHistory());
        assertEquals(resultActual.getFeedbackToUser(), resultExpected.getFeedbackToUser());

        // Check Sorting in descending order with limit
        command = new SortCommand(ORDER_DES, SMALL_LIMIT);
        resultActual = command.execute(model, new CommandHistory());
        resultExpected = command.execute(expectedModel, new CommandHistory());
        assertEquals(resultActual.getFeedbackToUser(), resultExpected.getFeedbackToUser());
    }

    @Test
    public void equals() {
        SortCommand sortAscNoLimit = new SortCommand(ORDER_ASC, NO_LIMIT);
        SortCommand sortDesNoLimit = new SortCommand(ORDER_DES, NO_LIMIT);
        SortCommand sortLimitOutOfRange = new SortCommand(ORDER_ASC, BIG_LIMIT);
        SortCommand sortLimitWithinRange = new SortCommand(ORDER_DES, SMALL_LIMIT);

        // same object -> returns true
        assertTrue(sortAscNoLimit.equals(sortAscNoLimit));

        // same values -> returns true
        SortCommand sortAscNoLimitCopy = new SortCommand(ORDER_ASC, NO_LIMIT);
        assertTrue(sortAscNoLimit.equals(sortAscNoLimitCopy));

        // different types -> returns false
        assertFalse(sortDesNoLimit.equals(1));

        // null -> returns false
        assertFalse(sortDesNoLimit.equals(null));

        // different Limits -> returns false
        assertFalse(sortLimitOutOfRange.equals(sortLimitWithinRange));

        // different Orders -> returns false
        assertFalse(sortAscNoLimit.equals(sortDesNoLimit));
    }

}
