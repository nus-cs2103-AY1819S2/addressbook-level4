package seedu.address.logic.commands.management;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_CARD_VIEW_COMMAND;
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
import seedu.address.testutil.TypicalLessonList;

/**
 * Contains tests for ListCommand.
 */
public class ListCardsCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_noOpenedLesson_throwsCommandException() throws CommandException {
        ManagementModel modelStub = new MgtModelStubWithNoCards();

        // attempt to list all cards when there is no opened lesson -> error message
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_CARD_VIEW_COMMAND);
        new ListCardsCommand().execute(modelStub, commandHistory);
    }

    @Test
    public void execute_openedLessonHasNoCards_throwsCommandException() throws CommandException {
        MgtModelStubWithNoCards modelStub = new MgtModelStubWithNoCards();
        modelStub.setIsThereOpenedLesson(true);

        // attempt to list all cards when there is no cards in opened lesson -> error message
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_NO_CARDS);
        new ListCardsCommand().execute(modelStub, commandHistory);
    }

    @Test
    public void execute_openedLessonHasCards_listCards() throws Exception {
        ManagementModel modelStub = new MgtModelStubWithCards();

        // attempt to list all cards when there are cards -> list all cards
        CommandResult commandResult = new ListCardsCommand().execute(modelStub, commandHistory);
        assertNotNull(commandResult.getFeedbackToUser());
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
        String feedback = new ListCardsCommand().buildList(new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
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

    private class MgtModelStubWithNoCards extends ManagementModelStub {
        private boolean isThereOpenedLesson = false;

        public void setIsThereOpenedLesson(boolean isThereOpenedLesson) {
            this.isThereOpenedLesson = isThereOpenedLesson;
        }

        @Override
        public boolean isThereOpenedLesson() {
            return isThereOpenedLesson;
        }

        @Override
        public List<Card> getOpenedLessonCards() {
            return null;
        }
    }


    private class MgtModelStubWithCards extends ManagementModelStub {
        @Override
        public boolean isThereOpenedLesson() {
            return true;
        }

        @Override
        public List<Card> getOpenedLessonCards() {
            return TypicalLessonList.LESSON_DEFAULT.getCards();
        }

        @Override
        public List<String> getOpenedLessonCoreHeaders() {
            return TypicalLessonList.LESSON_DEFAULT.getCoreHeaders();
        }

        @Override
        public List<String> getOpenedLessonOptionalHeaders() {
            return TypicalLessonList.LESSON_DEFAULT.getOptionalHeaders();
        }
    }
}
