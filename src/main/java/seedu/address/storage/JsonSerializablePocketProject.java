package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PocketProject;
import seedu.address.model.ReadOnlyPocketProject;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * An Immutable PocketProject that is serializable to JSON format.
 */
@JsonRootName(value = "pocketproject")
class JsonSerializablePocketProject {

    public static final String MESSAGE_DUPLICATE_EMPLOYEE = "Employees list contains duplicate employee(s).";
    public static final String MESSAGE_DUPLICATE_PROJECT = "Project list contains duplicate project(s).";
    public static final String MESSAGE_DUPLICATE_COMPLETED_PROJECT =
            "Completed project list contains duplicate project(s).";

    private final List<JsonAdaptedEmployee> employees = new ArrayList<>();
    private final List<JsonAdaptedProject> projects = new ArrayList<>();
    private final List<JsonAdaptedProject> completedProjects = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePocketProject} with the given employees and projects.
     */
    @JsonCreator
    public JsonSerializablePocketProject(
            @JsonProperty("employees") List<JsonAdaptedEmployee> employees,
            @JsonProperty("projects") List<JsonAdaptedProject> projects,
            @JsonProperty("completedProjects") List<JsonAdaptedProject> completedProjects) {
        this.employees.addAll(employees);
        this.projects.addAll(projects);
        this.completedProjects.addAll(completedProjects);
    }

    /**
     * Converts a given {@code ReadOnlyPocketProject} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePocketProject}.
     */
    public JsonSerializablePocketProject(ReadOnlyPocketProject source) {
        employees.addAll(source.getEmployeeList().stream().map(JsonAdaptedEmployee::new).collect(Collectors.toList()));
        projects.addAll(source.getProjectList().stream().map(JsonAdaptedProject::new).collect(Collectors.toList()));
        completedProjects.addAll(source.getCompletedProjectList().stream()
                .map(JsonAdaptedProject::new).collect(Collectors.toList()));
    }

    /**
     * Converts this pocket project into the model's {@code PocketProject} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PocketProject toModelType() throws IllegalValueException {
        PocketProject pocketProject = new PocketProject();
        for (JsonAdaptedEmployee jsonAdaptedEmployee : employees) {
            Employee employee = jsonAdaptedEmployee.toModelType();
            if (pocketProject.hasEmployee(employee)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EMPLOYEE);
            }
            pocketProject.addEmployee(employee);
        }

        for (JsonAdaptedProject jsonAdaptedProject : projects) {
            Project project = jsonAdaptedProject.toModelType();
            if (pocketProject.hasProject(project)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROJECT);
            }
            pocketProject.addProject(project);
        }
        for (JsonAdaptedProject jsonAdaptedProject : completedProjects) {
            Project project = jsonAdaptedProject.toModelType();
            if (pocketProject.hasCompletedProject(project)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_COMPLETED_PROJECT);
            }
            pocketProject.addCompletedProject(project);
        }
        return pocketProject;
    }

}
