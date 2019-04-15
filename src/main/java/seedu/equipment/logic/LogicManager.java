package seedu.equipment.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.equipment.commons.core.GuiSettings;
import seedu.equipment.commons.core.LogsCenter;
import seedu.equipment.logic.commands.Command;
import seedu.equipment.logic.commands.CommandResult;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.logic.parser.EquipmentManagerParser;
import seedu.equipment.logic.parser.exceptions.ParseException;
import seedu.equipment.model.Model;
import seedu.equipment.model.ReadOnlyEquipmentManager;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final EquipmentManagerParser equipmentManagerParser;
    private boolean equipmentManagerModified;

    private Name name;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        equipmentManagerParser = new EquipmentManagerParser();

        // Set equipmentManagerModified to true whenever the models' equipment is modified.
        model.getEquipmentManager().addListener(observable -> equipmentManagerModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        equipmentManagerModified = false;

        CommandResult commandResult;
        try {
            Command command = equipmentManagerParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (equipmentManagerModified) {
            logger.info("Equipment Manager modified, saving to file.");
            try {
                storage.saveEquipmentManager(model.getEquipmentManager());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyEquipmentManager getEquipmentManager() {
        return model.getEquipmentManager();
    }

    @Override
    public ObservableList<Equipment> getFilteredEquipment() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<WorkList> getFilteredWorkListList() {
        return model.getFilteredWorkListList();
    }

    @Override
    public ObservableList<Name> getFilteredClientList() {
        return model.getFilteredClientList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getEquipmentManagerFilePath() {
        return model.getEquipmentManagerFilePath();
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
    public ReadOnlyProperty<Equipment> selectedEquipmentProperty() {
        return model.selectedEquipmentProperty();
    }

    @Override
    public ReadOnlyProperty<Name> selectedClientProperty() {
        return model.selectedClientProperty();
    }

    @Override
    public ReadOnlyProperty<WorkList> selectedWorkListProperty() {
        return model.selectedWorkListProperty();
    }

    @Override
    public void setSelectedPerson(Equipment equipment) {
        model.setSelectedEquipment(equipment);
    }

    @Override
    public void setSelectedClient(Name name) {
        this.name = name;
        model.setSelectedClient(name);
    }

    @Override
    public Name getSelectedClient() {
        return name;
    }



    @Override
    public void setSelectedWorkList(WorkList workList) {
        model.setSelectedWorkList(workList);
    }
}
