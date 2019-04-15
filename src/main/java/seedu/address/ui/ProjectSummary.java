package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.project.Project;

/**
 * Summary of a project
 */
public class ProjectSummary extends UiPart<Region> {

    private static final String FXML = "ProjectSummary.fxml";

    @FXML
    private Label name;

    @FXML
    private Label desc;

    @FXML
    private Label client;

    @FXML
    private Label startDate;

    @FXML
    private Label deadline;


    public ProjectSummary(Project project) {
        super(FXML);
        desc.setText(project.getDescription().description);
        name.setText(project.getProjectName().projectName);
        client.setText(project.getClient().client);
        startDate.setText(project.getStartDate().date);
        deadline.setText(project.getDeadline().date);
    }
}
