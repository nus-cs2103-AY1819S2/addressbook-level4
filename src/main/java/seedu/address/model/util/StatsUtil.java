package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.employee.Employee;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectTask;

/**
 * Provides static methods for looking through the list of projects/employees/single project for
 * information. Returns the result as a string contain the information.
 */
public class StatsUtil {
    /**
     * Returns a string describing the overall state of all the projects.
     */
    public static String overAllStatsString(List<Employee> employees, List<Project> currentProjects,
                                            List<Project> completedProjects) {
        StringBuilder builder = new StringBuilder();
        builder.append(ongoingProjects(currentProjects));
        builder.append(currentProjects(currentProjects));
        return builder.toString();
    }

    /**
     * Returns a string describing the total number of ongoing projects.
     */
    public static String ongoingProjects(List<Project> projects) {
        int number = projects.size();
        return "Number of ongoing projects: " + number + "\n";
    }

    /**
     * Look through all the projects and return a string describing all the projects' progress.
     */
    public static String currentProjects(List<Project> projects) {
        StringBuilder builder = new StringBuilder();
        builder.append("Projects with deadline in this month:\n------------------------\n");
        ArrayList<Project> clone = new ArrayList<>(projects);
        Comparator<Project> comparator = new Comparator<Project>() {
            @Override
            public int compare(Project o1, Project o2) {
                return -PocketProjectDate.DATE_STRING_COMPARATOR.compare(o1.getDeadline().date, o2.getDeadline().date);
            }
        };
        clone.sort(comparator);
        int count = 1;
        for (Project p : clone) {
            if (PocketProjectDate.isThisMonth(p.getDeadline())) {
                builder.append(count + ". " + p.getProjectName().projectName + " | " + roughProgress(p) + "\n");
                count++;
            }
        }
        builder.append("------------------------\n");
        for (Project project : clone) {
            if (!PocketProjectDate.isThisMonth(project.getDeadline())) {
                builder.append(count + ". " + project.getProjectName().projectName + " | " + roughProgress(project)
                        + "\n");
                count++;
            }
        }
        return builder.toString();
    }

    /**
     * Returns a string describing the rough progress of a project.
     */
    public static String roughProgress(Project project) {
        StringBuilder builder = new StringBuilder();
        int numMilestonesReached = 0;
        for (Milestone m: project.getMilestones()) {
            if (m.reached()) {
                numMilestonesReached++;
            }
        }
        int numTotalMilestones = project.getMilestones().size();
        builder.append(String.format("deadline: %s | reached %d out of %d milestones",
                project.getDeadline().date, numMilestonesReached, numTotalMilestones));
        return builder.toString();
    }

    /**
     * Returns a string describing the progress of an individual project.
     */
    public static String individualStatsString(Project project) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Progress of %s:\n", project.getProjectName().projectName));
        builder.append(roughProgress(project) + "\n");
        builder.append("Milestones not reached yet:\n");
        int count = 1;
        for (Milestone m: project.getMilestones()) {
            if (!m.reached()) {
                builder.append(count + ". " + milestoneProgress(m) + "\n");
                count++;
            }
        }
        return builder.toString();
    }
    /**
     * Returns a string describing the rough progress of a milestone.
     */
    public static String milestoneProgress(Milestone milestone) {
        int numTask = milestone.getProjectTaskList().size();
        int numCompletedTasks = 0;
        for (ProjectTask pt: milestone.getProjectTaskList()) {
            if (pt.isComplete()) {
                numCompletedTasks++;
            }
        }
        return String.format(milestone.getMilestoneDescription().description + " | " + "deadline: "
                        + milestone.getDate().date
                        + " | %d out of %d tasks completed.",
                numCompletedTasks, numTask);
    }

}
