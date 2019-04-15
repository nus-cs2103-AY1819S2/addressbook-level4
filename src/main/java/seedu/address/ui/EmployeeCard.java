package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.employee.Employee;

/**
 * An UI component that displays information of a {@code Employee}.
 */
public class EmployeeCard extends UiPart<Region> {

    private static final String FXML = "EmployeeListCard.fxml";
    private static final String GITHUB_PREFIX = "GitHub: ";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/pocketproject/issues/336">The issue on PocketProject level 4</a>
     */

    public final Employee employee;

    @FXML
    private HBox cardPane;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private Label github;

    @FXML
    private FlowPane skills;

    public EmployeeCard(Employee employee, int displayedIndex) {
        super(FXML);
        this.employee = employee;
        id.setText(displayedIndex + ". ");
        name.setText(employee.getEmployeeName().fullName);
        github.setText(employee.getGithub().value);
        phone.setText(employee.getPhone().value);
        email.setText(employee.getEmail().value);
        employee.getSkills().forEach(skill -> {
            Label label = new Label(skill.skillName);
            label.getStyleClass().add(SideTabPanel.getSkillLabelColor(skill.skillName));
            skills.getChildren().add(label);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmployeeCard)) {
            return false;
        }

        // state check
        EmployeeCard card = (EmployeeCard) other;
        return id.getText().equals(card.id.getText())
                && employee.equals(card.employee);
    }
}
