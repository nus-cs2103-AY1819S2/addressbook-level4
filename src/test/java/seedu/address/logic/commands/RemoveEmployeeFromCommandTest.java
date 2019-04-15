package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalProjects;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code RemoveEmployeeFromCommand}.
 */
public class RemoveEmployeeFromCommandTest {

    private Model model = new ModelManager(TestUtil.typicalPocketProject(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validProjectNameValidIndex_success() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Index validIndex = Index.fromOneBased(targetProject.getEmployees().size());
        RemoveEmployeeFromCommand removeEmployeeFromCommand = new RemoveEmployeeFromCommand(validIndex,
                targetProject.getProjectName());
        Employee targetEmployee = targetProject.getEmployees().get(validIndex.getZeroBased());
        String expectedMessage = String.format(RemoveEmployeeFromCommand.MESSAGE_REMOVE_EMPLOYEE_SUCCESS,
                targetEmployee, targetProject.getProjectName());

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.removeEmployeeFrom(targetProject, targetEmployee);
        expectedModel.commitPocketProject();

        assertCommandSuccess(removeEmployeeFromCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidProjectName_throwsCommandException() {
        RemoveEmployeeFromCommand removeEmployeeFromCommand = new RemoveEmployeeFromCommand(Index.fromOneBased(1),
                new ProjectName("INVALID"));
        assertCommandFailure(removeEmployeeFromCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECT_NAME);
    }

    @Test
    public void execute_invalidIndexValidProjectName_throwsCommandException() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Index outOfBoundIndex = Index.fromOneBased(targetProject.getEmployees().size() + 1);
        RemoveEmployeeFromCommand removeEmployeeFromCommand = new RemoveEmployeeFromCommand(outOfBoundIndex,
                targetProject.getProjectName());

        assertCommandFailure(removeEmployeeFromCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        RemoveEmployeeFromCommand removeEmployeeFromCommand1 = new RemoveEmployeeFromCommand(Index.fromOneBased(1),
                TypicalProjects.PROJECT_ALICE.getProjectName());
        RemoveEmployeeFromCommand removeEmployeeFromCommand2 = new RemoveEmployeeFromCommand(Index.fromOneBased(1),
                TypicalProjects.PROJECT_BENSON.getProjectName());

        // same object -> returns true
        assertTrue(removeEmployeeFromCommand1.equals(removeEmployeeFromCommand1));

        // same values -> returns true
        RemoveEmployeeFromCommand removeEmployeeFromCommand1Copy = new RemoveEmployeeFromCommand(Index.fromOneBased(1),
                TypicalProjects.PROJECT_ALICE.getProjectName());
        assertTrue(removeEmployeeFromCommand1.equals(removeEmployeeFromCommand1Copy));

        // different types -> returns false
        assertFalse(removeEmployeeFromCommand1.equals(1));

        // null -> returns false
        assertFalse(removeEmployeeFromCommand1.equals(null));

        // different projects -> returns false
        assertFalse(removeEmployeeFromCommand1.equals(removeEmployeeFromCommand2));

        // different indices -> returns false
        RemoveEmployeeFromCommand removeEmployeeFromCommand3 = new RemoveEmployeeFromCommand(Index.fromOneBased(2),
                TypicalProjects.PROJECT_ALICE.getProjectName());
        assertFalse(removeEmployeeFromCommand1.equals(removeEmployeeFromCommand3));
    }
}

