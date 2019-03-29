package seedu.address.logic;

//import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.ReadOnlyAddressBook;
//import seedu.address.model.person.Person;
import seedu.address.model.ReadOnlyHealthWorkerBook;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.request.Request;

import java.nio.file.Path;

/**
 * API of the Logic component
 *
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
     *
     * @see seedu.address.model.Model#getRequestBook()
     */
    ReadOnlyRequestBook getRequestBook();

    /**
     *
     * @see seedu.address.model.Model#getHealthWorkerBook()
     */
    ReadOnlyHealthWorkerBook getHealthWorkerBook();

    /** Returns an unmodifiable view of the filtered list of health workers */
    ObservableList<HealthWorker> getFilteredHealthWorkerList();

    /** Returns an unmodifiable view of the filtered list of requests */
    ObservableList<Request> getFilteredRequestList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs'request book file path.
     */
    Path getRequestBookFilePath();

    /**
     * Returns the user prefs' health worker book file path.
     */
    Path getHealthWorkerBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);


    /**
     * Selected health worker in the filtered health worker list.
     * null if no health worker is selected.
     */
    ReadOnlyProperty<HealthWorker> selectedHealthWorkerProperty();

    /**
     * Selected request in the filtered request list.
     * null if no request is selected.
     */
    ReadOnlyProperty<Request> selectedRequestProperty();


    /**
     * Sets the selected health worker in the filtered health worker list.
     */
    void setSelectedHealthWorker(HealthWorker worker);

    /**
     * Sets the selected request in the filtered request list.
     */
    void setSelectedRequest(Request request);
}
