package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.model.employee.Employee;

/**
 * Summary of an employee
 */
public class EmployeeSummary extends UiPart<Region> {

    private static final String FXML = "EmployeeSummary.fxml";

    @FXML
    private Pane employeeDetails;

    @FXML
    private Label name;

    @FXML
    private Label projects;

    @FXML
    private Label email;

    @FXML
    private Label phone;

    @FXML
    private Label github;

    @FXML
    private FlowPane skills;



    public EmployeeSummary(Employee employee) {
        super(FXML);
        name.setText(employee.getEmployeeName().fullName);
        phone.setText(employee.getPhone().value);
        email.setText(employee.getEmail().value);
        github.setText(employee.getGithub().value);
        employee.getSkills().forEach(skill -> {
            Label label = new Label(skill.skillName);
            label.getStyleClass().add(SideTabPanel.getSkillLabelColor(skill.skillName));
            skills.getChildren().add(label);
        });
    }
}
