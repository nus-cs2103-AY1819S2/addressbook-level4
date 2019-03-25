package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.InventoryParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.medicine.Medicine;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final InventoryParser inventoryParser;
    private boolean inventoryModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        inventoryParser = new InventoryParser();

        // Set inventoryModified to true whenever the models' inventory is modified.
        model.getInventory().addListener(observable -> inventoryModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        inventoryModified = false;

        CommandResult commandResult;
        try {
            Command command = inventoryParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (inventoryModified) {
            logger.info("Inventory modified, saving to file.");
            try {
                storage.saveInventory(model.getInventory());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyInventory getInventory() {
        return model.getInventory();
    }

    @Override
    public WarningPanelPredicateAccessor getWarningPanelPredicateAccessor() {
        return model.getWarningPanelPredicateAccessor();
    }

    @Override
    public ObservableList<Medicine> getFilteredMedicineList() {
        return model.getFilteredMedicineList();
    }

    @Override
    public ObservableList<Medicine> getExpiringMedicinesList() {
        return model.getExpiringMedicinesList();
    }

    @Override
    public ObservableList<Medicine> getLowQuantityMedicinesList() {
        return model.getLowQuantityMedicinesList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getInventoryFilePath() {
        return model.getInventoryFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Medicine> selectedMedicineProperty() {
        return model.selectedMedicineProperty();
    }

    @Override
    public void setSelectedMedicine(Medicine medicine) {
        model.setSelectedMedicine(medicine);
    }
}
