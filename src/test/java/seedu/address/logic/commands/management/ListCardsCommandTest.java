package seedu.address.logic.commands.management;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.management.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.management.ListCardsCommand.MESSAGE_NO_CARDS;
import static seedu.address.logic.commands.management.ManagementCommand.MESSAGE_EXPECTED_MODEL;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.ManagementModelStub;
import seedu.address.model.modelmanager.QuizModelStub;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.TypicalLessons;

/**
 * Contains tests for ListCommand.
 */
public class ListCardsCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_listNoCards() {
        ManagementModel modelStub = new MgtModelStubWithNoCards();

        // attempt to list all cards when there are no cards -> return feedback that there are no cards
        assertCommandSuccess(new ListCardsCommand(), modelStub,
                commandHistory, MESSAGE_NO_CARDS, modelStub);
    }

    @Test
    public void execute_listCards() throws Exception {
        ManagementModel modelStub = new MgtModelStubWithCards();
        ListCardsCommand listCardsCommand = new ListCardsCommand();
        String expectedOutput = listCardsCommand.buildList(
                LessonBuilder.DEFAULT_CORE_HEADERS,
                LessonBuilder.DEFAULT_OPT_HEADERS,
                LessonBuilder.DEFAULT_CARDS);

        String feedback = new ListCardsCommand().buildList(LessonBuilder.DEFAULT_CORE_HEADERS,
                LessonBuilder.DEFAULT_OPT_HEADERS, LessonBuilder.DEFAULT_CARDS);


        // attempt to list all cards when there are cards -> list all cards
        CommandResult commandResult = new ListCardsCommand().execute(modelStub, commandHistory);
        assertEquals(commandResult.getFeedbackToUser(), feedback);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        ListCardsCommand listCardsCommand = new ListCardsCommand();

        // attempting to execute ListCardsCommand on a QuizModel instead of a ManagementModel ->
        // CommandException thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MODEL);
        listCardsCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void buildEmptyList_buildSuccessful() {
        String feedback = new ListCardsCommand().buildList(new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<Card>());
        assertEquals(feedback, MESSAGE_NO_CARDS);
    }

    @Test
    public void equals() {
        ListCardsCommand listCardsCommand = new ListCardsCommand();

        // same object -> returns true
        assertEquals(listCardsCommand, listCardsCommand);

        // all ListCardsCommand objects are the same -> returns true
        ListCardsCommand listCardsCommand2 = new ListCardsCommand();
        assertEquals(listCardsCommand, listCardsCommand2);

        // different types -> returns false
        assertNotEquals(listCardsCommand, 1);

        // null -> returns false
        assertNotEquals(listCardsCommand, null);
    }

    @Test
    public void isSaveRequired_isTrue() {
        assertFalse(new ListCardsCommand().isSaveRequired());
    }

    private class MgtModelStubWithNoCards extends ManagementModelStub {
        @Override
        public List<Card> getOpenedLessonCards() {
            return null;
        }
    }


    private class MgtModelStubWithCards extends ManagementModelStub {
        @Override
        public List<Card> getOpenedLessonCards() {
            return TypicalLessons.LESSON_DEFAULT.getCards();
        }

        @Override
        public List<String> getOpenedLessonCoreHeaders() {
            return TypicalLessons.LESSON_DEFAULT.getCoreHeaders();
        }

        @Override
        public List<String> getOpenedLessonOptionalHeaders() {
            return TypicalLessons.LESSON_DEFAULT.getOptionalHeaders();
        }
    }
}
