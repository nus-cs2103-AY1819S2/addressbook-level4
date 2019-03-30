package seedu.knowitall.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.knowitall.commons.core.GuiSettings;
import seedu.knowitall.commons.core.LogsCenter;
import seedu.knowitall.logic.commands.Command;
import seedu.knowitall.logic.commands.CommandResult;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.logic.parser.CommandParser;
import seedu.knowitall.logic.parser.exceptions.ParseException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ReadOnlyCardFolder;
import seedu.knowitall.model.VersionedCardFolder;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final CommandParser commandParser;
    private boolean cardFolderModified;
    private boolean modelModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        commandParser = new CommandParser();

        // Add listeners to all card folders and the model
        addCardFolderListeners(model);

        // Set modelModified whenever the models' card folders are modified
        model.addListener(observable -> modelModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        cardFolderModified = false;
        modelModified = false;

        CommandResult commandResult;
        try {
            Command command = commandParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (cardFolderModified) {
            logger.info("card folder modified, saving to file.");
            try {
                storage.saveCardFolder(model.getActiveCardFolder(), model.getActiveCardFolderIndex());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        if (modelModified) {
            logger.info("model is modified, saving to file");
            try {
                List<ReadOnlyCardFolder> cardFolders = model.getCardFolders();
                Path path = model.getcardFolderFilesPath();

                storage.saveCardFolders(cardFolders, path);
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }

            // re-register listeners to all card folders
            addCardFolderListeners(model);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyCardFolder getCardFolder() {
        return model.getActiveCardFolder();
    }

    @Override
    public ObservableList<Card> getFilteredCards() {
        return model.getFilteredCards();
    }

    @Override
    public ObservableList<VersionedCardFolder> getFilteredCardFolders() {
        return model.getFilteredFolders();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getcardFolderFilesPath() {
        return model.getcardFolderFilesPath();
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
    public ReadOnlyProperty<Card> selectedCardProperty() {
        return model.selectedCardProperty();
    }

    @Override
    public void setSelectedCard(Card card) {
        model.setSelectedCard(card);
    }

    /**
     * Adds listeners to all {@code CardFolders} in {@code model}
     */
    private void addCardFolderListeners(Model model) {
        // Set cardFolderModified to true whenever the models' card folder is modified.
        for (ReadOnlyCardFolder cardFolder : model.getCardFolders()) {
            cardFolder.addListener(observable -> cardFolderModified = true);
        }
    }
}
