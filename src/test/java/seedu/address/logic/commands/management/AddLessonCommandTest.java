package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.Command.MESSAGE_EXPECTED_MGT_MODEL;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.management.ManagementModelStub;
import seedu.address.model.modelmanager.quiz.QuizModelStub;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.TypicalLessons;

public class AddLessonCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddLessonCommand(null);
    }

    @Test
    public void execute_lessonAcceptedByModel_addSuccessful() throws Exception {
        ManagementModelStubAcceptingLesson modelStub = new ManagementModelStubAcceptingLesson();
        Lesson validLesson = new LessonBuilder().build();

        CommandResult commandResult =
                new AddLessonCommand(validLesson).execute(modelStub, commandHistory);

        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validLesson), modelStub.lessonsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        Lesson validLesson = new LessonBuilder().build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(validLesson);

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MGT_MODEL);
        addLessonCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Lesson lessonDefault = new LessonBuilder(TypicalLessons.LESSON_DEFAULT).build();
        Lesson lessonTrueFalse = new LessonBuilder(TypicalLessons.LESSON_TRUE_FALSE).build();
        AddLessonCommand addLessonDefCommand = new AddLessonCommand(lessonDefault);
        AddLessonCommand addLessonPropCommand = new AddLessonCommand(lessonTrueFalse);

        // same object -> returns true
        assertEquals(addLessonDefCommand, addLessonDefCommand);

        // same values -> returns true
        AddLessonCommand addLessonDefCommandCopy = new AddLessonCommand(lessonDefault);
        assertEquals(addLessonDefCommand, addLessonDefCommandCopy);

        // different types -> returns false
        assertNotEquals(addLessonDefCommand, 1);

        // null -> returns false
        assertNotEquals(addLessonDefCommand, null);

        // different lesson -> returns false
        assertNotEquals(addLessonDefCommand, addLessonPropCommand);
    }

    /**
     * A Model stub that always accept the lesson being added.
     */
    private class ManagementModelStubAcceptingLesson extends ManagementModelStub {
        public final ArrayList<Lesson> lessonsAdded = new ArrayList<>();

        @Override
        public void addLesson(Lesson lesson) {
            requireNonNull(lesson);
            lessonsAdded.add(lesson);
        }
    }
}
