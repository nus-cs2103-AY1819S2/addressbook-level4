package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.place.Place;
import seedu.address.testutil.PlaceBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPlace_success() {
        Place validPlace = new PlaceBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPlace(validPlace);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validPlace), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validPlace), expectedModel);
    }

    @Test
    public void execute_duplicatePlace_throwsCommandException() {
        Place placeInList = model.getAddressBook().getPlaceList().get(0);
        assertCommandFailure(new AddCommand(placeInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
