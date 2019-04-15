package seedu.equipment.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.AYERRAJAHCC;
import static seedu.equipment.testutil.TypicalEquipments.BUKITGCC;
import static seedu.equipment.testutil.TypicalEquipments.CHENGSANCC;
import static seedu.equipment.testutil.TypicalEquipments.HWIYOHCC;
import static seedu.equipment.testutil.TypicalEquipments.JURONGREENCC;
import static seedu.equipment.testutil.TypicalEquipments.TECKGHEECC;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalEquipmentManager;

import java.util.Arrays;

import org.junit.Test;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.parser.DateComparator;
import seedu.equipment.logic.parser.NameComparator;
import seedu.equipment.logic.parser.PhoneComparator;

import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;
import seedu.equipment.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListEquipmentCommand.
 */
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalEquipmentManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void execute_sortListByName() {
        Model expectedModel = new ModelManager(new EquipmentManager(model.getEquipmentManager()), new UserPrefs());
        expectedModel.sortFilteredEquipmentList(new NameComparator());
        expectedModel.commitEquipmentManager();
        assertCommandSuccess(new SortCommand(new NameComparator()), model, commandHistory,
                SortCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(Arrays.asList(ANCHORVALECC, AYERRAJAHCC, BUKITGCC,
                CHENGSANCC, HWIYOHCC, JURONGREENCC, TECKGHEECC), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortListByDate() {
        Model expectedModel = new ModelManager(new EquipmentManager(model.getEquipmentManager()), new UserPrefs());
        expectedModel.sortFilteredEquipmentList(new DateComparator());
        expectedModel.commitEquipmentManager();
        assertCommandSuccess(new SortCommand(new DateComparator()), model, commandHistory,
                SortCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(Arrays.asList(ANCHORVALECC, HWIYOHCC, JURONGREENCC, BUKITGCC,
                CHENGSANCC, TECKGHEECC, AYERRAJAHCC), model.getFilteredPersonList());
    }
    @Test
    public void execute_sortListByPhone() {
        Model expectedModel = new ModelManager(new EquipmentManager(model.getEquipmentManager()), new UserPrefs());
        expectedModel.sortFilteredEquipmentList(new PhoneComparator());
        expectedModel.commitEquipmentManager();
        assertCommandSuccess(new SortCommand(new PhoneComparator()), model, commandHistory,
                SortCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(Arrays.asList(TECKGHEECC, CHENGSANCC, HWIYOHCC, ANCHORVALECC, BUKITGCC, AYERRAJAHCC, JURONGREENCC),
                model.getFilteredPersonList());
    }
}
