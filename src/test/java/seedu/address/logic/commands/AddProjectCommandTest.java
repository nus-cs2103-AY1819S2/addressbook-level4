package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalProjects.PROJECT_ALICE;
import static seedu.address.testutil.TypicalProjects.PROJECT_BENSON;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PocketProject;
import seedu.address.model.ReadOnlyPocketProject;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.ProjectTask;
import seedu.address.model.project.Status;
import seedu.address.model.project.UserStory;
import seedu.address.model.util.PocketProjectDate;
import seedu.address.testutil.ProjectBuilder;

public class AddProjectCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddProjectCommand(null);
    }

    @Test
    public void execute_projectAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingProjectAdded modelStub = new ModelStubAcceptingProjectAdded();
        Project validProject = new ProjectBuilder().build();

        CommandResult commandResult = new AddProjectCommand(validProject).execute(modelStub, commandHistory);

        assertEquals(String.format(AddProjectCommand.MESSAGE_ADD_PROJECT_SUCCESS, validProject),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validProject), modelStub.projectsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateProject_throwsCommandException() throws Exception {
        Project validProject = new ProjectBuilder().build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(validProject);
        ModelStub modelStub = new ModelStubWithProject(validProject);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_DUPLICATE_PROJECT);
        addProjectCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Project alice = PROJECT_ALICE;
        Project benson = PROJECT_BENSON;
        AddProjectCommand addAliceCommand = new AddProjectCommand(alice);
        AddProjectCommand addBensonCommand = new AddProjectCommand(benson);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddProjectCommand addAliceCommandCopy = new AddProjectCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different employee -> returns false
        assertFalse(addAliceCommand.equals(addBensonCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getPocketProjectFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPocketProjectFilePath(Path pocketProjectFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPocketProject(ReadOnlyPocketProject pocketProject) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public String individualStats(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Project> getProjectList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Project> getCompletedProjectList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Employee> getEmployeeList() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ReadOnlyPocketProject getPocketProject() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public String overallStats() {
            throw new AssertionError("This method should not be called.");
        }

        //---------------- Methods related to employee class --------------------------------------
        @Override
        public void addEmployee(Employee employee) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public boolean hasEmployee(Employee employee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEmployee(Employee target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEmployee(Employee target, Employee editedEmployee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProject(Project target, Project editedProject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Employee> getFilteredEmployeeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        //----------------------------methods related to project class ----------------------------------------
        @Override
        public void addProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void completeProject(Project project, PocketProjectDate completionDate) {
            throw new AssertionError("This method should not be called."); }
        @Override
        public boolean hasProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteProject(Project target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Project> getFilteredProjectList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredProjectList(Predicate<Project> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Project getProjectWithName(ProjectName projectName) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void removeEmployeeFrom(Project targetProject, Employee targetEmployee) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void removeMilestoneFrom(Project targetProject, Milestone targetMilestone) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void removeUserStoryFrom(Project targetProject, UserStory targetStory) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void removeProjectTaskFrom(Project targetProject, Milestone targetMilestone,
                                          ProjectTask targetProjectTask) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addEmployeeTo(Project targetProject, Employee targetEmployee) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addMilestoneTo(Project targetProject, Milestone targetMilestone) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addUserStoryTo(Project targetProject, UserStory targetStory) {
            throw new AssertionError("This method should not be called");
        }
        @Override
        public void updateUserStory(Project targetProject, UserStory targetStory, Status newStatus) {
            throw new AssertionError("This method should not be called");
        }
        @Override
        public void addProjectTaskTo(Project targetProject, Milestone milestone, ProjectTask task) {
            throw new AssertionError("This method should not be called");
        }
        @Override
        public void updateProjectTask(Project targetProject, Milestone targetMilestone, ProjectTask targetTask,
                                      Status newStatus) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public List<Project> getProjectsContaining(Employee employee) {
            throw new AssertionError("This method should not be called.");
        }

        //--------------------------------------------------------------------------------------------------

        @Override
        public boolean canUndoPocketProject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoPocketProject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoPocketProject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoPocketProject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitPocketProject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Employee> selectedEmployeeProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Employee getSelectedEmployee() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedEmployee(Employee employee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Project> selectedProjectProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Project getSelectedProject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single project.
     */
    private class ModelStubWithProject extends AddProjectCommandTest.ModelStub {
        private final Project project;

        ModelStubWithProject(Project project) {
            requireNonNull(project);
            this.project = project;
        }

        @Override
        public boolean hasProject(Project project) {
            requireNonNull(project);
            return this.project.isSameProject(project);
        }
    }

    /**
     * A Model stub that always accept the employee being added.
     */
    private class ModelStubAcceptingProjectAdded extends AddProjectCommandTest.ModelStub {
        final ArrayList<Project> projectsAdded = new ArrayList<>();

        @Override
        public boolean hasProject(Project project) {
            requireNonNull(project);
            return projectsAdded.stream().anyMatch(project::isSameProject);
        }

        @Override
        public void addProject(Project project) {
            requireNonNull(project);
            projectsAdded.add(project);
        }

        @Override
        public void commitPocketProject() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyPocketProject getPocketProject() {
            return new PocketProject();
        }
    }

}
