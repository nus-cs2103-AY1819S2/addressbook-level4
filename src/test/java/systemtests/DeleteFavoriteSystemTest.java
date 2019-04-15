package systemtests;

import org.junit.Test;
import seedu.address.logic.commands.DelFavoriteCommand;
import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.model.Model;

public class DeleteFavoriteSystemTest extends  AddressBookSystemTest{

    @Test
    public void delFavorite() {

        /* Case: delete a person not in the favorites list -> rejected */
        String command = "   " + DelFavoriteCommand.COMMAND_WORD + " Benson Meier";
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel, "This person doesn't exist in the favorite list");
        assertSelectedCardUnchanged();

        /* Case: add a favorite and then delete them -> accepted */
        executeCommand(FavoriteCommand.COMMAND_WORD + " Benson Meier");
        assertCommandSuccess(command, expectedModel, "Person deleted");
        assertSelectedCardUnchanged();

        /* Case: delete a favorite twice -> rejected */
        assertCommandSuccess(command, expectedModel, "This person doesn't exist in the favorite list");
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
