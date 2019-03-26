package seedu.equipment.logic.commands;

import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;
import seedu.equipment.model.UserPrefs;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.testutil.EquipmentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddEquipmentCommand}.
 */
public class AddEquipmentCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Equipment validEquipment = new EquipmentBuilder().build();

        Model expectedModel = new ModelManager(model.getEquipmentManager(), new UserPrefs());
        expectedModel.addEquipment(validEquipment);
        expectedModel.commitEquipmentManager();

        assertCommandSuccess(new AddEquipmentCommand(validEquipment), model, commandHistory,
                String.format(AddEquipmentCommand.MESSAGE_SUCCESS, validEquipment), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Equipment equipmentInList = model.getEquipmentManager().getPersonList().get(0);
        assertCommandFailure(new AddEquipmentCommand(equipmentInList), model, commandHistory,
                AddEquipmentCommand.MESSAGE_DUPLICATE_EQUIPMENT);
    }

}
