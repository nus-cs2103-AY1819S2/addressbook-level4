package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_JAVA;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.getTypicalPocketProjectWithEmployees;
import static seedu.address.testutil.TypicalProjects.PROJECT_ALICE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.exceptions.DuplicateEmployeeException;
import seedu.address.model.project.Project;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.ProjectBuilder;
import seedu.address.testutil.TypicalProjects;

public class PocketProjectTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PocketProject pocketProject = new PocketProject();

    @Test
    public void constructor() {

        assertEquals(Collections.emptyList(), pocketProject.getEmployeeList());
        assertEquals(Collections.emptyList(), pocketProject.getProjectList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        pocketProject.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyPocketProject_replacesData() {
        PocketProject newData = TypicalProjects.addTypicalProjects(getTypicalPocketProjectWithEmployees());
        pocketProject.resetData(newData);
        assertEquals(newData, pocketProject);
    }

    @Test
    public void resetData_withDuplicateEmployees_throwsDuplicateEmployeeException() {
        // Two employees with the same identity fields
        Employee editedAlice = new EmployeeBuilder(ALICE).withGitHubAccount(VALID_GITHUB_ALICE)
            .withSkills(VALID_SKILL_JAVA).build();
        List<Employee> newEmployees = Arrays.asList(ALICE, editedAlice);
        PocketProjectStub newData = new PocketProjectStub(newEmployees);

        thrown.expect(DuplicateEmployeeException.class);
        pocketProject.resetData(newData);
    }

    @Test
    public void hasEmployee_nullEmployee_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        pocketProject.hasEmployee(null);
    }

    @Test
    public void hasProject_nullProject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        pocketProject.hasProject(null);
    }



    @Test
    public void hasEmployee_employeeNotInPocketProject_returnsFalse() {
        assertFalse(pocketProject.hasEmployee(ALICE));
    }

    @Test
    public void hasProject_projectNotInPocketProject_returnsFalse() {
        assertFalse(pocketProject.hasProject(PROJECT_ALICE));
    }

    @Test
    public void hasEmployee_employeeInPocketProject_returnsTrue() {
        pocketProject.addEmployee(ALICE);
        assertTrue(pocketProject.hasEmployee(ALICE));
    }

    @Test
    public void hasProject_projectInPocketProject_returnsTrue() {
        pocketProject.addProject(PROJECT_ALICE);
        assertTrue(pocketProject.hasProject(PROJECT_ALICE));
    }

    @Test
    public void hasEmployee_employeeWithSameIdentityFieldsInPocketProject_returnsTrue() {
        pocketProject.addEmployee(ALICE);
        Employee editedAlice = new EmployeeBuilder(ALICE).withGitHubAccount(VALID_GITHUB_ALICE)
            .withSkills(VALID_SKILL_JAVA).build();
        assertTrue(pocketProject.hasEmployee(editedAlice));
    }

    @Test
    public void hasProject_projectWithSameNameInPocketProject_returnsTrue() {
        pocketProject.addProject(PROJECT_ALICE);
        Project editedAlice = new ProjectBuilder(PROJECT_ALICE).withClient(VALID_CLIENT_BOB)
                .withDeadline(VALID_DEADLINE_AMY)
                .build();
        assertTrue(pocketProject.hasProject(editedAlice));
    }

    @Test
    public void getEmployeeList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        pocketProject.getEmployeeList().remove(0);
    }

    @Test
    public void getProjectList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        pocketProject.getProjectList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        pocketProject.addListener(listener);
        pocketProject.addEmployee(ALICE);
        assertEquals(1, counter.get());
        pocketProject.addProject(PROJECT_ALICE);
        assertEquals(2, counter.get());

    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        pocketProject.addListener(listener);
        pocketProject.removeListener(listener);
        pocketProject.addEmployee(ALICE);
        pocketProject.addProject(PROJECT_ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyPocketProject whose employees/projects list can violate interface constraints.
     */
    private static class PocketProjectStub implements ReadOnlyPocketProject {
        private final ObservableList<Employee> employees = FXCollections.observableArrayList();
        private final ObservableList<Project> projects = FXCollections.observableArrayList();
        private final ObservableList<Project> completedProjects = FXCollections.observableArrayList();

        PocketProjectStub(Collection<Employee> employees) {
            this.projects.setAll(projects);
            this.employees.setAll(employees);
            this.completedProjects.setAll(completedProjects);
        }


        @Override
        public ObservableList<Employee> getEmployeeList() {
            return employees;
        }

        @Override
        public ObservableList<Project> getCompletedProjectList() {
            return completedProjects;
        }
        @Override
        public ObservableList<Project> getProjectList() {
            return projects;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
