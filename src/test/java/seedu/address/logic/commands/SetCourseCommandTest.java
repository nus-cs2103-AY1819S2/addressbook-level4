package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.util.SampleCourse.COMPUTER_SCIENCE_AI;
import static seedu.address.model.util.SampleCourse.COMPUTER_SCIENCE_ALGORITHMS;
import static seedu.address.testutil.TypicalModuleTaken.getTypicalGradTrak;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserInfo;
import seedu.address.model.UserPrefs;
import seedu.address.model.course.CourseList;
import seedu.address.model.course.CourseName;
import seedu.address.model.moduleinfo.ModuleInfoList;
import seedu.address.testutil.Assert;

public class SetCourseCommandTest {
    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalGradTrak(), new UserPrefs(),
            new ModuleInfoList(), new CourseList(), new UserInfo());

    private Model expectedModel = new ModelManager(getTypicalGradTrak(), new UserPrefs(),
            new ModuleInfoList(), new CourseList(), new UserInfo());

    @Test
    public void equals() {
        CourseName algorithms = new CourseName("Computer Science Algorithms");
        SetCourseCommand setAlgorithms = new SetCourseCommand(algorithms);
        assertEquals(setAlgorithms, new SetCourseCommand(COMPUTER_SCIENCE_ALGORITHMS.getCourseName()));
        assertNotEquals(null, setAlgorithms);
        assertNotEquals(new SetCourseCommand(COMPUTER_SCIENCE_AI.getCourseName()), setAlgorithms);
    }

    //TODO: add other test cases
    @Test
    public void execute_existingCourse() {
        CourseName courseName = new CourseName("Computer Science Algorithms");
        String expectedMessage = String.format(SetCourseCommand.MESSAGE_SUCCESS, courseName);
        SetCourseCommand command = new SetCourseCommand(courseName);
        expectedModel.setCourse(courseName);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_setNonExistingCourse() {
        Assert.assertThrows(CommandException.class, ()
            -> new SetCourseCommand(new CourseName("No such course")).execute(model, commandHistory));
    }

}
