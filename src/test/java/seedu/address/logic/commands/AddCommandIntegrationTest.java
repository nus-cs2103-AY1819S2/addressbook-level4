package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicine.Medicine;
import seedu.address.testutil.MedicineBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalInventory(), new UserPrefs());
    }

    @Test
    public void execute_newMedicine_success() {
        Medicine validMedicine = new MedicineBuilder().build();

        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs());
        expectedModel.addMedicine(validMedicine);
        expectedModel.commitInventory();

        assertCommandSuccess(new AddCommand(validMedicine), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validMedicine), expectedModel);
    }

    @Test
    public void execute_duplicateMedicine_throwsCommandException() {
        Medicine medicineInList = model.getInventory().getMedicineList().get(0);
        assertCommandFailure(new AddCommand(medicineInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_MEDICINE);
    }

}
