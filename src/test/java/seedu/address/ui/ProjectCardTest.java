package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysProject;

import org.junit.Test;

import guitests.guihandles.ProjectCardHandle;
import seedu.address.model.project.Project;
import seedu.address.testutil.ProjectBuilder;

public class ProjectCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no milestones
        Project projectWithNoMilestones = new ProjectBuilder().build();
        ProjectCard projectCard = new ProjectCard(projectWithNoMilestones, 1);
        uiPartRule.setUiPart(projectCard);
        assertCardDisplay(projectCard, projectWithNoMilestones, 1);

    }

    @Test
    public void equals() {

        Project project = new ProjectBuilder().build();
        ProjectCard projectCard = new ProjectCard(project, 0);

        //same project, same index -> returns true
        ProjectCard copy = new ProjectCard(project, 0);
        assertTrue(projectCard.equals(copy));

        //same object -> return true
        assertTrue(projectCard.equals(projectCard));

        //null -> returns false
        assertFalse(projectCard.equals(null));

        //different types -> returns false
        assertFalse(projectCard.equals(0));

        //different project, same index -> returns false
        Project differentProject = new ProjectBuilder().withProjectName("differentName").build();
        assertFalse(projectCard.equals(new ProjectCard(differentProject, 0)));

        //same project, different index -> returns false
        assertFalse(projectCard.equals(new ProjectCard(project, 1)));
    }

    /**
     * Asserts that {@code projectCard} displays the details of {@code expectedProject} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ProjectCard projectCard, Project expectedProject, int expectedId) {
        guiRobot.pauseForHuman();

        ProjectCardHandle projectCardHandle = new ProjectCardHandle(projectCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", projectCardHandle.getId());

        // verify project details are displayed correctly
        assertCardDisplaysProject(expectedProject, projectCardHandle);
    }
}
