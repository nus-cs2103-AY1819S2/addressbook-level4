package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRestaurantAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PostalDataSet;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Postal;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListUnvisitedCommandTest {

    private static final String INVALID_POSTAL_CODE = "000000";
    private static final String VALID_POSTAL_CODE = "267951";
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
        expectedModel = new ModelManager(model.getFoodDiary(), new UserPrefs(), new PostalDataSet());
        expectedModel.updateFilteredRestaurantList(Model.PREDICATE_SHOW_UNVISITED_RESTAURANTS);

    }

    @Test
    public void execute_listIsFiltered_invalidPostal() {
        showRestaurantAtIndex(model, INDEX_FIRST_RESTAURANT);
        assertCommandSuccess(new ListUnvisitedCommand(new Postal(INVALID_POSTAL_CODE)), model, commandHistory,
                ListUnvisitedCommand.MESSAGE_INVALID_POSTAL_CODE, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_missingPostalData() {
        model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), null);
        expectedModel = new ModelManager(model.getFoodDiary(), new UserPrefs(), null);
        showRestaurantAtIndex(model, INDEX_FIRST_RESTAURANT);
        assertCommandSuccess(new ListUnvisitedCommand(new Postal(VALID_POSTAL_CODE)), model, commandHistory,
                ListUnvisitedCommand.MESSAGE_INVALID_POSTAL_DATA, expectedModel);
    }


}
