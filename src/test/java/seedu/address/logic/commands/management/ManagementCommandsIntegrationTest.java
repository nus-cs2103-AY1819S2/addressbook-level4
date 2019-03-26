package seedu.address.logic.commands.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.testutil.TypicalLessons.LESSON_DEFAULT;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.testutil.LessonBuilder;

/**
 * Integration tests for the {@link AddLessonCommand}, {@link DeleteLessonCommand},
 * {@link OpenLessonCommand} and {@link CloseLessonCommand}.
 * Uses actual {@link ManagementModelManager}.
 *
 * Work-in-progress
 */
public class ManagementCommandsIntegrationTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private CommandHistory commandHistory = new CommandHistory();
    private ManagementModelManager model = new ManagementModelManager();
    private Lesson validLesson = new LessonBuilder().build();

    /**
     * Tests {@link AddLessonCommand} and {@link DeleteLessonCommand}.
     */
    @Test
    public void execute_lessonDeletedByModel_deleteSuccessful() throws Exception {
        // add valid lesson -> lesson added successfully
        CommandResult commandResult =
                new AddLessonCommand(validLesson).execute(model, commandHistory);

        // lesson added successfully -> success feedback
        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());

        // lesson added successfully -> lesson in lessons
        assertEquals(Collections.singletonList(validLesson), model.getLessonList());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // delete a lesson which exists in model -> lesson deleted successfully
        Index toDeleteIndex = Index.fromZeroBased(0);
        commandResult =
                new DeleteLessonCommand(toDeleteIndex).execute(model, commandHistory);

        // lesson deleted successfully -> success feedback
        assertEquals(String.format(DeleteLessonCommand.MESSAGE_SUCCESS, LESSON_DEFAULT.getName()),
                commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    /**
     * Tests {@link AddLessonCommand} and {@link OpenLessonCommand}.
     */
    @Test
    public void execute_addAndOpenLesson_openSuccessful() throws Exception {
        // add valid lesson -> lesson added successfully
        CommandResult commandResult =
                new AddLessonCommand(validLesson).execute(model, commandHistory);

        // lesson added successfully -> success feedback
        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());

        // lesson added successfully -> lesson in lessons
        assertEquals(Collections.singletonList(validLesson), model.getLessonList());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // opens a lesson which exists in model -> lesson opened successfully
        Index toOpenIndex = Index.fromZeroBased(0);
        commandResult =
                new OpenLessonCommand(toOpenIndex).execute(model, commandHistory);

        // lesson opened successfully -> success feedback
        assertEquals(String.format(OpenLessonCommand.MESSAGE_SUCCESS, validLesson.getName()),
                commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // get opened lesson which was added -> same as lesson which was added
        assertEquals(model.getOpenedLesson(), validLesson);
    }

    /**
     * Tests {@link AddLessonCommand}, {@link OpenLessonCommand} and {@link CloseLessonCommand}.
     */
    @Test
    public void execute_addAndOpenAndCloseLesson_closeSuccessful() throws Exception {
        // add valid lesson -> lesson added successfully
        CommandResult commandResult =
                new AddLessonCommand(validLesson).execute(model, commandHistory);

        // lesson added successfully -> success feedback
        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());

        // lesson added successfully -> lesson in lessons
        assertEquals(Collections.singletonList(validLesson), model.getLessonList());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // opens a lesson which exists in model -> lesson opened successfully
        Index toOpenIndex = Index.fromZeroBased(0);
        commandResult =
                new OpenLessonCommand(toOpenIndex).execute(model, commandHistory);

        // lesson opened successfully -> success feedback
        assertEquals(String.format(OpenLessonCommand.MESSAGE_SUCCESS, validLesson.getName()),
                commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // get opened lesson which was added -> same as lesson which was added
        assertEquals(model.getOpenedLesson(), validLesson);

        // closes the opened lesson -> lesson closed successfully without exception
        new CloseLessonCommand().execute(model, commandHistory);

        // openedLesson is now null
        assertNull(model.getOpenedLesson());
    }

    /**
     * Tests {@link AddLessonCommand}, {@link DeleteLessonCommand}, {@link OpenLessonCommand}.
     */
    @Test
    public void execute_addAndDeleteAndOpenLesson_openUnsuccessful() throws Exception {
        // add valid lesson -> lesson added successfully
        CommandResult commandResult =
                new AddLessonCommand(validLesson).execute(model, commandHistory);

        // lesson added successfully -> success feedback
        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());

        // lesson added successfully -> lesson in lessons
        assertEquals(Collections.singletonList(validLesson), model.getLessonList());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // opens a lesson which exists in model -> lesson opened successfully
        Index toOpenIndex = Index.fromZeroBased(0);
        new OpenLessonCommand(toOpenIndex).execute(model, commandHistory);

        // delete a lesson which exists in model -> lesson deleted successfully
        Index toDeleteIndex = Index.fromZeroBased(0);
        commandResult =
                new DeleteLessonCommand(toDeleteIndex).execute(model, commandHistory);

        // lesson deleted successfully -> success feedback
        assertEquals(String.format(DeleteLessonCommand.MESSAGE_SUCCESS, LESSON_DEFAULT.getName()),
                commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // opens a lesson which has been deleted -> CommandException thrown
        thrown.expect(CommandException.class);
        toOpenIndex = Index.fromZeroBased(0);
        new OpenLessonCommand(toOpenIndex).execute(model, commandHistory);
    }

    /**
     * Tests {@link AddLessonCommand}, {@link DeleteLessonCommand}, {@link OpenLessonCommand}.
     */
    @Test
    public void execute_addAndOpenAndDeleteAndCloseLesson_closeUnsuccessful() throws Exception {
        // add valid lesson -> lesson added successfully
        CommandResult commandResult =
                new AddLessonCommand(validLesson).execute(model, commandHistory);

        // lesson added successfully -> success feedback
        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());

        // lesson added successfully -> lesson in lessons
        assertEquals(Collections.singletonList(validLesson), model.getLessonList());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // opens a lesson which exists in model -> lesson opened successfully
        Index toOpenIndex = Index.fromZeroBased(0);
        new OpenLessonCommand(toOpenIndex).execute(model, commandHistory);

        // delete a lesson which exists in model -> lesson deleted successfully
        Index toDeleteIndex = Index.fromZeroBased(0);
        commandResult =
                new DeleteLessonCommand(toDeleteIndex).execute(model, commandHistory);

        // lesson deleted successfully -> success feedback
        assertEquals(String.format(DeleteLessonCommand.MESSAGE_SUCCESS, LESSON_DEFAULT.getName()),
                commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // lesson is no longer in memory and hence no lesson is open, automatically closed
        // get openedLesson -> return null
        assertNull(model.getOpenedLesson());

        // attempt to close opened lesson when already automatically closed
        thrown.expect(CommandException.class);
        new CloseLessonCommand().execute(model, commandHistory);
    }
}
