package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.exceptions.DuplicateProjectException;
import seedu.address.model.project.exceptions.ProjectNotFoundException;

/**
 * A list of Projects that enforces uniqueness between its elements and does not allow nulls.
 * An Project is considered unique by comparing using {@code Project#isSameProject(Project)}. As such, adding and
 * updating of Projects uses Project#isSameProject(Project) for equality so as to ensure that the Project being
 * added or updated is unique in terms of identity in the UniqueProjectList. However, the removal of an Project uses
 * Project#equals(Object) so as to ensure that the Project with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * //@see Project#isSameProject(Project)
 */
public class UniqueProjectList implements Iterable<Project> {

    private final ObservableList<Project> internalList = FXCollections.observableArrayList();
    private final ObservableList<Project> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Project as the given argument.
     */
    public boolean contains(Project toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameProject);
    }

    /**
     * Adds an Project to the list.
     * The Project must not already exist in the list.
     */
    public void add(Project toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateProjectException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns a clone of this UniqueProjectList object.
     */
    public UniqueProjectList clone() {
        UniqueProjectList newList = new UniqueProjectList();
        for (Project p: internalList) {
            newList.add(p.clone());
        }
        return newList;
    }

    /**
     * Replaces the Project {@code target} in the list with {@code editedProject}.
     * {@code target} must exist in the list.
     * The Project identity of {@code editedProject} must not be the same as another existing Project in the list.
     */
    public void setProject(Project target, Project editedProject) {
        requireAllNonNull(target, editedProject);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProjectNotFoundException();
        }

        if (!target.isSameProject(editedProject) && contains(editedProject)) {
            throw new DuplicateProjectException();
        }

        internalList.set(index, editedProject);
    }

    /**
     * Removes the equivalent Project from the list.
     * The Project must exist in the list.
     */
    public void remove(Project toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ProjectNotFoundException();
        }
    }

    public void setProjects(UniqueProjectList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Projects}.
     * {@code Projects} must not contain duplicate Projects.
     */
    public void setProjects(List<Project> projects) {
        requireAllNonNull(projects);
        if (!projectsAreUnique(projects)) {
            throw new DuplicateProjectException();
        }

        internalList.setAll(projects);
    }

    /**
     *  Removes the specified employee from the specified project. They must exist.
     */
    public void removeEmployeeFrom(Project project, Employee employee) {
        requireAllNonNull(project, employee);
        internalList.get(internalList.indexOf(project)).removeEmployee(employee);
    }

    /**
     *  Removes the specified milestone from the specified project. They must exist.
     */
    public void removeMilestoneFrom(Project project, Milestone milestone) {
        requireAllNonNull(project, milestone);
        internalList.get(internalList.indexOf(project)).removeMilestone(milestone);
    }

    /**
     *  Removes the specified user story from the specified project. They must exist.
     */
    public void removeUserStoryFrom(Project project, UserStory userStory) {
        requireAllNonNull(project, userStory);
        internalList.get(internalList.indexOf(project)).removeUserStory(userStory);
    }

    /**
     *  Removes the specified project task from the specified project milestone. They must exist.
     */
    public void removeProjectTaskFrom(Project project, Milestone milestone, ProjectTask task) {
        requireAllNonNull(project, milestone, task);
        List<Milestone> milestones = internalList.get(internalList.indexOf(project)).getMilestones();
        milestones.get(milestones.indexOf(milestone)).removeProjectTask(task);
    }

    /**
     *  Adds the specified employee to the specified project. They must exist.
     */
    public void addEmployeeTo(Project project, Employee employee) {
        requireAllNonNull(project, employee);
        internalList.get(internalList.indexOf(project)).addEmployee(employee);
    }

    /**
     *  Removes the specified milestone from the specified project. They must exist.
     */
    public void addMilestoneTo(Project project, Milestone milestone) {
        requireAllNonNull(project, milestone);
        internalList.get(internalList.indexOf(project)).addMilestone(milestone);
    }

    /**
     *  Adds the specified user story to the specified project. They must exist.
     */
    public void addUserStoryTo(Project project, UserStory userStory) {
        requireAllNonNull(project, userStory);
        internalList.get(internalList.indexOf(project)).addUserStory(userStory);
    }

    /**
     * Updates the specified user story in the specified project. They must exist.
     */
    public void updateUserStory(Project project, UserStory userStory, Status newStatus) {
        requireAllNonNull(project, userStory, newStatus);
        internalList.get(internalList.indexOf(project)).updateUserStory(userStory, newStatus);
    }

    /**
     *  Adds the specified project task to the specified project milestone. They must exist.
     */
    public void addProjectTaskTo(Project project, Milestone milestone, ProjectTask task) {
        requireAllNonNull(project, milestone, task);
        List<Milestone> milestones = internalList.get(internalList.indexOf(project)).getMilestones();
        milestones.get(milestones.indexOf(milestone)).addProjectTask(task);
    }

    /**
     * Updates the specified project task in the specified project milestone. They must exist.
     */
    public void updateProjectTask(Project targetProject, Milestone targetMilestone, ProjectTask targetTask,
                                  Status newStatus) {
        requireAllNonNull(targetProject, targetMilestone, targetTask, newStatus);
        List<Milestone> milestones = internalList.get(internalList.indexOf(targetProject)).getMilestones();
        milestones.get(milestones.indexOf(targetMilestone)).updateProjectTask(targetTask, newStatus);
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Project> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Project> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueProjectList // instanceof handles nulls
                && internalList.equals(((UniqueProjectList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Projects} contains only unique Projects.
     */
    private boolean projectsAreUnique(List<Project> projects) {
        for (int i = 0; i < projects.size() - 1; i++) {
            for (int j = i + 1; j < projects.size(); j++) {
                if (projects.get(i).isSameProject(projects.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
