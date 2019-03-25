package seedu.equipment.logic.commands;

import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandSuccessWithChanges;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalAddressBook;

import org.junit.Test;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;
import seedu.equipment.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListEquipmentCommand.
 */
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_sortListByAddress() {
        String addressSortParameter = "address";
        SortCommand sortCommand = new SortCommand(addressSortParameter);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, addressSortParameter);
        assertCommandSuccessWithChanges(sortCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_sortListByEmail() {
        String emailSortParameter = "email";
        SortCommand sortCommand = new SortCommand(emailSortParameter);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, emailSortParameter);
        assertCommandSuccessWithChanges(sortCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_sortListByPhone() {
        String phoneSortParameter = "phone";
        SortCommand sortCommand = new SortCommand(phoneSortParameter);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, phoneSortParameter);
        assertCommandSuccessWithChanges(sortCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noSortParameter_success() {
        String defaultSortParameter = "";
        SortCommand sortCommand = new SortCommand(defaultSortParameter);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, SortCommand.DEFAULT_SORT_PARAMETER);
        assertCommandSuccessWithChanges(sortCommand, model, commandHistory, expectedMessage);
    }
}
