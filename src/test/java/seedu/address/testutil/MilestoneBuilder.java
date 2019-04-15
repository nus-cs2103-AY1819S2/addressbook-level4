package seedu.address.testutil;

import java.util.List;

import seedu.address.model.project.Milestone;
import seedu.address.model.project.MilestoneDescription;
import seedu.address.model.project.ProjectTask;
import seedu.address.model.project.UniqueProjectTaskList;
import seedu.address.model.util.PocketProjectDate;

/**
 * A utility class to help with building Milestone objects.
 */
public class MilestoneBuilder {

    public static final String DEFAULT_MILESTONE_DESC = "v1.1";
    public static final String DEFAULT_DEADLINE = "01/02/2019";

    private MilestoneDescription milestoneDesc;
    private PocketProjectDate deadline;
    private UniqueProjectTaskList projectTasks;

    public MilestoneBuilder() {
        this.milestoneDesc = new MilestoneDescription(DEFAULT_MILESTONE_DESC);
        this.deadline = new PocketProjectDate(DEFAULT_DEADLINE);
        this.projectTasks = new UniqueProjectTaskList();
    }

    /**
     * Initializes the MilestoneBuilder with the data of {@code milestoneToCopy}.
     */
    public MilestoneBuilder(Milestone milestoneToCopy) {
        this.milestoneDesc = new MilestoneDescription(milestoneToCopy.getMilestoneDescription().description);
        this.deadline = new PocketProjectDate(milestoneToCopy.getDate().getDate());
        this.projectTasks = new UniqueProjectTaskList();
        for (ProjectTask pt: milestoneToCopy.getProjectTaskList()) {
            this.projectTasks.add(pt);
        }
    }

    /**
     * Sets the {@code MilestoneDescription} of the {@code Milestone} that we are building.
     */
    public MilestoneBuilder withDescription(String description) {
        this.milestoneDesc = new MilestoneDescription(description);
        return this;
    }

    /**
     * Sets the {@code MilestoneDeadline} of the {@code Milestone} that we are building.
     */
    public MilestoneBuilder withDeadline(String deadline) {
        this.deadline = new PocketProjectDate(deadline);
        return this;
    }

    /**
     * Sets the milestones of the {@code Project} that we are building.
     */
    public MilestoneBuilder withProjectTasks(List<ProjectTask> projectTasks) {
        this.projectTasks.setProjectTasks(projectTasks);
        return this;
    }

    public Milestone build() {
        return new Milestone(milestoneDesc, deadline, projectTasks);
    }

}
