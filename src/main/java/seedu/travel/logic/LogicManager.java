package seedu.travel.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import seedu.travel.commons.core.GuiSettings;
import seedu.travel.commons.core.LogsCenter;
import seedu.travel.logic.commands.Command;
import seedu.travel.logic.commands.CommandResult;
import seedu.travel.logic.commands.exceptions.CommandException;
import seedu.travel.logic.parser.TravelBuddyParser;
import seedu.travel.logic.parser.exceptions.ParseException;
import seedu.travel.model.Model;
import seedu.travel.model.ReadOnlyTravelBuddy;
import seedu.travel.model.place.Place;
import seedu.travel.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    public static final boolean DISPLAY_CHART_INITIALLY = false;

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final TravelBuddyParser travelBuddyParser;
    private boolean travelBuddyModified;
    private final SimpleBooleanProperty chartDisplayed = new SimpleBooleanProperty(DISPLAY_CHART_INITIALLY);

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        travelBuddyParser = new TravelBuddyParser();

        // Set travelBuddyModified to true whenever the models' travel book is modified.
        model.getTravelBuddy().addListener(observable -> travelBuddyModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        travelBuddyModified = false;

        CommandResult commandResult;
        try {
            Command command = travelBuddyParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (travelBuddyModified) {
            logger.info("TravelBuddy modified, saving to file.");
            try {
                storage.saveTravelBuddy(model.getTravelBuddy());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyTravelBuddy getTravelBuddy() {
        return model.getTravelBuddy();
    }

    @Override
    public ObservableList<Place> getFilteredPlaceList() {
        return model.getFilteredPlaceList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getTravelBuddyFilePath() {
        return model.getTravelBuddyFilePath();
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
    public ReadOnlyProperty<Place> selectedPlaceProperty() {
        return model.selectedPlaceProperty();
    }

    @Override
    public void setSelectedPlace(Place place) {
        model.setSelectedPlace(place);
    }

    @Override
    public void setChartDisplayed(boolean isDisplayed) {
        model.setChartDisplayed(isDisplayed);
    }
}
