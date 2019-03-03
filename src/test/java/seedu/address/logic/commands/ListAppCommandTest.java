package seedu.address.logic.commands;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ListAppCommandTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeListApp() {
        CommandResult result = new ListAppCommand().execute(model, commandHistory);
        StringBuilder expected = new StringBuilder();
        expected.append(ListAppCommand.MESSAGE_SUCCESS)
                .append(model.listApp());

        Assert.assertTrue(result.getFeedbackToUser().equals(expected.toString()));
    }

    @Test
    public void equals() {
        ListAppCommand listApp = new ListAppCommand();

        // same object -> returns true
        Assert.assertTrue(listApp.equals(listApp));

        // different types -> returns false
        Assert.assertFalse(listApp.equals(1));

        // null -> returns false
        Assert.assertFalse(listApp.equals(null));
    }
}
