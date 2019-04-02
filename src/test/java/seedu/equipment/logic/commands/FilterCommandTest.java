package seedu.equipment.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
import java.util.List;

import org.junit.Test;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;
import seedu.equipment.model.UserPrefs;
import seedu.equipment.model.equipment.Equipment;
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
        List<String> nameKeywords = Arrays.asList("teck whye cc", "jurong cc");
        List<String> addressKeywords = Arrays.asList("600", "jurong");
        List<String> dateKeywords = Arrays.asList("22 may 2019", "31 april 2019");
        List<String> phoneKeywords = Arrays.asList("98765432", "64454223");
        List<String> tagKeywords = Arrays.asList("west", "ongoing");
        List<String> serialNumberKeywords = Arrays.asList("A008866X", "X14H702695");

        EquipmentContainsKeywordsPredicate firstPredicate =
                new EquipmentContainsKeywordsPredicate(nameKeywords, addressKeywords, dateKeywords, phoneKeywords,
                        tagKeywords, serialNumberKeywords);

        nameKeywords = Arrays.asList("teck whye cc", "jurong cc", "boon lay cc");
        addressKeywords = Arrays.asList("600", "jurong");
        dateKeywords = Arrays.asList("22 may 2019", "31 april 2019");
        phoneKeywords = Arrays.asList("98765432", "64454223");
        tagKeywords = Arrays.asList("west", "ongoing");
        serialNumberKeywords = Arrays.asList("A008866X", "X14H702695");

        EquipmentContainsKeywordsPredicate secondPredicate =
                new EquipmentContainsKeywordsPredicate(nameKeywords, addressKeywords, dateKeywords, phoneKeywords,
                        tagKeywords, serialNumberKeywords);

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertEquals(filterFirstCommand, filterFirstCommand);

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertEquals(filterFirstCommand, filterFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(filterFirstCommand, 1);

        // null -> returns false
        assertNotEquals(filterFirstCommand, null);

        // different equipment -> returns false
        assertNotEquals(filterFirstCommand, filterSecondCommand);
    }


    @Test

    public void execute_zeroKeywords_noEquipmentFound() {
        String expectedMessage = String.format(MESSAGE_EQUIPMENTS_LISTED_OVERVIEW, 0);
        EquipmentContainsKeywordsPredicate predicate = new EquipmentContainsKeywordsPredicate(
                Arrays.asList("anchorvalecc", " "), Arrays.asList(), Arrays.asList(), Arrays.asList(),
                Arrays.asList(), Arrays.asList());
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multipleEquipmentsFound() {
        String expectedMessage = String.format(MESSAGE_EQUIPMENTS_LISTED_OVERVIEW, 5);
        EquipmentContainsKeywordsPredicate predicate = new EquipmentContainsKeywordsPredicate(
                Arrays.asList("Anchorvale", "Ayer", "Bukit", "Hwi", "Jurong"),
                Arrays.asList(), Arrays.asList(), Arrays.asList(), Arrays.asList(), Arrays.asList());
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        List<Equipment> expectedList = Arrays.asList(ANCHORVALECC, HWIYOHCC, AYERRAJAHCC, BUKITGCC, JURONGREENCC);
        assertEquals(expectedList, model.getFilteredPersonList());
    }
}
