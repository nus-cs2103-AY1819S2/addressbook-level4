package seedu.address.logic.commands.management;

import static seedu.address.logic.commands.Command.MESSAGE_EXPECTED_MGT_MODEL;
import static seedu.address.logic.commands.management.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.management.ListLessonsCommand.MESSAGE_DELIMITER;
import static seedu.address.logic.commands.management.ListLessonsCommand.MESSAGE_NO_LESSONS;
import static seedu.address.logic.commands.management.ListLessonsCommand.MESSAGE_SUCCESS;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.management.ManagementModel;
import seedu.address.model.modelmanager.management.ManagementModelStub;
import seedu.address.model.modelmanager.quiz.QuizModelStub;
import seedu.address.testutil.TypicalLessons;

/**
 * Contains tests for ListCommand.
 */
public class ListLessonsCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_listNoLessons() {
        ManagementModel modelStub = new ManagementModelStubWithNoLessons();

        assertCommandSuccess(new ListLessonsCommand(), modelStub, commandHistory,
                MESSAGE_SUCCESS + MESSAGE_DELIMITER + MESSAGE_NO_LESSONS, modelStub);
    }

    @Test
    public void execute_listLessons() {
        ManagementModel modelStub = new ManagementModelStubWithLessons();
        ListLessonsCommand listLessonsCommand = new ListLessonsCommand();
        String expectedOutput = listLessonsCommand.buildList(TypicalLessons.getTypicalLessons());

        assertCommandSuccess(new ListLessonsCommand(), modelStub, commandHistory,
                expectedOutput, modelStub);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        ListLessonsCommand listLessonsCommand = new ListLessonsCommand();

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MGT_MODEL);
        listLessonsCommand.execute(modelStub, commandHistory);
    }

    private class ManagementModelStubWithNoLessons extends ManagementModelStub {
        @Override
        public List<Lesson> getLessons() {
            return new ArrayList<>();
        }
    }


    private class ManagementModelStubWithLessons extends ManagementModelStub {
        @Override
        public List<Lesson> getLessons() {
            return TypicalLessons.getTypicalLessons();
        }
    }
}
