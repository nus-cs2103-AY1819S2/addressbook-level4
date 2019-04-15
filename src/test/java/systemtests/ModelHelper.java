package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Employee> PREDICATE_MATCHING_NO_EMPLOYEES = unused -> false;
    private static final Predicate<Project> PREDICATE_MATCHING_NO_PROJECTS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Employee> toDisplay) {
        Optional<Predicate<Employee>> predicate =
            toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredEmployeeList(predicate.orElse(PREDICATE_MATCHING_NO_EMPLOYEES));
    }
    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Employee... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * @see ModelHelper#setProjectFilteredList(Model, List)
     */
    public static void setProjectFilteredList(Model model, Project... toDisplay) {
        setProjectFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setProjectFilteredList(Model model, List<Project> toDisplay) {
        Optional<Predicate<Project>> predicate =
            toDisplay.stream().map(ModelHelper::getProjectPredicateMatching).reduce(Predicate::or);
        model.updateFilteredProjectList(predicate.orElse(PREDICATE_MATCHING_NO_PROJECTS));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Employee} equals to {@code other}.
     */
    private static Predicate<Employee> getPredicateMatching(Employee other) {
        return employee -> employee.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Project} equals to {@code other}.
     */
    private static Predicate<Project> getProjectPredicateMatching(Project other) {
        return project -> project.equals(other);
    }
}
