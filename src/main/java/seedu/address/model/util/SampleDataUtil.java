package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.PocketProject;
import seedu.address.model.ReadOnlyPocketProject;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.GitHubAccount;
import seedu.address.model.employee.Phone;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.project.Client;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.MilestoneDescription;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectDescription;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.ProjectTask;
import seedu.address.model.project.ProjectTaskDescription;
import seedu.address.model.project.SortedUserStoryList;
import seedu.address.model.project.Status;
import seedu.address.model.project.UniqueMilestoneList;
import seedu.address.model.project.UniqueProjectTaskList;
import seedu.address.model.project.UserStory;
import seedu.address.model.project.UserStoryFunction;
import seedu.address.model.project.UserStoryImportance;
import seedu.address.model.project.UserStoryReason;
import seedu.address.model.project.UserStoryUser;
import seedu.address.model.skill.Skill;

/**
 * Contains utility methods for populating {@code PocketProject} with sample data.
 */
public class SampleDataUtil {
    public static Employee[] getSampleEmployees() {
        return new Employee[] {
            new Employee(new EmployeeName("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new GitHubAccount("yeohyeoh"),
                getSkillSet("C", "Java")),
            new Employee(new EmployeeName("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new GitHubAccount("bernthemall"),
                getSkillSet("SQL", "Ruby")),
            new Employee(new EmployeeName("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"), new GitHubAccount("thespiderweb"),
                    getSkillSet("Haskell")),
            new Employee(new EmployeeName("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new GitHubAccount("liddavid"),
                getSkillSet("JavaScript")),
            new Employee(new EmployeeName("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new GitHubAccount("relaxandcode"),
                getSkillSet("CSS", "REACT")),
            new Employee(new EmployeeName("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new GitHubAccount("chindianattacks"),
                getSkillSet("html")),
            new Employee(new EmployeeName("Shune Lei"), new Phone("93427846"), new Email("shunelei@gmail.com"),
                new GitHubAccount("shunnizuka"),
                getSkillSet("Java", "C", "SQL")),
            new Employee(new EmployeeName("Jeff Gan"), new Phone("96313111"), new Email("jeffganmr@hotmail.com"),
                new GitHubAccount("jeffgan96"),
                getSkillSet("JavaFX", "CSS", "html")),
            new Employee(new EmployeeName("Daryl Tan"), new Phone("91234321"), new Email("daryltan@yahoo.com"),
                new GitHubAccount("ditan96"),
                getSkillSet("Java", "C", "REACT", "Python")),
            new Employee(new EmployeeName("Jo Jo Joget"), new Phone("81886875"), new Email("joget@gmail.com"),
                new GitHubAccount("jojothejetplane"),
                getSkillSet("Javascript", "html")),
            new Employee(new EmployeeName("De Hui"), new Phone("98782231"), new Email("dedehui@gmail.com"),
                new GitHubAccount("dehui333"),
                getSkillSet("Java", "C", "Javascript")),
        };
    }

    public static Project[] getSampleProjects() {

        Status complete = new Status("complete");
        Status onHold = new Status("on hold");
        ProjectTask taskA = new ProjectTask(new ProjectTaskDescription("Task a"));
        ProjectTask taskB = new ProjectTask(new ProjectTaskDescription("Task b"));
        ProjectTask taskC = new ProjectTask(new ProjectTaskDescription("Task c"));
        ProjectTask taskD = new ProjectTask(new ProjectTaskDescription("Task d"));
        ProjectTask taskAComplete = new ProjectTask(new ProjectTaskDescription("Task a"), new Status("complete"));
        ProjectTask taskBComplete = new ProjectTask(new ProjectTaskDescription("Task b"), new Status("complete"));
        ProjectTask taskCComplete = new ProjectTask(new ProjectTaskDescription("Task c"), new Status("complete"));
        UniqueProjectTaskList taskList1 = new UniqueProjectTaskList();
        taskList1.setProjectTasks(Arrays.asList(taskA.clone(), taskB.clone(), taskC.clone()));
        UniqueProjectTaskList taskList2 = new UniqueProjectTaskList();
        taskList2.setProjectTasks(Arrays.asList(taskAComplete.clone(), taskBComplete.clone(), taskCComplete.clone()));
        UniqueProjectTaskList taskList3 = new UniqueProjectTaskList();
        taskList3.setProjectTasks(Arrays.asList(taskA.clone(), taskB.clone(), taskC.clone(), taskD.clone()));

        Milestone m1 = new Milestone(new MilestoneDescription("v1.1"), new PocketProjectDate("18/04/2019"),
                taskList2.clone());
        Milestone m2 = new Milestone(new MilestoneDescription("v1.2"), new PocketProjectDate("25/04/2019"),
                taskList1.clone());
        Milestone m3 = new Milestone(new MilestoneDescription("v1.3"), new PocketProjectDate("05/05/2019"),
                taskList3.clone());
        Milestone m4 = new Milestone(new MilestoneDescription("v1.4"), new PocketProjectDate("25/05/2019"),
                taskList1.clone());
        Milestone m5 = new Milestone(new MilestoneDescription("v1.1"), new PocketProjectDate("18/04/2019"),
                taskList1.clone());
        Milestone m6 = new Milestone(new MilestoneDescription("v1.2"), new PocketProjectDate("25/04/2019"),
                taskList3.clone());
        Milestone m7 = new Milestone(new MilestoneDescription("v1.3"), new PocketProjectDate("05/05/2019"),
                taskList1.clone());
        UniqueMilestoneList milestoneList = new UniqueMilestoneList();
        milestoneList.setMilestones(Arrays.asList(m1.clone(), m2.clone(), m3.clone(), m4.clone()));
        UniqueMilestoneList milestoneList1 = new UniqueMilestoneList();
        milestoneList1.setMilestones(Arrays.asList(m5.clone(), m6.clone(), m7.clone()));
        UniqueMilestoneList milestoneList2 = new UniqueMilestoneList();
        milestoneList2.setMilestones(Arrays.asList(m1.clone(), m6.clone(), m3.clone()));

        UserStory us1 = new UserStory(new UserStoryImportance("3"), new UserStoryUser("A Typical manager"),
                new UserStoryFunction("add projects"), new UserStoryReason("keep a record of my projects"));
        UserStory us2 = new UserStory(new UserStoryImportance("2"),
                new UserStoryUser("A Typical manager"),
                new UserStoryFunction("see my projects"),
                new UserStoryReason("better track my projects progress"));
        UserStory us3 = new UserStory(new UserStoryImportance("2"),
                new UserStoryUser("A Typical manager"),
                new UserStoryFunction("find employees by skill"),
                new UserStoryReason("easily assign employees to the project"));
        UserStory us4 = new UserStory(new UserStoryImportance("3"),
                new UserStoryUser("A user"),
                new UserStoryFunction("order online"),
                new UserStoryReason("get a convenient meal"));
        UserStory us5 = new UserStory(new UserStoryImportance("2"),
                new UserStoryUser("A user"),
                new UserStoryFunction("see what the menu has"),
                new UserStoryReason("decide what to get"));
        SortedUserStoryList userStoryList = new SortedUserStoryList();
        userStoryList.setUserStories(Arrays.asList(us1.clone(), us2.clone(), us3.clone()));
        SortedUserStoryList userStoryList1 = new SortedUserStoryList();
        userStoryList1.setUserStories(Arrays.asList(us4.clone(), us5.clone()));

        return new Project[] {
            new Project(new ProjectName("Moonlight"), new Client("Hipster Pte Ltd"),
                new PocketProjectDate("13/04/2019"), new PocketProjectDate("31/05/2019"),
                milestoneList.clone(), new ProjectDescription("Purple light in the night"), new UniqueEmployeeList(),
                userStoryList.clone()),
            new Project(new ProjectName("KFC Online Bot"), new Client("KFC"),
                new PocketProjectDate("28/05/2019"), new PocketProjectDate("25/11/2019"),
                milestoneList1.clone(), new ProjectDescription("Making online deliveries one click away!"),
                new UniqueEmployeeList(), userStoryList1.clone()),
            new Project(new ProjectName("Project Taka"), new Client("Noppo"),
                new PocketProjectDate("12/08/2019"), new PocketProjectDate("22/03/2020"),
                milestoneList2.clone()),
            new Project(new ProjectName("Automatica"), new Client("Robo2B"),
                new PocketProjectDate("20/10/2019"), new PocketProjectDate("10/06/2020")),
            new Project(new ProjectName("Sublime Exploration"), new Client("New worlds"),
                new PocketProjectDate("20/01/2020"), new PocketProjectDate("05/09/2020"))
        };
    }

    public static ReadOnlyPocketProject getSamplePocketProject() {
        PocketProject samplePp = new PocketProject();
        Employee[] employees = getSampleEmployees();
        for (Project sampleProject : getSampleProjects()) {
            for (int i = 0; i < employees.length; i++) {
                if (Math.random() < 0.5) {
                    Employee editedEmployee = employees[i].clone();
                    sampleProject.addEmployee(editedEmployee);
                    editedEmployee.join(sampleProject);
                    employees[i] = editedEmployee;
                }
            }
            samplePp.addProject(sampleProject);
        }
        for (Employee sampleEmployee : employees) {
            samplePp.addEmployee(sampleEmployee);
        }
        return samplePp;
    }

    /**
     * Returns a skill set containing the list of strings given.
     */
    public static Set<Skill> getSkillSet(String... strings) {
        return Arrays.stream(strings)
                .map(Skill::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a list of project names containing the strings given.
     */
    public static List<ProjectName> getProjectNamesList(String... strings) {
        return Arrays.stream(strings).map(ProjectName::new).collect(Collectors.toList());
    }
}
