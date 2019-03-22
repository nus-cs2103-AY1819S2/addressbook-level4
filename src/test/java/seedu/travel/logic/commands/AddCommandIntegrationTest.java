package seedu.travel.logic.commands;

import static seedu.travel.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.travel.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

import org.junit.Before;
import org.junit.Test;

import seedu.travel.logic.CommandHistory;
import seedu.travel.model.Model;
import seedu.travel.model.ModelManager;
import seedu.travel.model.UserPrefs;
import seedu.travel.model.place.Place;
import seedu.travel.testutil.PlaceBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    }

    @Test
    public void execute_newPlace_success() {
        Place validPlace = new PlaceBuilder().build();

        Model expectedModel = new ModelManager(model.getTravelBuddy(), new UserPrefs());
        expectedModel.addPlace(validPlace);
        expectedModel.commitTravelBuddy();

        assertCommandSuccess(new AddCommand(validPlace), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validPlace), expectedModel);
    }

    @Test
    public void execute_duplicatePlace_throwsCommandException() {
        Place placeInList = model.getTravelBuddy().getPlaceList().get(0);
        assertCommandFailure(new AddCommand(placeInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PLACE);
    }

}
