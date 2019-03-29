package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.management.ManagementCommand.MESSAGE_EXPECTED_MODEL;
import static seedu.address.model.lesson.LessonList.EXCEPTION_INVALID_INDEX;
import static seedu.address.testutil.TypicalLessons.LESSON_DEFAULT;

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
import seedu.address.testutil.TypicalCards;

/**
 * Unit tests for the {@link DeleteLessonCommand}.
 */
public class DeleteLessonCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_lessonDeletedByModel_deleteSuccessful() throws Exception {
        MgtModelStubAcceptingAddDelete modelStub = new MgtModelStubAcceptingAddDelete();
        modelStub.addLesson(LESSON_DEFAULT); // always work

        // delete a lesson which exists in model -> lesson deleted successfully
        Index toDeleteIndex = Index.fromZeroBased(0);
        CommandResult commandResult =
                new DeleteLessonCommand(toDeleteIndex).execute(modelStub, commandHistory);

        // lesson deleted successfully -> success feedback
        assertEquals(String.format(DeleteLessonCommand.MESSAGE_SUCCESS, LESSON_DEFAULT.getName()),
                commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_lessonDeletedByModel_deleteUnsuccessful() throws Exception {
        MgtModelStubAcceptingAddDelete modelStub = new MgtModelStubAcceptingAddDelete();
        Index toDeleteIndex = Index.fromZeroBased(0);

        // delete a lesson which does not exist in model -> CommandException thrown
        thrown.expect(CommandException.class);
        new DeleteLessonCommand(toDeleteIndex).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        Index toDeleteIndex = Index.fromZeroBased(0);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(toDeleteIndex);

        // attempting to execute DeleteLessonCommand on a QuizModel instead of a ManagementModel ->
        // CommandException thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MODEL);
        deleteLessonCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Index toDeleteIndex1 = Index.fromZeroBased(0);
        Index toDeleteIndex2 = Index.fromZeroBased(1);
        DeleteLessonCommand deleteLessonCommand1 = new DeleteLessonCommand(toDeleteIndex1);
        DeleteLessonCommand deleteLessonCommand2 = new DeleteLessonCommand(toDeleteIndex2);

        // same object -> returns true
        assertEquals(deleteLessonCommand1, deleteLessonCommand1);

        // same values -> returns true
        DeleteLessonCommand deleteLessonCommandCopy = new DeleteLessonCommand(toDeleteIndex1);
        assertEquals(deleteLessonCommand1, deleteLessonCommandCopy);

        // different types -> returns false
        assertNotEquals(deleteLessonCommand1, 1);

        // null -> returns false
        assertNotEquals(deleteLessonCommand1, null);

        // different lesson -> returns false
        assertNotEquals(deleteLessonCommand1, deleteLessonCommand2);
    }

    /**
     * A ManagementModel stub which always accept the lesson being added and can always delete a lesson if
     * it exists.
     */
    private class MgtModelStubAcceptingAddDelete extends ManagementModelStub {
        private final ArrayList<Lesson> lessons = new ArrayList<>();

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

        @Test
        public void isSaveRequired_isTrue() {
            assertTrue(new AddCardCommand(TypicalCards.CARD_JAPAN).isSaveRequired());
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
