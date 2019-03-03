package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.RestOrRantParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.person.Person;
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
    private final RestOrRantParser restOrRantParser;
    private boolean restOrRantModified;
    private Mode mode;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        restOrRantParser = new RestOrRantParser();
        mode = Mode.RESTAURANT_MODE;

        // Set addressBookModified to true whenever the models' address book is modified.
        model.getRestOrRant().addListener(observable -> restOrRantModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        restOrRantModified = false;

        CommandResult commandResult;
        try {
            Command command = restOrRantParser.parseCommand(mode, commandText);
            commandResult = command.execute(mode, model, history);
        } finally {
            history.add(commandText);
        }

        if (restOrRantModified) {
            logger.info("RestOrRant modified, saving to file.");
            try {
                storage.saveMenu(model.getRestOrRant());
                // TODO: add save<each feature> instead of saveRestOrRant
                // storage.saveRestOrRant(model.getRestOrRant());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    // returns
    public Mode getMode() {
        return mode;
    }
    @Override
    public ReadOnlyRestOrRant getRestOrRant() {
        return model.getRestOrRant();
    }
    //    public ReadOnlyRestOrRant getAddressBook() {
    //        return model.getRestOrRant();
    //    }

    @Override
    public ObservableList<MenuItem> getFilteredMenuItemList() {
        return model.getFilteredMenuItemList();
    }
    // TODO: remove
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList(); 
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getMenuFilePath() {
        return model.getMenuFilePath();
    }
    public Path getRestOrRantFilePath() {
        return model.getRestOrRantFilePath();
    } // TODO: remove

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<MenuItem> selectedMenuItemProperty() {
        return model.selectedMenuItemProperty();
    }
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return model.selectedPersonProperty();
    } // TODO: remove

    @Override
    public void setSelectedMenuItem(MenuItem item) {
        model.setSelectedMenuItem(item);
    }
    public void setSelectedPerson(Person person) {
        model.setSelectedPerson(person);
    } // TODO: remove
}
