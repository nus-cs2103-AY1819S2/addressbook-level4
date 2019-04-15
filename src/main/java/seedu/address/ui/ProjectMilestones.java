package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.ProjectTask;
import seedu.address.model.project.Status;

/**
 * UI component to represent employees in a project
 */
public class ProjectMilestones extends UiPart<Region> {

    public static final String FXML = "ProjectMilestones.fxml";

    public static final String STATUS_COLOUR_RED = "redCircle";
    public static final String STATUS_COLOUR_GREEN = "greenCircle";
    public static final String STATUS_COLOUR_ORANGE = "orangeCircle";
    public static final String STATUS_COLOUR_BLACK = "blackCircle";

    private ObservableList<Milestone> milestones;

    @FXML
    private TreeView<String> milestonesTreeView;

    public ProjectMilestones(ObservableList<Milestone> milestones) {
        super(FXML);
        this.milestones = milestones;

        TreeItem<String> root = new TreeItem<>();
        root.setExpanded(true);
        addMilestoneBranches(root);

        milestonesTreeView.setRoot(root);
        milestonesTreeView.setShowRoot(false);
    }

    /**
     * Adds existing milestones into the tree view with root as the parent.
     */
    public void addMilestoneBranches(TreeItem<String> root) {
        int index = 1;
        for (Milestone m: milestones) {
            String mInfo = index + ". " + m.getMilestoneDescription() + " (" + m.getDate() + ")";
            TreeItem<String> item = setTreeItem(mInfo, root);
            addTaskLeaves(item, m);
            index++;
        }
    }

    /**
     * Adds existing project tasks in {@code m} into the tree view with branch as the parent.
     */
    public void addTaskLeaves(TreeItem<String> branch, Milestone m) {
        ObservableList<ProjectTask> projectTaskList = m.getProjectTaskList();
        int index = 1;
        for (ProjectTask pt: projectTaskList) {
            Node taskIcon = instantiateColour(pt.getTaskStatus());
            String ptInfo = index + ". " + pt.getTaskStatus() + ": " + pt.getTaskDescription();
            TreeItem<String> leaf = setTreeItem(ptInfo, branch);
            leaf.setGraphic(taskIcon);
            index++;
        }
    }

    public TreeItem<String> setTreeItem(String itemName, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(itemName);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    /**
     * Instantiates a colour of the circle by using the hashcode value of {@code status}.
     */
    public Circle instantiateColour(String status) {
        Circle circle = new Circle(0, 0, 5);

        switch (status) {
        case Status.STATUS_COMPLETE:
            circle.setId(STATUS_COLOUR_GREEN);
            break;
        case Status.STATUS_ON_HOLD:
            circle.setId(STATUS_COLOUR_RED);
            break;
        case Status.STATUS_ONGOING:
            circle.setId(STATUS_COLOUR_ORANGE);
            break;
        default:
            circle.setId(STATUS_COLOUR_BLACK);
        }
        return circle;
    }
}
