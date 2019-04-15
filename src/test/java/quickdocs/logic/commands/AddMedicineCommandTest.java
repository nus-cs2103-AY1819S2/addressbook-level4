package quickdocs.logic.commands;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;
import quickdocs.model.medicine.Medicine;
import quickdocs.model.medicine.MedicineManager;

public class AddMedicineCommandTest {

    private Model modelManager;
    private CommandHistory history;

    private String medicineName = "panaddol";
    private Optional<Integer> presentQuantity = Optional.of(50);
    private String[] path = new String[] {"root"};
    private String[] invalidPath = new String[] {"root", "TCM"};
    private Optional<BigDecimal> presentPrice = Optional.of(BigDecimal.valueOf(37.9));

    @Before
    public void init() {
        modelManager = new ModelManager();
        history = new CommandHistory();
    }

    @Test
    public void addValidMedicine() {
        try {
            CommandResult commandResult =
                    new AddMedicineCommand(path, medicineName, presentQuantity, presentPrice)
                            .execute(modelManager, history);
            Medicine medicine = new Medicine(medicineName, presentQuantity.get());
            medicine.setPrice(presentPrice.get());
            Assert.assertEquals(String.format(AddMedicineCommand.MESSAGE_SUCCESS_NEW_MED,
                        medicineName, presentQuantity.get(), presentPrice.get().toString()),
                    commandResult.getFeedbackToUser());
        } catch (CommandException ex) {
            Assert.fail();
        }
    }

    @Test
    public void addValidMedicineWithInvalidPath_throwCommandException() {
        try {
            CommandResult commandResult =
                    new AddMedicineCommand(invalidPath, medicineName, presentQuantity, presentPrice)
                            .execute(modelManager, history);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals(MedicineManager.ERROR_MESSAGE_NO_DIRECTORY_FOUND, ex.getMessage());
        }
    }

    @Test
    public void addDuplicateMedicine_throwCommandException() {
        addValidMedicine();
        try {
            CommandResult commandResult =
                    new AddMedicineCommand(path, medicineName, presentQuantity, presentPrice)
                            .execute(modelManager, history);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals(MedicineManager.ERROR_MESSAGE_MEDICINE_WITH_SAME_NAME_EXISTS_IN_LIST, ex.getMessage());
        }
    }

    @Test
    public void addExistingMedicine_medicineNotFound_throwCommandException() {
        try {
            modelManager.addDirectory("test", new String[] {"root"});
            CommandResult commandResult =
                    new AddMedicineCommand(new String[] {"root", "test"}, medicineName,
                            Optional.empty(), Optional.empty()).execute(modelManager, history);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals(String.format(MedicineManager.ERROR_MESSAGE_NO_EXISTING_MED_FOUND, medicineName),
                    ex.getMessage());
        }
    }

    @Test
    public void addNewMedicineWoQuanity_throwCommandException() {
        addValidMedicine();
        try {
            modelManager.addDirectory("test", new String[] {"root"});
            CommandResult commandResult =
                    new AddMedicineCommand(new String[] {"root", "test"}, medicineName, Optional.empty(), presentPrice)
                            .execute(modelManager, history);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals(AddMedicineCommand.ERRORMESSAGE_INSUFFICIENTINFO_NEWMEDICINE, ex.getMessage());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void addDuplicateMedicineWithQuantityAndPrice_throwCommandException() {
        addValidMedicine();
        try {
            modelManager.addDirectory("test", new String[] {"root"});
            CommandResult commandResult =
                    new AddMedicineCommand(new String[] {"root", "test"}, medicineName, presentQuantity,
                            presentPrice).execute(modelManager, history);
            Assert.fail();
        } catch (CommandException ex) {
            Assert.assertEquals(MedicineManager.ERROR_MESSAGE_MEDICINE_WITH_SAME_NAME_EXISTS_IN_LIST, ex.getMessage());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void addValidExistingMedicine_success() {
        addValidMedicine();
        try {
            modelManager.addDirectory("test", new String[] {"root"});
            CommandResult commandResult =
                    new AddMedicineCommand(new String[] {"root", "test"}, medicineName, Optional.empty(),
                            Optional.empty()).execute(modelManager, history);
            Medicine medicine = new Medicine(medicineName, presentQuantity.get());
            medicine.setPrice(presentPrice.get());
            Assert.assertEquals(String.format(AddMedicineCommand.MESSAGE_SUCCESS_EXISTING_MED,
                    medicine.toString(), "root\\test"), commandResult.getFeedbackToUser());
        } catch (Exception ex) {
            Assert.fail();
        }
    }
}
