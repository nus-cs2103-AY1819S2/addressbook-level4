package seedu.travel.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import seedu.travel.commons.core.GuiSettings;
import seedu.travel.logic.commands.CommandResult;
import seedu.travel.logic.commands.exceptions.CommandException;
import seedu.travel.logic.parser.exceptions.ParseException;
import seedu.travel.model.ReadOnlyTravelBuddy;
import seedu.travel.model.place.Place;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the TravelBuddy.
     *
     * @see seedu.travel.model.Model#getTravelBuddy()
     */
    ReadOnlyTravelBuddy getTravelBuddy();

    /** Returns an unmodifiable view of the filtered list of places */
    ObservableList<Place> getFilteredPlaceList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' TravelBuddy file path.
     */
    Path getTravelBuddyFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected place in the filtered place list.
     * null if no place is selected.
     *
     * @see seedu.travel.model.Model#selectedPlaceProperty()
     */
    ReadOnlyProperty<Place> selectedPlaceProperty();

    /**
     * Sets the selected place in the filtered place list.
     *
     * @see seedu.travel.model.Model#setSelectedPlace(Place)
     */
    void setSelectedPlace(Place place);

    SimpleBooleanProperty chartDisplayed;

    /**
     * Set when chart needs to be displayed.
     */
    void setChartDisplayed(boolean chartDisplayed);
}
