package seedu.address.logic.commands;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.medicine.MedicineManager;

public class PurchaseMedicineWoPathCommandTest {

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void init() {
        this.model = new ModelManager();
        this.commandHistory = new CommandHistory();
        model.addDirectory("TCM", new String[] {"root"});
        model.addMedicine("Pannadol", new String[] {"root", "TCM"}, BigDecimal.valueOf(233));
    }

    @Test
    public void purchaseValidMedicine() {
        try {
            CommandResult commandResult =
                    new PurchaseMedicineWoPathCommand("Pannadol", 50,
                            BigDecimal.valueOf(500)).execute(model, commandHistory);
            Assert.assertEquals(PurchaseMedicineCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void purchaseNonExistingMedicine_throwCommandException() {
        try {
            CommandResult commandResult =
                    new PurchaseMedicineWoPathCommand("Pannadoll", 50,
                            BigDecimal.valueOf(500)).execute(model, commandHistory);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals(MedicineManager.ERROR_MESSAGE_NO_MEDICINE_FOUND_BY_NAME, ex.getMessage());
        }
    }

    @Test
    public void purchaseNegativeQuantity_throwCommandException() {
        try {
            CommandResult commandResult =
                    new PurchaseMedicineWoPathCommand("Pannadol", -10,
                            BigDecimal.valueOf(500)).execute(model, commandHistory);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals("Change amount must be positive", ex.getMessage());
        }
    }
}
