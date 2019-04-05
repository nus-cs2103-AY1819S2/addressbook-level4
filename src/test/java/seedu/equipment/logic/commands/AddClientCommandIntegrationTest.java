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

public class AddClientCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newClient_success() {
        Equipment validEquipment = new EquipmentBuilder().build();

        Model expectedModel = new ModelManager(model.getEquipmentManager(), new UserPrefs());
        expectedModel.addClient(validEquipment.getName());
        expectedModel.commitEquipmentManager();

        assertCommandSuccess(new AddClientCommand(validEquipment.getName()), model, commandHistory,
                String.format(AddClientCommand.MESSAGE_SUCCESS, validEquipment.getName()), expectedModel);
    }

    @Test
    public void execute_duplicateName_throwsCommandException() {
        Equipment equipmentInList = model.getEquipmentManager().getPersonList().get(0);
        assertCommandFailure(new AddClientCommand(equipmentInList.getName()), model, commandHistory,
                AddClientCommand.MESSAGE_DUPLICATE_CLIENT);
    }
}
