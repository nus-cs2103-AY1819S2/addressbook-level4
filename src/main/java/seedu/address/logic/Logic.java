package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyFoodDiary;
import seedu.address.model.restaurant.Restaurant;

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
     * Returns the FoodDiary.
     *
     * @see seedu.address.model.Model#getFoodDiary()
     */
    ReadOnlyFoodDiary getFoodDiary();

    /** Returns an unmodifiable view of the filtered list of restaurants */
    ObservableList<Restaurant> getFilteredRestaurantList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' food diary file path.
     */
    Path getFoodDiaryFilePath();

    String getName();
    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected restaurant in the filtered restaurant list.
     * null if no restaurant is selected.
     *
     * @see seedu.address.model.Model#selectedRestaurantProperty()
     */
    ReadOnlyProperty<Restaurant> selectedRestaurantProperty();


    /**
     * Sets the selected restaurant in the filtered restaurant list.
     *
     * @see seedu.address.model.Model#setSelectedRestaurant(Restaurant)
     */
    void setSelectedRestaurant(Restaurant restaurant);
}
