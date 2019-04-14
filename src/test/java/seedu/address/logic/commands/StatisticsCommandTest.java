/*
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class StatisticsCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();

        String expectedCommandResult = "Current Statistics:" + "\n"
                + model.getFavApparel() + "\n"
                + model.getFavColor() + "\n"
                + "Total Apparel in Wardrobe : " + model.getFilteredApparelList().size() + "\n"
                + model.getTotalColor() + "\n"
                + model.getLeastFavApparel() + "\n"
                + model.getCleanOrDirty();

        assertCommandSuccess(new StatisticsCommand(), model, commandHistory,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.setAddressBook(getTypicalAddressBook());
        expectedModel.commitAddressBook();

        String command1 = model.getFavApparel();
        String command2 = model.getFavColor();
        String command3 = "Total Apparel in Wardrobe : " + model.getFilteredApparelList().size();
        String command4 = model.getTotalColor();
        String command5 = model.getLeastFavApparel();
        String command6 = model.getCleanOrDirty();

        String expectedMessage = String.format(StatisticsCommand.MESSAGE_SUCCESS,
                String.join("\n", command1, command2, command3, command4, command5, command6));

        assertCommandSuccess(new StatisticsCommand(), model, commandHistory,
                expectedMessage, expectedModel);
    }

}
*/
