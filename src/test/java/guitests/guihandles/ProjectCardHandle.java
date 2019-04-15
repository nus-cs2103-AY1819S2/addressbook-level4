package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.project.Project;

/**
 * Provides a handle to a project card in the project list panel.
 */
public class ProjectCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String PROJECTNAME_FIELD_ID = "#name";
    private static final String CLIENT_FIELD_ID = "#client";
    private static final String DEADLINE_FIELD_ID = "#deadline";

    private final Label idLabel;
    private final Label projectNameLabel;
    private final Label clientLabel;
    private final Label deadlineLabel;


    public ProjectCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        projectNameLabel = getChildNode(PROJECTNAME_FIELD_ID);
        clientLabel = getChildNode(CLIENT_FIELD_ID);
        deadlineLabel = getChildNode(DEADLINE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getProjectName() {
        return projectNameLabel.getText();
    }

    public String getClient() {
        return clientLabel.getText();
    }

    public String getDeadline() {
        return deadlineLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code project}.
     */
    public boolean equals(Project project) {
        return getProjectName().equals(project.getProjectName().projectName)
                && getClient().equals(project.getClient().client)
                && getDeadline().equals(project.getDeadline().date);
    }
}
