package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * Unmodifiable view of an pocket project
 */
public interface ReadOnlyPocketProject extends Observable {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Employee> getEmployeeList();

    /**
     * Returns an unmodifiable view of the projects list.
     * This list will not contain any duplicate projects.
     */
    ObservableList<Project> getProjectList();

    /**
     * Returns an unmodifiable view of the completed projects list.
     * This list will not contain any duplicate projects.
     */
    ObservableList<Project> getCompletedProjectList();

}
