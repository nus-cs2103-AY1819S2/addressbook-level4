package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.ProjectTask;
import seedu.address.model.project.Status;
import seedu.address.model.project.UserStory;
import seedu.address.model.util.PocketProjectDate;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Employee> PREDICATE_SHOW_ALL_EMPLOYEES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Project> PREDICATE_SHOW_ALL_PROJECTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' pocket project file path.
     */
    Path getPocketProjectFilePath();

    /**
     * Sets the user prefs' pocket project file path.
     */
    void setPocketProjectFilePath(Path pocketProjectFilePath);

    /**
     * Replaces pocket project data with the data in {@code pocketProject}.
     */
    void setPocketProject(ReadOnlyPocketProject pocketProject);

    /** Returns the PocketProject */
    ReadOnlyPocketProject getPocketProject();


    //----------------Methods related to employees ----------------------------------------------------------
    /**
     * Returns true if a employee with the same identity as {@code employee} exists in the pocket project.
     */
    boolean hasEmployee(Employee employee);

    /**
     * Deletes the given employee.
     * The employee must exist in the pocket project.
     */
    void deleteEmployee(Employee target);

    /**
     * Adds the given employee.
     * {@code employee} must not already exist in the pocket project.
     */
    void addEmployee(Employee employee);

    /**
     * Replaces the given employee {@code target} with {@code editedEmployee}.
     * {@code target} must exist in the pocket project.
     * The employee identity of {@code editedEmployee} must not be the same as another existing employee in the pocket
     * project.
     */
    void setEmployee(Employee target, Employee editedEmployee);

    /** Returns an unmodifiable view of the filtered employee list */
    ObservableList<Employee> getFilteredEmployeeList();

    /**
     * Updates the filter of the filtered employee list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEmployeeList(Predicate<Employee> predicate);

    /**
     * Selected employee in the filtered employee list.
     * null if no employee is selected.
     */
    ReadOnlyProperty<Employee> selectedEmployeeProperty();

    /**
     * Returns the selected employee in the filtered employee list.
     * null if no employee is selected.
     */
    Employee getSelectedEmployee();

    /**
     * Sets the selected employee in the filtered employee list.
     */
    void setSelectedEmployee(Employee employee);



    //------------- Methods related to projects ----------------------------------------------------------
    /**
     * Returns true if a employee with the same identity as {@code employee} exists in the pocket project.
     */
    boolean hasProject(Project employee);

    /**
     * Deletes the given project.
     * The project must exist in the projects list.
     */
    void deleteProject(Project target);

    /**
     * Adds the given project.
     * {@code project} must not already exist in the projects list.
     */
    void addProject(Project project);

    /** Returns an unmodifiable view of the filtered projects list */
    ObservableList<Project> getFilteredProjectList();

    /**
     * Updates the filter of the filtered project list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProjectList(Predicate<Project> predicate);

    /**
     * Returns an unmodifiable view of the list of {@code Project} backed by the internal list of
     * {@code versionedPocketProject}
     */
    ObservableList<Project> getProjectList();

    /**
     * Returns an unmodifiable view of the list of completed {@code Project} backed by the internal list of
     * {@code versionedPocketProject}
     */
    ObservableList<Project> getCompletedProjectList();

    /**
     * Returns an unmodifiable view of the list of {@code Employee} backed by the internal list of
     * {@code versionedPocketProject}
     */
    ObservableList<Employee> getEmployeeList();

    /**
     * Completes a project and move it to the list of completed projects.
     */
    void completeProject(Project project, PocketProjectDate completionDate);

    /**
     * Selected project in the filtered projects list.
     * null if no project is selected.
     */
    ReadOnlyProperty<Project> selectedProjectProperty();

    /**
     * Returns the selected project in the filtered projects list.
     * null if no project is selected.
     */
    Project getSelectedProject();

    /**
     * Sets the selected project in the filtered projects list.
     */
    void setSelectedProject(Project project);

    /**
     * Replaces the given project {@code target} with {@code editedProject}.
     * {@code target} must exist in the pocket project.
     * The project name of {@code editedProject} must not be the same as another existing project in the pocket
     * project.
     */
    void setProject(Project target, Project editedProject);

    /**
     * Returns the project with the given name if it exists, if not, return null.
     */
    Project getProjectWithName(ProjectName targetProjectName);

    /**
     * Removes the {@code targetEmployee} from the {@code targetProject}.
     * Both {@code targetProject} must exist in the pocket project and {@code targetEmployee} in the employee list of
     * {@code targetProject}.
     */
    void removeEmployeeFrom(Project targetProject, Employee targetEmployee);

    /**
     * Removes the {@code targetMilestone} from the {@code targetProject}.
     * Both {@code targetProject} must exist in the pocket project and {@code targetMilestone} in the milestone list of
     * {@code targetProject}.
     */
    void removeMilestoneFrom(Project targetProject, Milestone targetMilestone);

    /**
     * Removes the {@code targetUserStory} from the {@code targetProject}.
     * Both {@code targetProject} must exist in the pocket project and {@code targetUserStory} in the userstory list of
     * {@code targetProject}.
     */
    void removeUserStoryFrom(Project targetProject, UserStory targetUserStory);

    /**
     * Removes the {@code targetProjectTask} from the {@code targetMilestone} in the {@code targetProject}.
     * {@code targetProject} must exist in the pocket project and {@code targetMilestone} and {@code targetProjectTask}
     * in the milestone and task list of {@code targetProject}.
     */
    void removeProjectTaskFrom(Project targetProject, Milestone targetMilestone, ProjectTask targetProjectTask);

    /**
     * Updates the {@code targetStory} from the {@code targetProject} to the new {@code newStatus}.
     */
    void updateUserStory(Project targetProject, UserStory targetStory, Status newStatus);

    /**
     * Adds the {@code targetEmployee} to the {@code targetProject}.
     * Both {@code targetProject} must exist in the pocket project and {@code targetEmployee} in the employee list
     */
    void addEmployeeTo(Project targetProject, Employee targetEmployee);

    /**
     * Adds the {@code milestone} to the {@code targetProject}.
     * {@code targetProject} must exist in the pocket project.
     */
    void addMilestoneTo(Project targetProject, Milestone milestone);

    /**
     * Adds the {@code userStory} to the {@code targetProject}.
     * {@code targetProject} must exist in the pocket project.
     */
    void addUserStoryTo(Project targetProject, UserStory targetUserStory);

    /**
     * Returns the list of projects that this employee is working on.
     */
    List<Project> getProjectsContaining(Employee employee);

    /**
     * Returns a string describing the overview of all projects.
     */
    String overallStats();

    /**
     * Adds the {@code task} to the {@code milestone}.
     * {@code milestone} must exist in the pocket project.
     */
    void addProjectTaskTo(Project targetProject, Milestone milestone, ProjectTask task);

    /**
     * Updates the {@code targetTask} from the {@code targetProject} to the new {@code newStatus}.
     */
    void updateProjectTask(Project targetProject, Milestone targetMilestone, ProjectTask targetTask,
                           Status newStatus);

    /**
     * Returns a string describing the progress of an individual project.
     */
    String individualStats(Project project);


    //-------------------------------------------------------------------------------------------

    /**
     * Returns true if the model has previous pocket project states to restore.
     */
    boolean canUndoPocketProject();

    /**
     * Returns true if the model has undone pocket project states to restore.
     */
    boolean canRedoPocketProject();

    /**
     * Restores the model's pocket project to its previous state.
     */
    void undoPocketProject();

    /**
     * Restores the model's pocket project to its previously undone state.
     */
    void redoPocketProject();

    /**
     * Saves the current pocket project state for undo/redo.
     */
    void commitPocketProject();

}
