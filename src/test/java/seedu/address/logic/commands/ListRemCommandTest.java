package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalReminders.getTypicalRemindersQuickDocs;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.QuickDocs;
import seedu.address.model.UserPrefs;

public class ListRemCommandTest {
    private QuickDocs quickDocs = getTypicalRemindersQuickDocs();
    private Model model = new ModelManager(new AddressBook(), quickDocs, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeListRem() {
        CommandResult result = new ListRemCommand().execute(model, commandHistory);
        StringBuilder expected = new StringBuilder();
        expected.append(ListRemCommand.MESSAGE_SUCCESS)
                .append(model.listRem());

        Assert.assertTrue(result.getFeedbackToUser().equals(expected.toString()));
    }

    @Test
    public void equals() {
        ListRemCommand listRem = new ListRemCommand();

        // same object -> returns true
        Assert.assertTrue(listRem.equals(listRem));

        // different types -> returns false
        Assert.assertFalse(listRem.equals(1));

        // null -> returns false
        Assert.assertFalse(listRem.equals(null));
    }
}
