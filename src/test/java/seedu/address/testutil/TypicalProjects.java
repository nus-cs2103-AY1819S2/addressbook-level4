package seedu.address.testutil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PocketProject;
import seedu.address.model.project.Client;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.util.PocketProjectDate;

/**
 * A utility class containing a list of {@code Project} objects to be used in tests.
 */
public class TypicalProjects {


    public static final Project PROJECT_ALICE = new ProjectBuilder().withProjectName("Project Alice hey")
            .withClient("Dehui").withStartDate("10/10/2010").withDeadline("11/02/2019")
            .withDescription("An application for Alice software hello")
            .withEmployees(Arrays.asList(TypicalEmployees.BENSON, TypicalEmployees.CARL))
            .withMilestones(Arrays.asList(TypicalMilestones.TYPICAL_MILESTONE_START,
                    TypicalMilestones.TYPICAL_MILESTONE_END))
            .withUserStories(Arrays.asList(TypicalUserStories.USER_STORY_FIRST_MANAGER,
                    TypicalUserStories.USER_STORY_SECOND_MANAGER)).build();


    public static final Project PROJECT_BENSON = new ProjectBuilder().withProjectName("Project Benson")
        .withClient("Jeff software").withStartDate("11/10/2010").withDeadline("23/01/2011")
        .withDescription("An application for Benson")
        .withEmployees(Arrays.asList(TypicalEmployees.CARL, TypicalEmployees.DANIEL))
        .withMilestones(Arrays.asList(TypicalMilestones.TYPICAL_MILESTONE_START,
            TypicalMilestones.TYPICAL_MILESTONE_END))
        .withUserStories(Arrays.asList(TypicalUserStories.USER_STORY_SECOND_MANAGER)).build();

    public static final Project PROJECT_CARL = new ProjectBuilder().withProjectName("Project Carl hey")
        .withClient("Darryl").withStartDate("12/10/2010").withDeadline("12/12/2012")
        .withDescription("An application for Carl hello")
        .withEmployees(Arrays.asList(TypicalEmployees.DANIEL, TypicalEmployees.ELLE)).build();

    public static final Project PROJECT_DANIEL = new ProjectBuilder().withProjectName("Project Daniel")
        .withClient("Shune").withStartDate("13/10/2010").withDeadline("21/12/2012")
        .withDescription("An application for Daniel software")
        .withEmployees(Arrays.asList(TypicalEmployees.ELLE, TypicalEmployees.FIONA)).build();


    public static final Project PROJECT_ELLE = new ProjectBuilder().withProjectName("Project Elle").withClient("Jothi")
        .withStartDate("14/10/2010").withDeadline("22/03/2019").withDescription("An application for Elle")
        .withEmployees(Arrays.asList(TypicalEmployees.FIONA, TypicalEmployees.GEORGE)).build();

    public static final Project PROJECT_FIONA = new ProjectBuilder().withProjectName("Project Fiona").withClient("SOC")
        .withStartDate("15/10/2010").withDeadline("01/01/2020").withDescription("An application for Fiona")
        .withEmployees(Arrays.asList(TypicalEmployees.GEORGE, TypicalEmployees.ALICE)).build();

    public static final Project PROJECT_GEORGE = new ProjectBuilder()
        .withProjectName("Project George").withClient("FASS")
        .withStartDate("16/10/2010").withDeadline("11/02/2021").withDescription("An application for George")
        .withEmployees(Arrays.asList(TypicalEmployees.ALICE, TypicalEmployees.BENSON)).build();


    // Manually added
    public static final Project PROJECT_HOON = new ProjectBuilder()
        .withProjectName("Project Hoon Meier").withClient("FOE")
        .withStartDate("17/10/2010").withDeadline("21/11/2018").withDescription("An application for Hoon Meir").build();

    public static final Project PROJECT_IDA = new ProjectBuilder()
        .withProjectName("Project Ida Mueller").withClient("FOS")
        .withStartDate("18/10/2010").withDeadline("03/03/2013")
        .withDescription("An application for Ida Mueller").build();

    public static final Project PROJECT_VICTOR = new Project(new ProjectName("Victor"), new Client("Jipple Os"),
        new PocketProjectDate("02/11/2009"), new PocketProjectDate("04/10/2011"));

    public static final Project PROJECT_WHISKEY = new Project(new ProjectName("Whiskey"), new Client("Curry S"),
        new PocketProjectDate("06/10/2010"), new PocketProjectDate("10/10/2010"));

    public static final Project PROJECT_XAVIER = new Project(new ProjectName("Xavier"), new Client("JJ Pte Ltd"),
        new PocketProjectDate("07/10/2010"), new PocketProjectDate("02/03/2033"));

    public static final Project PROJECT_YANKEE = new Project(new ProjectName("Yankee"), new Client("Pang Po"),
        new PocketProjectDate("08/10/2010"), new PocketProjectDate("13/12/2011"));

    public static final Project PROJECT_ZULU = new Project(new ProjectName("Zulu"), new Client("Shunnizuka"),
        new PocketProjectDate("09/10/2010"), new PocketProjectDate("09/04/2019"));
    /*
    // Manually added - Project's details found in {@code CommandTestUtil}
    public static final Project AMY = new ProjectBuilder().withProjectName(VALID_NAME_AMY).withClient(VALID_PHONE_AMY)
            .withDeadline(VALID_EMAIL_AMY).withClient(VALID_GITHUB_AMY).withSkills(VALID_SKILL_C).build();
    public static final Project BOB = new ProjectBuilder().withProjectName(VALID_NAME_BOB).withClient(VALID_PHONE_BOB)
            .withDeadline(VALID_EMAIL_BOB).withClient(VALID_GITHUB_BOB)
            .withSkills(VALID_SKILL_JAVA, VALID_SKILL_C).build();
    */
    public static final String KEYWORD_MATCHING_YANKEE = "Yankee"; // A keyword that matches YANKEE

    private TypicalProjects() {} // prevents instantiation

    /**
     * Returns an {@code PocketProject} with all the typical projects.
     */
    public static PocketProject getTypicalPocketProjectWithProjects() {
        PocketProject ab = new PocketProject();
        for (Project project : getTypicalProjects()) {
            ab.addProject(project);
        }
        return ab;
    }

    /**
     * Returns an {@code PocketProject} with all the typical projects added to the given {@code PocketProject}.
     */
    public static PocketProject addTypicalProjects(PocketProject ab) {
        for (Project project : getTypicalProjects()) {
            ab.addProject(project);
        }
        return ab;
    }

    public static List<Project> getTypicalProjects() {
        return new ArrayList<>(Arrays.asList(PROJECT_ALICE, PROJECT_BENSON, PROJECT_CARL,
            PROJECT_DANIEL, PROJECT_ELLE, PROJECT_FIONA, PROJECT_GEORGE));
    }
}
