package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import org.junit.Test;

import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.commons.core.InformationPanelSettings.SortDirection;
import seedu.address.commons.core.InformationPanelSettings.SortProperty;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Inventory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {
    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_success() {
        SortProperty sortProperty = SortProperty.EXPIRY;
        SortDirection sortDirection = SortDirection.DESCENDING;
        SortCommand sortCommand = new SortCommand(sortProperty, sortDirection);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        InformationPanelSettings informationPanelSettings = new InformationPanelSettings(sortProperty, sortDirection);
        expectedModel.setInformationPanelSettings(informationPanelSettings);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, informationPanelSettings);
        assertCommandSuccess(sortCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        SortCommand sortCommand = new SortCommand(SortProperty.QUANTITY, SortDirection.DESCENDING);

        // same object -> returns true
        assertTrue(sortCommand.equals(sortCommand));

        // same values -> returns true
        SortCommand sortCommandCopy = new SortCommand(SortProperty.QUANTITY, SortDirection.DESCENDING);
        assertTrue(sortCommand.equals(sortCommandCopy));

        // different types -> returns false
        assertFalse(sortCommand.equals(1));

        // null -> returns false
        assertFalse(sortCommand.equals(null));

        // different medicine -> returns false
        SortCommand otherSortCommand = new SortCommand(SortProperty.EXPIRY, SortDirection.DESCENDING);
        assertFalse(sortCommand.equals(otherSortCommand));
    }
}
