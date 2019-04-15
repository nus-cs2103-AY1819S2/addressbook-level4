package seedu.address.testutil;

import java.util.List;

import seedu.address.model.employee.Employee;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.project.Client;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectDescription;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.SortedUserStoryList;
import seedu.address.model.project.UniqueMilestoneList;
import seedu.address.model.project.UserStory;
import seedu.address.model.util.PocketProjectDate;

/**
 * A utility class to help with building Project objects.
 */
public class ProjectBuilder {

    public static final String DEFAULT_PROJECT_NAME = "Apollo";
    public static final String DEFAULT_START_DATE = "22/01/2019";
    public static final String DEFAULT_DEADLINE = "03/03/2019";
    public static final String DEFAULT_CLIENT = "NUS";
    public static final String DEFAULT_DESCRIPTION = "This project currently has no description. Kindly use "
        + "the edit command to change this project's description";


    private ProjectName projectName;
    private PocketProjectDate startDate;
    private PocketProjectDate deadline;
    private Client client;
    private ProjectDescription description;
    private UniqueEmployeeList employees;
    private UniqueMilestoneList milestones;
    private SortedUserStoryList userStories;

    public ProjectBuilder() {
        projectName = new ProjectName(DEFAULT_PROJECT_NAME);
        startDate = new PocketProjectDate(DEFAULT_START_DATE);
        deadline = new PocketProjectDate(DEFAULT_DEADLINE);
        client = new Client(DEFAULT_CLIENT);
        description = new ProjectDescription((DEFAULT_DESCRIPTION));
        employees = new UniqueEmployeeList();
        milestones = new UniqueMilestoneList();
        userStories = new SortedUserStoryList();
    }

    /**
     * Initializes the ProjectBuilder with the data of {@code projectToCopy}.
     */
    public ProjectBuilder(Project projectToCopy) {
        projectName = projectToCopy.getProjectName();
        startDate = projectToCopy.getStartDate();
        deadline = projectToCopy.getDeadline();
        client = projectToCopy.getClient();
        description = projectToCopy.getDescription();
        employees = new UniqueEmployeeList();
        this.milestones = new UniqueMilestoneList();
        this.userStories = new SortedUserStoryList();
        for (Employee e: projectToCopy.getEmployees()) {
            employees.add(e);
        }
        for (Milestone m: projectToCopy.getMilestones()) {
            milestones.add(m);
        }
        for (UserStory u: projectToCopy.getUserStories()) {
            userStories.add(u);
        }
    }

    /**
     * Sets the {@code ProjectName} of the {@code Project} that we are building.
     */
    public ProjectBuilder withProjectName(String projectName) {
        this.projectName = new ProjectName(projectName);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code Project} that we are building.
     */
    public ProjectBuilder withStartDate(String startDate) {
        this.startDate = new PocketProjectDate(startDate);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Project} that we are building.
     */
    public ProjectBuilder withDeadline(String deadline) {
        this.deadline = new PocketProjectDate(deadline);
        return this;
    }

    /**
     * Sets the {@code Client} of the {@code Project} that we are building.
     */
    public ProjectBuilder withClient(String client) {
        this.client = new Client(client);
        return this;
    }

    /**
     * Sets the {@code ProjectDescription} of the {@code Project} that we are building.
     */
    public ProjectBuilder withDescription(String desc) {
        this.description = new ProjectDescription(desc);
        return this;
    }
    /**
     * Sets the milestones of the {@code Project} that we are building.
     */
    public ProjectBuilder withMilestones(List<Milestone> milestones) {
        this.milestones.setMilestones(milestones);
        return this;
    }
    /**
     * Sets the employees of the {@code Project} that we are building.
     */
    public ProjectBuilder withEmployees(List<Employee> employees) {
        this.employees.setEmployees(employees);
        return this;
    }

    /**
     * Sets the user stories of the {@code Project} that we are building.
     */
    public ProjectBuilder withUserStories(List<UserStory> stories) {
        this.userStories.setUserStories(stories);
        return this;
    }

    /**
     * Build the components into a {@code Project}
     */
    public Project build() {
        return new Project(projectName, client, startDate, deadline, milestones, description, employees,
                userStories);
    }
}
