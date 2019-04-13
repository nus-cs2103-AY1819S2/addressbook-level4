package quickdocs.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import quickdocs.commons.core.GuiSettings;
import quickdocs.commons.core.LogsCenter;
import quickdocs.logic.commands.Command;
import quickdocs.logic.commands.CommandResult;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.logic.parser.QuickDocsParser;
import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.model.Model;
import quickdocs.model.reminder.Reminder;
import quickdocs.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final QuickDocsParser quickDocsParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        quickDocsParser = new QuickDocsParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        try {
            Command command = quickDocsParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (model.getQuickDocs().isModified()) {
            logger.info("QuickDocs modified, saving to file.");
            try {
                storage.saveQuickDocs(model.getQuickDocs());
                model.getQuickDocs().indicateModification(false);
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

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
    public ObservableList<Reminder> getFilteredReminderList() {
        return model.getFilteredReminderList();
    }

    @Override
    public ReadOnlyProperty<Reminder> selectedReminderProperty() {
        return model.selectedReminderProperty();
    }

    @Override
    public void setSelectedReminder(Reminder reminder) {
        model.setSelectedReminder(reminder);
    }

    @Override
    public ArrayList<String> getDirectorySuggestions (String rawArgs) {
        String rawPath = quickDocsParser.getArgument(rawArgs);
        return model.getDirectorySuggestions(rawPath);
    }

    @Override
    public ArrayList<String> getMedicineSuggestions (String rawArgs) {
        String rawPath = quickDocsParser.getArgument(rawArgs);
        return model.getMedicineSuggestions(rawPath);
    }

    @Override
    public boolean isDirectoryFormat(String rawArgs) {
        return quickDocsParser.isDirectoryFormat(rawArgs);
    }

    @Override
    public boolean isMedicineAllowed(String rawArgs) {
        return quickDocsParser.isMedicineAllowed(rawArgs);
    }
}
