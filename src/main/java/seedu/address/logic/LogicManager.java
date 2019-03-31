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
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyArchiveBook;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    public static final String INVALID_LIST_SHOWN_MAIN = "Invalid list displayed. Switch to main person list first!";
    public static final String INVALID_LIST_SHOWN_ARCHIVE = "Invalid list displayed. Switch to archive list first!";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private boolean addressBookModified;
    private boolean archiveBookModified;
    private boolean archiveShown;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        this.archiveShown = false;

        // Set addressBookModified to true whenever the models' address book is modified.
        model.getAddressBook().addListener(observable -> addressBookModified = true);

        // Set archiveBookModified to true whenever the models' archive book is modified.
        model.getArchiveBook().addListener(observable -> archiveBookModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        addressBookModified = false;
        archiveBookModified = false;

        CommandResult commandResult;
        try {
            Command command = addressBookParser.parseCommand(commandText);
            checkListShown(command);
            commandResult = command.execute(model, history);
            setArchiveShown(commandResult);
        } finally {
            history.add(commandText);
        }

        if (addressBookModified) {
            logger.info("Address book modified, saving to file.");
            try {
                storage.saveAddressBook(model.getAddressBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        if (archiveBookModified) {
            logger.info("Archive book modified, saving to file.");
            try {
                storage.saveArchiveBook(model.getArchiveBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ReadOnlyArchiveBook getArchiveBook() {
        return model.getArchiveBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Person> getFilteredArchivedPersonList() {
        return model.getFilteredArchivedPersonList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public Path getArchiveBookFilePath() {
        return model.getArchiveBookFilePath();
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
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return model.selectedPersonProperty();
    }

    @Override
    public void setSelectedPerson(Person person) {
        model.setSelectedPerson(person);
    }

    @Override
    public void checkListShown(Command command) throws CommandException {
        if ((command.requiresMainList()) && archiveShown) {
            throw new CommandException(INVALID_LIST_SHOWN_MAIN);
        }
        if ((command.requiresArchiveList()) && !archiveShown) {
            throw new CommandException(INVALID_LIST_SHOWN_ARCHIVE);
        }
    }

    @Override
    public void setArchiveShown(CommandResult commandResult) {
        this.archiveShown = commandResult.getArchiveShown();
    }
}
