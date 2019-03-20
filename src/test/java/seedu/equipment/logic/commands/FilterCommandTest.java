package seedu.equipment.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.commons.core.Messages.MESSAGE_EQUIPMENTS_LISTED_OVERVIEW;
import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.AYERRAJAHCC;
import static seedu.equipment.testutil.TypicalEquipments.BUKITGCC;
import static seedu.equipment.testutil.TypicalEquipments.HWIYOHCC;
import static seedu.equipment.testutil.TypicalEquipments.JURONGREENCC;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.FilterCommand;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;
import seedu.equipment.model.UserPrefs;
import seedu.equipment.model.equipment.EquipmentContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        EquipmentContainsKeywordsPredicate firstPredicate =
                new EquipmentContainsKeywordsPredicate(Collections.singletonList("first"));
        EquipmentContainsKeywordsPredicate secondPredicate =
                new EquipmentContainsKeywordsPredicate(Collections.singletonList("second"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different equipment -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }


    @Test

    public void execute_zeroKeywords_noEquipmentFound() {
        String expectedMessage = String.format(MESSAGE_EQUIPMENTS_LISTED_OVERVIEW, 0);
        EquipmentContainsKeywordsPredicate predicate = preparePredicate(" ");
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(new FilterCommand(predicate), model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multipleEquipmentsFound() {
        String expectedMessage = String.format(MESSAGE_EQUIPMENTS_LISTED_OVERVIEW, 5);
        EquipmentContainsKeywordsPredicate predicate = preparePredicate("west urgent");
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(new FilterCommand(predicate), model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ANCHORVALECC, HWIYOHCC, AYERRAJAHCC, BUKITGCC, JURONGREENCC), model.getFilteredPersonList());
    }
    private EquipmentContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EquipmentContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
