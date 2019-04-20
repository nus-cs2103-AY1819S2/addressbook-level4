package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.logic.commands.management.ManagementCommand.MESSAGE_EXPECTED_MODEL;
import static seedu.address.testutil.TypicalLessonList.LESSON_DEFAULT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModelStub;
import seedu.address.model.modelmanager.QuizModelStub;

/**
 * Unit tests for the {@link QuitLessonCommand}.
 */
public class QuitLessonCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_lessonClosedByModel_closeSuccessful() throws Exception {
        MgtModelStub modelStub = new MgtModelStub();
        modelStub.openLesson(1); // always work regardless of index
        assertEquals(modelStub.getOpenedLesson(), modelStub.toBeOpenedLesson); // always work

        // closes the opened lesson -> lesson closed successfully without exception
        new QuitLessonCommand().execute(modelStub, commandHistory);
        // openedLesson is now null
        assertNull(modelStub.getOpenedLesson());
    }

    @Test
    public void execute_lessonClosedByModel_closeUnsuccessful() throws Exception {
        MgtModelStub modelStub = new MgtModelStub();

        // attempts to close a lesson when no lesson is opened -> CommandException thrown
        thrown.expect(CommandException.class);
        new QuitLessonCommand().execute(modelStub, commandHistory);
    }

    @Test
    public void execute_lessonClosedByModelTwice_closeUnsuccessful() throws Exception {
        MgtModelStub modelStub = new MgtModelStub();
        modelStub.openLesson(1); // always work regardless of index
        assertEquals(modelStub.getOpenedLesson(), modelStub.toBeOpenedLesson); // always work

        // closes the opened lesson -> lesson closed successfully without exception
        new QuitLessonCommand().execute(modelStub, commandHistory);
        // openedLesson is now null
        assertNull(modelStub.getOpenedLesson());

        // attempts to close a lesson when no lesson is opened -> CommandException thrown
        thrown.expect(CommandException.class);
        new QuitLessonCommand().execute(modelStub, commandHistory);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        QuitLessonCommand quitLessonCommand = new QuitLessonCommand();

        // attempting to execute QuitLessonCommand on a QuizModel instead of a ManagementModel ->
        // CommandException thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MODEL);
        quitLessonCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        QuitLessonCommand quitLessonCommand = new QuitLessonCommand();

        // same object -> returns true
        assertEquals(quitLessonCommand, quitLessonCommand);

        // all QuitLessonCommand objects are the same -> returns true
        QuitLessonCommand quitLessonCommand2 = new QuitLessonCommand();
        assertEquals(quitLessonCommand, quitLessonCommand2);

        // different types -> returns false
        assertNotEquals(quitLessonCommand, 1);

        // null -> returns false
        assertNotEquals(quitLessonCommand, null);
    }

    /**
     * A ManagementModel stub for testing closeLesson command.
     */
    private class MgtModelStub extends ManagementModelStub {
        /**
         * The dud lesson which will always be opened by {@link #openLesson(int)} regardless of index parameter.
         */
        public final Lesson toBeOpenedLesson = LESSON_DEFAULT;
        /**
         * The {@link Lesson} object currently in focus. All lesson-editing-related commands will apply
         * to this lesson.
         */
        private Lesson openedLesson = null; // The lesson currently being edited

        /**
         * This method will always assign {@link #toBeOpenedLesson} to {@link #openedLesson} successfully.
         */
        public String openLesson(int index) {
            openedLesson = toBeOpenedLesson;
            return openedLesson.getName();
        }

        /**
         * Returns the {@link #openedLesson}. A lesson is opened by calling {@link #openLesson(int)} and
         * closed by calling {@link #closeLesson()}. If there is no lesson currently opened, this returns
         * null.
         *
         * @return the {@link Lesson} object in {@link #openedLesson}. Null if there is no opened lesson.
         */
        public Lesson getOpenedLesson() {
            return openedLesson;
        }

        /**
         * Closes the opened {@link Lesson} object.
         * @return the name of the closed {@link Lesson} object
         */
        @Override
        public String closeLesson() {
            requireNonNull(openedLesson);
            String lessonName = openedLesson.getName();
            openedLesson = null;
            return lessonName;
        }
    }
}
