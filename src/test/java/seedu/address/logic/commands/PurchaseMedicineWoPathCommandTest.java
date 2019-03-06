package seedu.address.logic.commands;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class PurchaseMedicineWoPathCommandTest {

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void init() {
        this.model = new ModelManager();
        this.commandHistory = new CommandHistory();
        model.addDirectory("TCM", new String[] {"root"});
        model.addMedicine("Pannadol", new String[] {"root", "TCM"});
    }

    @Test
    public void purchaseValidMedicine() {
        try {
            CommandResult commandResult =
                    new PurchaseMedicineWoPathCommand("Pannadol", 50,
                            BigDecimal.valueOf(500)).execute(model, commandHistory);
            Assert.assertEquals("Purchase successful.", commandResult.getFeedbackToUser());
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
            Assert.assertEquals("No such medicine found.", ex.getMessage());
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
