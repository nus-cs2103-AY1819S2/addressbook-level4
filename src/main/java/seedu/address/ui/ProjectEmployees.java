package seedu.address.ui;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.model.employee.Employee;
import seedu.address.model.skill.Skill;

/**
 * UI component to represent employees in a project
 */
public class ProjectEmployees extends UiPart<Region> {

    public static final String FXML = "ProjectEmployees.fxml";

    private ObservableList<Employee> employees;

    private ObservableList<ProjectEmployees.EmployeeCell> employeeCells;

    @FXML
    private TableView<ProjectEmployees.EmployeeCell> employeeTableView;

    @FXML
    private TableColumn<ProjectEmployees.EmployeeCell, String> indexCol;

    @FXML
    private TableColumn<ProjectEmployees.EmployeeCell, String> nameCol;

    @FXML
    private TableColumn<ProjectEmployees.EmployeeCell, String> githubCol;

    @FXML
    private TableColumn<ProjectEmployees.EmployeeCell, String> skillsCol;

    public ProjectEmployees(ObservableList<Employee> employees) {
        super(FXML);
        this.employees = employees;
        employeeCells = getEmployeeCells(employees);
        employeeTableView.setItems(employeeCells);

        indexCol.setCellValueFactory(new PropertyValueFactory<>("index"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        githubCol.setCellValueFactory(new PropertyValueFactory<>("github"));
        skillsCol.setCellValueFactory(new PropertyValueFactory<>("skills"));
    }

    private ObservableList<ProjectEmployees.EmployeeCell> getEmployeeCells(ObservableList<Employee> employees) {
        ObservableList<ProjectEmployees.EmployeeCell> newList = FXCollections.observableArrayList();
        for (int i = 0; i < employees.size(); i++) {
            ProjectEmployees.EmployeeCell newCell = new ProjectEmployees.EmployeeCell(employees.get(i), i + 1);
            newList.add(newCell);
        }
        return newList;
    }

    /**
     * A cell which contains an employee.
     */
    public class EmployeeCell {

        private Employee employee;
        private Integer index;

        public EmployeeCell(Employee e, int index) {
            this.employee = e;
            this.index = index;
        }

        public String getIndex() {
            return this.index.toString();
        }

        public String getName() {
            return employee.getEmployeeName().fullName;
        }

        public String getGithub() {
            return employee.getGithub().value;
        }

        public String getSkills() {
            Set<Skill> skills = employee.getSkills();
            StringBuilder skillString = new StringBuilder();
            for (Skill skill: skills) {
                skillString.append(skill.skillName);
                skillString.append("  ");
            }
            return skillString.toString();
        }
    }

}
