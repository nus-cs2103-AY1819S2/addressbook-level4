package seedu.address.logic.commands;

import static seedu.address.logic.commands.DescriptionCommand.EXCEPTION_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with Model)and unit tests for DescriptionCommand.
 */
public class DescriptionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandFailure(new DescriptionCommand(), model, new CommandHistory(), EXCEPTION_MESSAGE);
    }
}
