package seedu.address.logic;

import java.io.IOException;
//import java.nio.file.Path;
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
//import seedu.address.model.person.Person;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.request.Request;
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
    private final AddressBookParser addressBookParser;
    private boolean requestBookModified;
    private boolean healthWorkerBookModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.history = new CommandHistory();
        this.addressBookParser = new AddressBookParser();

        // Set addressBookModified to true whenever the models' address book is modified.
        model.getRequestBook().addListener(observable -> requestBookModified = true);
        model.getHealthWorkerBook().addListener(observable -> healthWorkerBookModified = true);

    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        requestBookModified = false;
        healthWorkerBookModified = false;

        CommandResult commandResult;
        try {
            Command command = addressBookParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (requestBookModified) {
            logger.info("Request book modified, saving to file.");
            try {
                storage.saveRequestBook(model.getRequestBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        if (healthWorkerBookModified) {
            logger.info("Healthworker book modified, saving to file.");
            try {
                storage.saveHealthWorkerBook(model.getHealthWorkerBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }



    @Override
    public ObservableList<HealthWorker> getFilteredHealthWorkerList() {
        return model.getFilteredHealthWorkerList(); }

    @Override
    public ObservableList<Request> getFilteredRequestList() {
        return model.getFilteredRequestList(); }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
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
    public ReadOnlyProperty<HealthWorker> selectedHealthWorkerProperty() {
        return model.selectedHealthWorkerProperty(); }

    @Override
    public ReadOnlyProperty<Request> selectedRequestProperty() {
        return model.selectedRequestProperty();
    }
    @Override
    public void setSelectedHealthWorker(HealthWorker worker) {
        model.setSelectedHealthWorker(worker);
    }

    @Override
    public void setSelectedRequest(Request request) {
        model.setSelectedRequest(request);
    }
}
