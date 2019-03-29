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

public class PurchaseMedicineViaPathCommandTest {

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void init() {
        model = new ModelManager();
        commandHistory = new CommandHistory();
        model.addDirectory("TCM", new String[] {"root"});
        model.addMedicine("Panaddol", 50, new String[] {"root", "TCM"}, BigDecimal.valueOf(34));
    }

    @Test
    public void purchaseValidMedicine() {
        try {
            CommandResult commandResult =
                    new PurchaseMedicineViaPathCommand(
                            new String[] {"root", "TCM", "Panaddol"}, 50, BigDecimal.valueOf(500))
                    .execute(model, commandHistory);
            Assert.assertEquals(PurchaseMedicineCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        } catch (CommandException ex) {
            Assert.fail();
        }
    }

    @Test
    public void invalidMedicinePath_throwCommandException() {
        try {
            CommandResult commandResult =
                    new PurchaseMedicineViaPathCommand(
                            new String[] {"root", "Panaddol"}, 50, BigDecimal.valueOf(500))
                    .execute(model, commandHistory);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals(MedicineManager.ERROR_MESSAGE_NO_MEDICINE_FOUND_BY_PATH, ex.getMessage());
        }
    }

    @Test
    public void invalidChangeAmount_throwCommandException() {
        try {
            CommandResult commandResult =
                    new PurchaseMedicineViaPathCommand(
                            new String[] {"root", "TCM", "Panaddol"}, -50, BigDecimal.valueOf(500))
                    .execute(model, commandHistory);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals("Change amount must be positive", ex.getMessage());
        }
    }
}
