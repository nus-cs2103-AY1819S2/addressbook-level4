package seedu.address.model.project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.UniqueEmployeeList;

/**
 * Represents a project in the pocket project.
 */
public class Project {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final Comparator<String> DATE_STRING_COMPARATOR = new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            int dd1 = Integer.parseInt(s1.substring(0, 2));
            int mm1 = Integer.parseInt(s1.substring(3, 5));
            int yy1 = Integer.parseInt(s1.substring(6, 10));
            int dd2 = Integer.parseInt(s2.substring(0, 2));
            int mm2 = Integer.parseInt(s2.substring(3, 5));
            int yy2 = Integer.parseInt(s2.substring(6, 10));
            if (yy1 != yy2) {
                return yy1 - yy2;
            } else if (mm1 != mm2) {
                return mm1 - mm2;
            } else {
                return dd1 - dd2;
            }
        }
    };
    private final ProjectName projectName;
    private final List<Milestone> milestones;
    private final Client client;
    private final ProjectDate deadline;
    private final UniqueEmployeeList employees;
    private final SortedUserStoryList userStories;
    private final Description description;
    private ProjectDate completionDate = null;

    /**
     * Constructor for each Project Object.
     */
    public Project (ProjectName pn, Client c, ProjectDate d) {
        this(pn, c, d, new ArrayList<>(), new Description(), new UniqueEmployeeList(), new SortedUserStoryList());
    }

    /**
     * Constructor specifying milestones too.
     */
    public Project (ProjectName pn, Client c, ProjectDate d, List<Milestone> m) {
        this(pn, c, d, m, new Description(), new UniqueEmployeeList(), new SortedUserStoryList());
    }

    /**
     * Constructor specifying description and milestone too.
     */
    public Project (ProjectName pn, Client c, ProjectDate d, List<Milestone> m, Description desc) {
        this(pn, c, d, m, desc, new UniqueEmployeeList(), new SortedUserStoryList());
    }

    /**
     * Constructor specifying description.
     */
    public Project (ProjectName pn, Client c, ProjectDate d, Description desc) {
        this(pn, c, d, new ArrayList<>(), desc, new UniqueEmployeeList(), new SortedUserStoryList());
    }

    /**
     * Constructor specifying employees in the project.
     */
    public Project(ProjectName pn, Client c, ProjectDate d, Description desc, UniqueEmployeeList emp) {
        this(pn, c, d, new ArrayList<>(), desc, emp, new SortedUserStoryList());
    }

    /**
     * Constructor specifying all fields except userstories.
     */
    public Project(ProjectName pn, Client c, ProjectDate d, List<Milestone> m, Description desc,
                   UniqueEmployeeList emp) {
        this(pn, c, d, m, desc, emp, new SortedUserStoryList());
    }

    /**
     * Constructor specifying all fields except completion date.
     */
    public Project(ProjectName pn, Client c, ProjectDate d, List<Milestone> m, Description desc, UniqueEmployeeList emp,
                   SortedUserStoryList stories) {
        this.projectName = pn;
        this.client = c;
        this.deadline = d;
        this.description = desc;
        this.employees = emp;
        this.milestones = m;
        this.userStories = stories;
    }

    /**
     * Constructor specifying all fields.
     */
    public Project(ProjectName pn, Client c, ProjectDate d, List<Milestone> m, Description desc, UniqueEmployeeList emp,
                   SortedUserStoryList stories, ProjectDate comp) {
        this.projectName = pn;
        this.client = c;
        this.deadline = d;
        this.description = desc;
        this.employees = emp;
        this.milestones = m;
        this.userStories = stories;
        this.completionDate = comp;
    }

    public ProjectName getProjectName() {
        return projectName;
    }
    public List<Milestone> getMilestones() {
        return milestones;
    }
    public Client getClient() {
        return client;
    }
    public ProjectDate getDeadline() {
        return deadline;
    }
    public Description getDescription() {
        return description;
    }
    public ObservableList<Employee> getEmployees() {
        return employees.asUnmodifiableObservableList();
    }
    public ObservableList<UserStory> getUserStories() {
        return userStories.asUnmodifiableObservableList();
    }

    /**
     * Returns a clone of this Project object.
     */
    public Project clone() {
        List<Milestone> cloneOfMilestones = new ArrayList<>();
        for (Milestone m: this.milestones) {
            cloneOfMilestones.add(m.clone());
        }

        return new Project(this.projectName.clone(), this.client.clone(), this.deadline.clone(),
                cloneOfMilestones,
                this.description.clone(), this.employees.clone(), userStories.clone());
    }

    /**
     * Returns true if both projects have the same name.
     */
    public boolean isSameProject(Project otherProject) {
        if (otherProject == this) {
            return true;
        }

        return otherProject != null
            && otherProject.getProjectName().equals(getProjectName());
    }

    /**
     * Adds the given employee to this project.
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Adds the given milestone to this project.
     */
    public void addMilestone(Milestone milestone) {
        milestones.add(milestone);
        Comparator<? super Milestone> comparator = new Comparator<Milestone>() {
            @Override
            public int compare(Milestone m1, Milestone m2) {
                return DATE_STRING_COMPARATOR.compare(m1.date, m2.date);
            }
        };
        milestones.sort(comparator);
    }

    /**
     * Completes the project, specifying the completion date.
     */
    public void setCompletionDate(ProjectDate completionDate) {
        this.completionDate = completionDate;
    }

    /**
     *  Removes the given employee from this project.
     */
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    /**
     * Removes the given milestone from this project.
     */
    public void removeMilestone(Milestone milestone) {
        milestones.remove(milestone);
    }

    /**
     * Replace the existing employee with the new editedEmployee.
     */
    public void setEmployee(Employee target, Employee editedEmployee) {
        employees.setEmployee(target, editedEmployee);
    }

    /**
     * Returns true if this project has the given projectName.
     */
    public boolean hasProjectName(ProjectName projectName) {
        return this.projectName.equals(projectName);
    }

    /**
<<<<<<< HEAD
     * Adds the given user story to this project.
     */
    public void addUserStory(UserStory story) {
        userStories.add(story);
    }

    /**
     * Removes the given user story from this project.
     */
    public void removeUserStory(UserStory story) {
        userStories.remove(story);
    }

    /**
     * Returns true if this project contains the given Employee.
     */
    public boolean containsEmployee(Employee employee) {
        return employees.containsExactSameEmployee(employee);
    }

    @Override
    public boolean equals (Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Project)) {
            return false;
        }

        Project otherProject = (Project) other;
        return otherProject.getProjectName().equals(getProjectName())
            && otherProject.getClient().equals(getClient())
            && otherProject.getDeadline().equals(getDeadline())
            && otherProject.getDescription().equals(getDescription())
            && otherProject.getMilestones().equals(getMilestones())
            && otherProject.employees.equals(this.employees)
            && otherProject.userStories.equals(this.userStories);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(projectName, client, deadline, description, employees, userStories);
    }

    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder();
        builder.append(getProjectName())
            .append(" Client: ")
            .append(getClient())
            .append(" Deadline: ")
            .append(getDeadline())
            .append(" " + getDescription()).append("\nemployees:\n");
        for (Employee e: employees) {
            builder.append(e);
            builder.append("\n");
        }
        builder.append("milestones:\n");
        for (Milestone m: milestones) {
            builder.append(m);
            builder.append("\n");
        }
        builder.append("user stories:\n");
        for (UserStory s: userStories) {
            builder.append(s);
            builder.append("\n");
        }

        return builder.toString();
    }



}
