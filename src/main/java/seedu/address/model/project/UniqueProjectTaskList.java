package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.project.exceptions.DuplicateProjectTaskException;
import seedu.address.model.project.exceptions.ProjectTaskNotFoundException;

/**
 * A list of Project Tasks that enforces uniqueness between its elements and does not allow nulls.
 * A Project Task is considered unique by comparing using {@code ProjectTask#isSameTask(ProjectTask)}. As such, adding
 * and updating of Project Tasks uses ProjectTask#isSameTask(ProjectTask) for equality so as to ensure that the Project
 * Task being added or updated is unique in terms of identity in the UniqueProjectTaskList. However, the removal of a
 * Project Task uses ProjectTask#equals(Object) so as to ensure that the Project Task with exactly the same fields will
 * be removed.
 *
 * Supports a minimal set of list operations.
 *
 * //@see ProjectTask#isSameTask(ProjectTask)
 */
public class UniqueProjectTaskList implements Iterable<ProjectTask> {

    private final ObservableList<ProjectTask> internalList = FXCollections.observableArrayList();
    private final ObservableList<ProjectTask> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Project Task as the given argument.
     */
    public boolean contains(ProjectTask toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTask);
    }

    /**
     * Adds a Project Task to the list.
     * The Project Task must not already exist in the list.
     */
    public void add(ProjectTask toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateProjectTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns a clone of this UniqueProjectTaskList object.
     */
    public UniqueProjectTaskList clone() {
        UniqueProjectTaskList newList = new UniqueProjectTaskList();
        for (ProjectTask t: internalList) {
            newList.add(t.clone());
        }
        return newList;
    }

    /**
     * Removes the equivalent Project Task from the list.
     * The Project Task must exist in the list.
     */
    public void remove(ProjectTask toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ProjectTaskNotFoundException();
        }
    }

    public void setProjects(UniqueProjectTaskList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code ProjectTasks}.
     * {@code ProjectTasks} must not contain duplicate Project Tasks.
     */
    public void setProjectTasks(List<ProjectTask> projectTasks) {
        requireAllNonNull(projectTasks);
        if (!projectTasksAreUnique(projectTasks)) {
            throw new DuplicateProjectTaskException();
        }

        internalList.setAll(projectTasks);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ProjectTask> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ProjectTask> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueProjectTaskList // instanceof handles nulls
            && internalList.equals(((UniqueProjectTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


    /**
     * Returns true if {@code projectTasks} contains only unique Project Tasks.
     */
    private boolean projectTasksAreUnique(List<ProjectTask> projectTasks) {
        for (int i = 0; i < projectTasks.size() - 1; i++) {
            for (int j = i + 1; j < projectTasks.size(); j++) {
                if (projectTasks.get(i).isSameTask(projectTasks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
