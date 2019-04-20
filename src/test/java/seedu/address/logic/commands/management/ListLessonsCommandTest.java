package seedu.address.logic.commands.management;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_LESSON_VIEW_COMMAND;
import static seedu.address.logic.commands.management.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.management.ListLessonsCommand.MESSAGE_NO_LESSONS;
import static seedu.address.logic.commands.management.ManagementCommand.MESSAGE_EXPECTED_MODEL;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.ManagementModelStub;
import seedu.address.model.modelmanager.QuizModelStub;
import seedu.address.testutil.TypicalLessonList;

/**
 * Contains tests for ListCommand.
 */
public class ListLessonsCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_listNoLessons_listSuccessful() {
        ManagementModel modelStub = new MgtModelStubWithNoLessons();

        // attempt to list all lessons when there are no lessons -> return feedback that there are no lessons
        assertCommandSuccess(new ListLessonsCommand(), modelStub,
                commandHistory, MESSAGE_NO_LESSONS, modelStub);
    }

    @Test
    public void execute_listLessons_listSuccessful() {
        ManagementModel modelStub = new MgtModelStubWithLessons();
        ListLessonsCommand listLessonsCommand = new ListLessonsCommand();
        String expectedOutput = listLessonsCommand.buildList(TypicalLessonList.getTypicalLessonList());

        // attempt to list all lessons when there are lessons -> list all lessons
        assertCommandSuccess(new ListLessonsCommand(), modelStub, commandHistory,
                expectedOutput, modelStub);
    }

    @Test
    public void execute_listLessonsWhenHasOpenedLesson_listUnsuccessful() throws CommandException {
        MgtModelStubWithOpenedLesson modelStub = new MgtModelStubWithOpenedLesson();

        // attempt to list all lessons when in Lesson View mode (opened lesson) -> exception thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_LESSON_VIEW_COMMAND);
        new ListLessonsCommand().execute(modelStub, commandHistory);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        ListLessonsCommand listLessonsCommand = new ListLessonsCommand();

        // attempting to execute ListLessonsCommand on a QuizModel instead of a ManagementModel ->
        // CommandException thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MODEL);
        listLessonsCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        ListLessonsCommand listLessonCommand = new ListLessonsCommand();

        // same object -> returns true
        assertEquals(listLessonCommand, listLessonCommand);

        // all ListLessonsCommand objects are the same -> returns true
        ListLessonsCommand listLessonCommand2 = new ListLessonsCommand();
        assertEquals(listLessonCommand, listLessonCommand2);

        // different types -> returns false
        assertNotEquals(listLessonCommand, 1);

        // null -> returns false
        assertNotEquals(listLessonCommand, null);
    }

    private class MgtModelStubWithNoLessons extends ManagementModelStub {
        @Override
        public boolean isThereOpenedLesson() {
            return false;
        }

        @Override
        public List<Lesson> getLessons() {
            return new ArrayList<>();
        }
    }


    private class MgtModelStubWithLessons extends ManagementModelStub {
        @Override
        public boolean isThereOpenedLesson() {
            return false;
        }

        @Override
        public List<Lesson> getLessons() {
            return TypicalLessonList.getTypicalLessonList();
        }
    }

    private class MgtModelStubWithOpenedLesson extends ManagementModelStub {
        @Override
        public boolean isThereOpenedLesson() {
            return true;
        }

        @Override
        public List<Lesson> getLessons() {
            return TypicalLessonList.getTypicalLessonList();
        }
    }
}
