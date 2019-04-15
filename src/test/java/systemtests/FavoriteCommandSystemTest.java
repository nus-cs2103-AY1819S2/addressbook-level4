package systemtests;

import org.junit.Test;
import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.model.Model;

import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;

public class FavoriteCommandSystemTest extends AddressBookSystemTest{

    @Test
    public void favorite() {

        /* Case: multiple people with same last name -> rejected, lists all possibilities */
        String command = "   " + FavoriteCommand.COMMAND_WORD + "  Meier";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel, "No person exists with this name");
        assertSelectedCardUnchanged();

        /* Case: adds valid to favorites list -> accepted */
        command = "   " + FavoriteCommand.COMMAND_WORD + "  Benson Meier";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel, "New person added");
        assertSelectedCardUnchanged();

        /* Case: adds a person already in favorites list -> rejected */
        command = FavoriteCommand.COMMAND_WORD + "  Benson Meier";
        //expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel, "This person already exists in the favorite list");
        assertSelectedCardUnchanged();

    }

    /**
     * Executes {@code command} and verifies that the result display
     * box displays the valid string message and that the model has bee
     * appropriately updated.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedMessage) {

        executeCommand(command);
        assertApplicationDisplaysAsExpected(command, expectedMessage, expectedModel);
    }
}
