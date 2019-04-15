package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.EmployeeCardHandle;
import guitests.guihandles.EmployeeListPanelHandle;
import guitests.guihandles.ProjectCardHandle;
import guitests.guihandles.ResultDisplayHandle;

import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(EmployeeCardHandle expectedCard, EmployeeCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getGithub(), actualCard.getGithub());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getSkills(), actualCard.getSkills());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(ProjectCardHandle expectedCard, ProjectCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getProjectName(), actualCard.getProjectName());
        assertEquals(expectedCard.getClient(), actualCard.getClient());
        assertEquals(expectedCard.getDeadline(), actualCard.getDeadline());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEmployee}.
     */
    public static void assertCardDisplaysEmployee(Employee expectedEmployee, EmployeeCardHandle actualCard) {
        System.out.println("expected: " + expectedEmployee.getEmployeeName().fullName + "actual "
            + actualCard.getName());
        assertEquals(expectedEmployee.getEmployeeName().fullName, actualCard.getName());
        assertEquals(expectedEmployee.getPhone().value, actualCard.getPhone());
        assertEquals(expectedEmployee.getEmail().value, actualCard.getEmail());
        assertEquals(expectedEmployee.getGithub().value, actualCard.getGithub());
        assertEquals(expectedEmployee.getSkills().stream().map(skill -> skill.skillName).collect(Collectors.toList()),
                actualCard.getSkills());
    }

    /**
     * Asserts that the list in {@code employeeListPanelHandle} displays the details of {@code employees} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EmployeeListPanelHandle employeeListPanelHandle, Employee... employees) {
        for (int i = 0; i < employees.length; i++) {
            employeeListPanelHandle.navigateToCard(i);
            assertCardDisplaysEmployee(employees[i], employeeListPanelHandle.getEmployeeCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code employeeListPanelHandle} displays the details of {@code employees} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EmployeeListPanelHandle employeeListPanelHandle, List<Employee> employees) {
        assertListMatching(employeeListPanelHandle, employees.toArray(new Employee[0]));
    }

    /**
     * Asserts the size of the list in {@code employeeListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(EmployeeListPanelHandle employeeListPanelHandle, int size) {
        int numberOfPeople = employeeListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedProject}.
     */
    public static void assertCardDisplaysProject(Project expectedProject, ProjectCardHandle actualCard) {
        assertEquals(expectedProject.getProjectName().projectName, actualCard.getProjectName());
        assertEquals(expectedProject.getClient().client, actualCard.getClient());
        assertEquals(expectedProject.getDeadline().date, actualCard.getDeadline());
    }
}
