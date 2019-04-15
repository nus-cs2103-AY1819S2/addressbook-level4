package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import seedu.address.model.project.Project;

/**
 * Encapsulates all the information to show about a project
 */
public class ProjectDetails {

    private Project project;
    private List<Node> projectDetailsList;
    private ProjectSummary projectSummary;
    private ProjectUserStories projectUserStories;
    private ProjectEmployees projectEmployees;
    private ProjectMilestones projectMilestones;

    public ProjectDetails(Project project) {
        this.project = project;
        projectDetailsList = new ArrayList<>();
        projectSummary = new ProjectSummary(project);
        projectUserStories = new ProjectUserStories(project.getUserStories());
        projectEmployees = new ProjectEmployees(project.getEmployees());
        projectMilestones = new ProjectMilestones(project.getMilestones());

        projectDetailsList.add(projectSummary.getRoot());
        projectDetailsList.add(projectUserStories.getRoot());
        projectDetailsList.add(projectEmployees.getRoot());
        projectDetailsList.add(projectMilestones.getRoot());
    }

    public List<Node> getProjectDetails() {
        return projectDetailsList;
    }

}
