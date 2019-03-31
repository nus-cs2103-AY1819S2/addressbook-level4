package seedu.finance.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.finance.commons.core.GuiSettings;
import seedu.finance.commons.core.LogsCenter;
import seedu.finance.logic.commands.Command;
import seedu.finance.logic.commands.CommandResult;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.logic.parser.FinanceTrackerParser;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.Model;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Record;
import seedu.finance.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final FinanceTrackerParser financeTrackerParser;
    private boolean financeTrackerModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        financeTrackerParser = new FinanceTrackerParser();

        // Set financeTrackerModified to true whenever the models' finance tracker is modified.
        model.getFinanceTracker().addListener(observable -> financeTrackerModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        financeTrackerModified = false;

        CommandResult commandResult;
        try {
            Command command = financeTrackerParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (financeTrackerModified) {
            logger.info("Finance tracker modified, saving to file.");
            try {
                storage.saveFinanceTracker(model.getFinanceTracker());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public Budget getBudget() {
        return model.getBudget();
    }

    @Override
    public ReadOnlyFinanceTracker getFinanceTracker() {
        return model.getFinanceTracker();
    }

    @Override
    public ObservableList<Record> getFilteredRecordList() {
        return model.getFilteredRecordList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getFinanceTrackerFilePath() {
        return model.getFinanceTrackerFilePath();
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
    public ReadOnlyProperty<Record> selectedRecordProperty() {
        return model.selectedRecordProperty();
    }

    @Override
    public void setSelectedRecord(Record record) {
        model.setSelectedRecord(record);
    }
}
