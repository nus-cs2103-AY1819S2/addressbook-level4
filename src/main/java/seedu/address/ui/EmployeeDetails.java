package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import seedu.address.model.employee.Employee;

/**
 * Encapsulates all the information to display about an employee
 */
public class EmployeeDetails {

    private Employee employee;
    private List<Node> employeeDetailsList;
    private EmployeeSummary employeeSummary;
    private EmployeeProjects employeeProjects;
    private EmployeeGitHub gitHubProfile;

    public EmployeeDetails(Employee employee) {
        this.employee = employee;
        employeeDetailsList = new ArrayList<>();
        employeeSummary = new EmployeeSummary(employee);
        employeeProjects = new EmployeeProjects(employee.getCurrentProjects());
        gitHubProfile = new EmployeeGitHub(employee.getGithub());

        employeeDetailsList.add(employeeSummary.getRoot());
        employeeDetailsList.add(employeeProjects.getRoot());
        employeeDetailsList.add(gitHubProfile.getRoot());
    }

    public List<Node> getEmployeeDetails() {
        return employeeDetailsList;
    }

}
