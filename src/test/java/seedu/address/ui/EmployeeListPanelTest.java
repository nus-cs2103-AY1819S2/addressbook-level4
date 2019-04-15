package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalEmployees.getTypicalEmployees;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEmployee;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.EmployeeCardHandle;
import guitests.guihandles.EmployeeListPanelHandle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.GitHubAccount;
import seedu.address.model.employee.Phone;



public class EmployeeListPanelTest extends GuiUnitTest {
    private static final ObservableList<Employee> TYPICAL_EMPLOYEES =
            FXCollections.observableList(getTypicalEmployees());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Employee> selectedEmployee = new SimpleObjectProperty<>();
    private EmployeeListPanelHandle employeeListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_EMPLOYEES);

        for (int i = 0; i < TYPICAL_EMPLOYEES.size(); i++) {
            employeeListPanelHandle.navigateToCard(TYPICAL_EMPLOYEES.get(i));
            Employee expectedEmployee = TYPICAL_EMPLOYEES.get(i);
            EmployeeCardHandle actualCard = employeeListPanelHandle.getEmployeeCardHandle(i);

            assertCardDisplaysEmployee(expectedEmployee, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedEmployeeChanged_selectionChanges() {
        initUi(TYPICAL_EMPLOYEES);
        Employee secondEmployee = TYPICAL_EMPLOYEES.get(INDEX_SECOND_EMPLOYEE.getZeroBased());
        guiRobot.interact(() -> selectedEmployee.set(secondEmployee));
        guiRobot.pauseForHuman();

        EmployeeCardHandle expectedEmployee =
                employeeListPanelHandle.getEmployeeCardHandle(INDEX_SECOND_EMPLOYEE.getZeroBased());
        EmployeeCardHandle selectedEmployee = employeeListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedEmployee, selectedEmployee);
    }

    /**
     * Verifies that creating and deleting large number of employees in {@code EmployeeListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Employee> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of employee cards exceeded time limit");
    }

    /**
     * Returns a list of employees containing {@code employeeCount} employees that is used to populate the
     * {@code EmployeeListPanel}.
     */
    private ObservableList<Employee> createBackingList(int employeeCount) {
        ObservableList<Employee> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < employeeCount; i++) {
            EmployeeName employeeName = new EmployeeName(i + "a");
            Phone phone = new Phone("000");
            Email email = new Email("a@aa");
            GitHubAccount gitHubAccount = new GitHubAccount("a");
            Employee employee = new Employee(employeeName, phone, email, gitHubAccount, Collections.emptySet());
            backingList.add(employee);
        }
        return backingList;
    }

    /**
     * Initializes {@code employeeListPanelHandle} with a {@code EmployeeListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code EmployeeListPanel}.
     */
    private void initUi(ObservableList<Employee> backingList) {
        EmployeeListPanel employeeListPanel =
                new EmployeeListPanel(backingList, selectedEmployee, selectedEmployee::set);
        uiPartRule.setUiPart(employeeListPanel);

        employeeListPanelHandle = new EmployeeListPanelHandle(getChildNode(employeeListPanel.getRoot(),
                EmployeeListPanelHandle.EMPLOYEE_LIST_VIEW_ID));
    }
}
