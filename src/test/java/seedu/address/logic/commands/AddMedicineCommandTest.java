package seedu.address.logic.commands;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.medicine.Medicine;

public class AddMedicineCommandTest {

    private Model modelManager;
    private CommandHistory history;

    private String medicineName = "panaddol";
    private int quantity = 50;
    private String[] path = new String[] {"root"};
    private String[] invalidPath = new String[] {"root", "TCM"};
    private BigDecimal price = BigDecimal.valueOf(37.9);

    @Before
    public void init() {
        modelManager = new ModelManager();
        history = new CommandHistory();
    }
    @Test
    public void addValidMedicine() {
        try {
            CommandResult commandResult =
                    new AddMedicineCommand(path, medicineName, quantity, price).execute(modelManager, history);
            Medicine medicine = new Medicine(medicineName, quantity);
            medicine.setPrice(price);
            Assert.assertEquals(String.format(AddMedicineCommand.MESSAGE_SUCCESS_NEW_MED,
                        medicineName, quantity, price.toString()),
                    commandResult.getFeedbackToUser());
        } catch (CommandException ex) {
            Assert.fail();
        }
    }

    @Test
    public void addValidMedicineWithInvalidPath_throwCommandException() {
        try {
            CommandResult commandResult =
                    new AddMedicineCommand(invalidPath, medicineName, quantity, price).execute(modelManager, history);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals("No Directory found at given path", ex.getMessage());
        }
    }

    @Test
    public void addDuplicateMedicine_throwCommandException() {
        addValidMedicine();
        try {
            CommandResult commandResult =
                    new AddMedicineCommand(path, medicineName, quantity, price).execute(modelManager, history);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals("Medicine with same name has already existed", ex.getMessage());
        }
    }

    @Test
    public void addDuplicateMedicineWoQuanity_success() {
        addValidMedicine();
        try {
            modelManager.addDirectory("test", new String[] {"root"});
            CommandResult commandResult =
                    new AddMedicineCommand(new String[] {"root", "test"}, medicineName, price)
                            .execute(modelManager, history);
            Assert.assertEquals("Existing Medicine: " + medicineName
                            + ", Quantity: 50, Price: 37.9, added to root\\test\n",
                    commandResult.getFeedbackToUser());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void addDuplicateMedicineWoQuanityWithDiffPrice_successWithWarning() {
        addValidMedicine();
        try {
            modelManager.addDirectory("test", new String[] {"root"});
            CommandResult commandResult =
                    new AddMedicineCommand(new String[] {"root", "test"}, medicineName, BigDecimal.valueOf(22))
                            .execute(modelManager, history);
            Assert.assertEquals("Existing Medicine: " + medicineName
                            + ", Quantity: 50, Price: 37.9, added to root\\test\n"
                            + AddMedicineCommand.MESSAGE_SETPRICE_IGNORED,
                    commandResult.getFeedbackToUser());
        } catch (Exception ex) {
            Assert.fail();
        }
    }
}
