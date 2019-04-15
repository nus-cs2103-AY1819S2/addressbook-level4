package seedu.address.testutil;

import seedu.address.model.PocketProject;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * A utility class to help with building PocketProject objects.
 * Example usage: <br>
 *     {@code PocketProject ab = new PocketProjectBuilder().withEmployee("John", "Doe").build();}
 */
public class PocketProjectBuilder {

    private PocketProject pocketProject;

    public PocketProjectBuilder() {
        pocketProject = new PocketProject();
    }

    public PocketProjectBuilder(PocketProject pocketProject) {
        this.pocketProject = pocketProject;
    }

    /**
     * Adds a new {@code Employee} to the {@code PocketProject} that we are building.
     */
    public PocketProjectBuilder withEmployee(Employee employee) {
        pocketProject.addEmployee(employee);
        return this;
    }

    /**
     * Adds a new {@code Project} to the {@code PocketProject} that we are building.
     */
    public PocketProjectBuilder withProject(Project project) {
        pocketProject.addProject(project);
        return this;
    }

    public PocketProject build() {
        return pocketProject;
    }
}
