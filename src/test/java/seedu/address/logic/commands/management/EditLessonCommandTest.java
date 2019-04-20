package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_LESSON_VIEW_COMMAND;
import static seedu.address.logic.commands.management.ManagementCommand.MESSAGE_EXPECTED_MODEL;
import static seedu.address.model.lesson.LessonList.EXCEPTION_INVALID_INDEX;
import static seedu.address.testutil.TypicalLessonList.LESSON_DEFAULT;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModelStub;
import seedu.address.model.modelmanager.QuizModelStub;

/**
 * Unit tests for the {@link EditLessonCommand}.
 */
public class EditLessonCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_lessonOpenedByModel_openSuccessful() throws Exception {
        MgtModelStub modelStub = new MgtModelStub();
        modelStub.addLesson(LESSON_DEFAULT); // always work

        // opens a lesson which exists in model -> lesson opened successfully
        Index toOpenIndex = Index.fromZeroBased(0);
        CommandResult commandResult =
                new EditLessonCommand(toOpenIndex).execute(modelStub, commandHistory);

        // lesson opened successfully -> success feedback
        assertEquals(String.format(EditLessonCommand.MESSAGE_SUCCESS, LESSON_DEFAULT.getName()),
                commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // get opened lesson which was added -> same as lesson which was added
        assertEquals(modelStub.getOpenedLesson(), LESSON_DEFAULT);
    }

    @Test
    public void execute_alreadyHasOpenLesson_openUnsuccessful() throws Exception {
        MgtModelStub modelStub = new MgtModelStub();
        modelStub.addLesson(LESSON_DEFAULT); // always work

        // opens a lesson which exists in model -> lesson opened successfully
        Index toOpenIndex = Index.fromZeroBased(0);
        new EditLessonCommand(toOpenIndex).execute(modelStub, commandHistory);

        // opens another lesson again -> lesson open unsuccessful, need to close first
        toOpenIndex = Index.fromZeroBased(1);
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_LESSON_VIEW_COMMAND);
        new EditLessonCommand(toOpenIndex).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_lessonOpenedByModel_openUnsuccessful() throws Exception {
        MgtModelStub modelStub = new MgtModelStub();
        Index toOpenIndex = Index.fromZeroBased(0);

        // opens a lesson which does not exist in model -> CommandException thrown
        thrown.expect(CommandException.class);
        new EditLessonCommand(toOpenIndex).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        Index toOpenIndex = Index.fromZeroBased(0);
        EditLessonCommand editLessonCommand = new EditLessonCommand(toOpenIndex);

        // attempting to execute EditLessonCommand on a QuizModel instead of a ManagementModel ->
        // CommandException thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MODEL);
        editLessonCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Index toOpenIndex1 = Index.fromZeroBased(0);
        Index toOpenIndex2 = Index.fromZeroBased(1);
        EditLessonCommand editLessonCommand1 = new EditLessonCommand(toOpenIndex1);
        EditLessonCommand editLessonCommand2 = new EditLessonCommand(toOpenIndex2);

        // same object -> returns true
        assertEquals(editLessonCommand1, editLessonCommand1);

        // same values -> returns true
        EditLessonCommand editLessonCommandCopy = new EditLessonCommand(toOpenIndex1);
        assertEquals(editLessonCommand1, editLessonCommandCopy);

        // different types -> returns false
        assertNotEquals(editLessonCommand1, 1);

        // null -> returns false
        assertNotEquals(editLessonCommand1, null);

        // different lesson -> returns false
        assertNotEquals(editLessonCommand1, editLessonCommand2);
    }

    /**
     * A ManagementModel stub for testing openLesson command.
     */
    private class MgtModelStub extends ManagementModelStub {
        private final ArrayList<Lesson> lessons = new ArrayList<>();
        /**
         * The {@link Lesson} object currently in focus. All lesson-editing-related commands will apply
         * to this lesson.
         */
        private Lesson openedLesson = null; // The lesson currently being edited

        @Override
        public void addLesson(Lesson lesson) {
            requireNonNull(lesson);
            lessons.add(lesson);
        }

        public Lesson getLesson(int index) {
            try {
                return lessons.get(index);
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException(EXCEPTION_INVALID_INDEX + index);
            }
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
         * Sets {@link #openedLesson} to the lesson at the specified index.
         * All lesson-editing-related commands will apply to this lesson.
         *
         * @param index index of the lesson to be assigned to {@link #openedLesson}
         * @return the name of the lesson
         */
        public String openLesson(int index) {
            try {
                openedLesson = lessons.get(index);
                return openedLesson.getName();
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException(EXCEPTION_INVALID_INDEX + index);
            }
        }

        @Override
        public boolean isThereOpenedLesson() {
            return openedLesson != null;
        }

        @Override
        public List<Lesson> getLessons() {
            return lessons;
        }

        @Override
        public void deleteLesson(int index) {
            lessons.remove(index);
        }
    }
}
