package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyPocketProject;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

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
     * Returns the PocketProject.
     *
     * @see seedu.address.model.Model#getPocketProject()
     */
    ReadOnlyPocketProject getPocketProject();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Employee> getFilteredEmployeeList();

    /** Returns an unmodifiable view of the filtered list of projects */
    ObservableList<Project> getFilteredProjectList();


    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' pocket project file path.
     */
    Path getPocketProjectFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected employee in the filtered employee list.
     * null if no employee is selected.
     *
     * @see seedu.address.model.Model#selectedEmployeeProperty()
     */
    ReadOnlyProperty<Employee> selectedEmployeeProperty();

    /**
     * Sets the selected employee in the filtered employee list.
     *
     * @see seedu.address.model.Model#setSelectedEmployee(Employee)
     */
    void setSelectedEmployee(Employee employee);

    /**
     * Selected project in the filtered project list.
     * null if no project is selected.
     *
     * @see seedu.address.model.Model#selectedProjectProperty()
     */
    ReadOnlyProperty<Project> selectedProjectProperty();

    /**
     * Sets the selected project in the filtered project list.
     *
     * @see seedu.address.model.Model#setSelectedProject(Project)
     */
    void setSelectedProject(Project project);
}
