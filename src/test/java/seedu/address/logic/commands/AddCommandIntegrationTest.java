package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmployees.getTypicalPocketProjectWithEmployees;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.testutil.EmployeeBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPocketProjectWithEmployees(), new UserPrefs());
    }

    @Test
    public void execute_newEmployee_success() {
        Employee validEmployee = new EmployeeBuilder().withName("Jason Picard").withPhone("90990099")
            .withEmail("jasonroxxx@gmail.com").withGitHubAccount("jasonballer").build();

        Model expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.addEmployee(validEmployee);
        expectedModel.commitPocketProject();

        assertCommandSuccess(new AddEmployeeCommand(validEmployee), model, commandHistory,
                String.format(AddEmployeeCommand.MESSAGE_ADD_EMPLOYEE_SUCCESS, validEmployee), expectedModel);
    }

    @Test
    public void execute_duplicateEmployee_throwsCommandException() {
        Employee employeeInList = model.getPocketProject().getEmployeeList().get(0);
        assertCommandFailure(new AddEmployeeCommand(employeeInList), model, commandHistory,
                Messages.MESSAGE_DUPLICATE_EMPLOYEE);
    }

}
