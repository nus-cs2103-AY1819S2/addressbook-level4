package seedu.address.model.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ALICE_HEY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_ZULU;
import static seedu.address.testutil.TypicalProjects.PROJECT_ALICE;
import static seedu.address.testutil.TypicalProjects.PROJECT_CARL;
import static seedu.address.testutil.TypicalUserStories.USER_STORY_TYPICAL_MANAGER;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.util.PocketProjectDate;
import seedu.address.testutil.ProjectBuilder;

public class ProjectTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addMilestone() {
        //milestone is properly added
        Project p = PROJECT_CARL;
        Milestone m1 = new Milestone(new MilestoneDescription("1"), new PocketProjectDate("11/11/2011"));
        p.addMilestone(m1);
        assertTrue(p.getMilestones().equals(Arrays.asList(m1)));

        //milestone is sorted after being added
        Milestone m2 = new Milestone(new MilestoneDescription("2"), new PocketProjectDate("09/11/2011"));
        p.addMilestone(m2);
        assertTrue(p.getMilestones().equals(Arrays.asList(m2, m1)));
        assertFalse(p.getMilestones().equals(Arrays.asList(m1, m2)));

        //adding more
        Milestone m3 = new Milestone(new MilestoneDescription("3"), new PocketProjectDate("10/11/2011"));
        p.addMilestone(m3);
        assertTrue(p.getMilestones().equals(Arrays.asList(m2, m3, m1)));
    }

    @Test
    public void isValidMilestoneDate() {

        Project testProject = new Project(new ProjectName("Apollo"), new Client("Pegasus Pte Ltd"),
            new PocketProjectDate("20/10/2009"), new PocketProjectDate("22/01/2010"));

        //falls within range
        assertTrue(testProject.isValidMilestoneDate(new PocketProjectDate("22/11/2009")));

        //hits boundary of range
        assertTrue(testProject.isValidMilestoneDate(new PocketProjectDate("20/10/2009")));
        assertTrue(testProject.isValidMilestoneDate(new PocketProjectDate("22/01/2010")));

        //falls outside range
        assertFalse(testProject.isValidMilestoneDate(new PocketProjectDate("22/02/2009")));
        assertFalse(testProject.isValidMilestoneDate(new PocketProjectDate("22/11/2010")));

    }

    @Test
    public void isSameProject() {
        // same object -> returns true
        assertTrue(PROJECT_ALICE.isSameProject(PROJECT_ALICE));

        // null -> returns false
        assertFalse(PROJECT_ALICE.isSameProject(null));

        // different name -> returns false
        Project editedProject = new ProjectBuilder(PROJECT_ALICE).withProjectName(VALID_NAME_BOB)
            .withClient(VALID_CLIENT_ZULU).withStartDate(VALID_START_ZULU).withDeadline(VALID_DEADLINE_ZULU).build();
        assertFalse(PROJECT_ALICE.isSameProject(editedProject));

        //same name -> returns true
        editedProject = new ProjectBuilder(PROJECT_ALICE).withProjectName(VALID_PROJECT_NAME_ALICE_HEY)
            .withClient(VALID_CLIENT_ZULU).withStartDate(VALID_START_ZULU).withDeadline(VALID_DEADLINE_ZULU).build();
        assertTrue(PROJECT_ALICE.isSameProject(editedProject));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Project projectAliceCopy = new ProjectBuilder(PROJECT_ALICE).build();
        assertTrue(PROJECT_ALICE.equals(projectAliceCopy));

        // same object -> returns true
        assertTrue(PROJECT_ALICE.equals(PROJECT_ALICE));

        // null -> returns false
        assertFalse(PROJECT_ALICE.equals(null));

        // different type -> returns false
        assertFalse(PROJECT_ALICE.equals(5));

        // different project -> returns false
        assertFalse(PROJECT_ALICE.equals(PROJECT_CARL));

        // different name -> returns false
        Project editedAlice = new ProjectBuilder(PROJECT_ALICE).withProjectName(VALID_PROJECT_NAME_BOB).build();
        assertFalse(PROJECT_ALICE.equals(editedAlice));

        // different client -> returns false
        editedAlice = new ProjectBuilder(PROJECT_ALICE).withClient(VALID_CLIENT_BOB).build();
        assertFalse(PROJECT_ALICE.equals(editedAlice));

        // different date -> returns false
        editedAlice = new ProjectBuilder(PROJECT_ALICE).withDeadline(VALID_DEADLINE_BOB).build();
        assertFalse(PROJECT_ALICE.equals(editedAlice));

        //different userstory -> returns false
        editedAlice = new ProjectBuilder(PROJECT_ALICE).withUserStories(Arrays.asList(USER_STORY_TYPICAL_MANAGER))
                .build();
        assertFalse(PROJECT_ALICE.equals(editedAlice));
    }
}
