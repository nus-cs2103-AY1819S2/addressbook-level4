package seedu.address.model.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalProjectTasks.PROJECT_TASK_COMPLETED;
import static seedu.address.testutil.TypicalProjectTasks.PROJECT_TASK_DO_SOMETHING;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ProjectTaskTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ProjectTask(null));
        Assert.assertThrows(NullPointerException.class, () -> new ProjectTask(null, null));
    }

    @Test
    public void constructor_invalidProjectTask_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new ProjectTask(new ProjectTaskDescription(""))); // empty string
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new ProjectTask(new ProjectTaskDescription(" "))); // spaces only

        // invalid tasks with task status constructor
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new ProjectTask(new ProjectTaskDescription(""), new Status(""))); // empty string
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new ProjectTask(new ProjectTaskDescription(""), new Status(" "))); // spaces only
    }

    @Test
    public void isValidProjectTask() {
        // invalid task null status
        Assert.assertThrows(NullPointerException.class, () ->
                new ProjectTask(new ProjectTaskDescription("a"), new Status(null)));

        //invalid task status strings
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new ProjectTask(new ProjectTaskDescription("a"), new Status(""))); // empty string
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new ProjectTask(new ProjectTaskDescription("a"), new Status(" "))); // spaces only
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new ProjectTask(new ProjectTaskDescription("a"), new Status("complet"))); // wrong status string
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new ProjectTask(new ProjectTaskDescription("a"), new Status("ongoin"))); // wrong status string

        // valid task
        assertTrue(ProjectTask.isValidTask(new ProjectTask(new ProjectTaskDescription("a"))));

        assertTrue(ProjectTask.isValidTask(new ProjectTask(
                new ProjectTaskDescription("a"),
                new Status("ongoing")))); // include task status constructor
        assertTrue(ProjectTask.isValidTask(new ProjectTask(
                new ProjectTaskDescription(" Create a task"),
                new Status("ongoing")))); // task name with whitespace at start of string
        assertTrue(ProjectTask.isValidTask(new ProjectTask(
                new ProjectTaskDescription("Create a task "),
                new Status("complete")))); // task name with trailing whitespace
        assertTrue(ProjectTask.isValidTask(new ProjectTask(
                new ProjectTaskDescription("New task at pp/v1.1"),
                new Status("complete")))); // task name with non-alphanumeric characters
        assertTrue(ProjectTask.isValidTask(new ProjectTask(
                new ProjectTaskDescription("New task at pp/v1.1"),
                new Status("CoMpLeTe")))); // case insensitive task status
        assertTrue(ProjectTask.isValidTask(new ProjectTask(
                new ProjectTaskDescription("New task at pp/v1.1"),
                new Status("OnGoInG")))); // case insensitive task status
    }

    @Test
    public void isSameProjectTaskDescription() {
        // equals
        ProjectTaskDescription validProjectTaskDescription = new ProjectTaskDescription("Valid");
        ProjectTask validProjectTask = new ProjectTask(new ProjectTaskDescription("Valid"),
                new Status("ongoing"));
        assertEquals(validProjectTask.getTaskDescription(), validProjectTaskDescription.getTaskDescription());

        // not equals, case-sensitive
        ProjectTask invalidProjectTask = new ProjectTask(new ProjectTaskDescription("valid"),
                new Status("ongoing"));
        assertNotEquals(invalidProjectTask.getTaskDescription(), validProjectTaskDescription.getTaskDescription());
    }

    @Test
    public void isSameStatus() {
        // same string -> equals
        Status status = new Status("ongoing");
        ProjectTask validProjectTask = new ProjectTask(new ProjectTaskDescription("Valid"),
                new Status("ongoing"));
        assertEquals(validProjectTask.getTaskStatus(), status.getStatus());

        // status case insensitive -> equals
        ProjectTask caseProjectTask = new ProjectTask(new ProjectTaskDescription("Valid"),
                new Status("Ongoing"));
        assertEquals(caseProjectTask.getTaskStatus(), status.getStatus());

        // different statuses -> not equals
        ProjectTask invalidProjectTask = new ProjectTask(new ProjectTaskDescription("Valid"),
                new Status("Complete"));
        assertNotEquals(invalidProjectTask.getTaskStatus(), status.getStatus());

        // update status to match -> equals
        invalidProjectTask.updateStatus(new Status("ongoing"));
        assertEquals(invalidProjectTask.getTaskStatus(), status.getStatus());
    }

    @Test
    public void isSameTask() {
        // same object -> return true
        assertTrue(PROJECT_TASK_DO_SOMETHING.isSameTask(PROJECT_TASK_DO_SOMETHING));

        // null -> return false
        assertFalse(PROJECT_TASK_DO_SOMETHING.isSameTask(null));

        // different project task -> returns false
        assertFalse(PROJECT_TASK_DO_SOMETHING.isSameTask(PROJECT_TASK_COMPLETED));
    }

    @Test
    public void equals() {
        // same values -> return true
        ProjectTask typicalCopy = PROJECT_TASK_DO_SOMETHING.clone();
        assertTrue(typicalCopy.equals(PROJECT_TASK_DO_SOMETHING));

        // same object -> return true
        assertTrue(PROJECT_TASK_DO_SOMETHING.equals(PROJECT_TASK_DO_SOMETHING));

        // null -> return false
        assertFalse(PROJECT_TASK_DO_SOMETHING.equals(null));

        // different type -> returns false
        assertFalse(PROJECT_TASK_DO_SOMETHING.equals(5));

        // different project task -> returns false
        assertFalse(PROJECT_TASK_DO_SOMETHING.equals(PROJECT_TASK_COMPLETED));
    }
}
