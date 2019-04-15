package seedu.address.logic.commands.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_CARD_VIEW_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.commands.management.ManagementCommand.MESSAGE_EXPECTED_MODEL;
import static seedu.address.model.lesson.LessonList.EXCEPTION_INVALID_INDEX;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModelStub;
import seedu.address.model.modelmanager.QuizModelStub;

/**
 * Unit tests for the {@link DeleteCardCommand}.
 */
public class DeleteCardCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Index toDeleteIndex1 = Index.fromZeroBased(0);
    private Index toDeleteIndex2 = Index.fromZeroBased(1);

    @Test
    public void execute_openedLessonAndValidCard_deleteSuccessful() throws Exception {
        MgtModelStubAcceptingAddDelete modelStub = new MgtModelStubAcceptingAddDelete();
        modelStub.addCardToOpenedLesson(CARD_JAPAN); // always work

        // delete a card which exists in model -> card deleted successfully
        Index toDeleteIndex = Index.fromZeroBased(0);
        CommandResult commandResult =
                new DeleteCardCommand(toDeleteIndex).execute(modelStub, commandHistory);

        // card deleted successfully -> success feedback
        assertEquals(DeleteCardCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_noOpenedLesson_throwsCommandException() throws CommandException {
        MgtModelStubWithNoOpenedLesson modelStub = new MgtModelStubWithNoOpenedLesson();
        Index toDeleteIndex = Index.fromZeroBased(0);

        // delete a card which does not exist in model -> CommandException thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_CARD_VIEW_COMMAND);
        new DeleteCardCommand(toDeleteIndex).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_noCardAtIndex_throwsCommandException() throws CommandException {
        MgtModelStubAcceptingAddDelete modelStub = new MgtModelStubAcceptingAddDelete();
        Index toDeleteIndex = Index.fromZeroBased(0);

        // delete a card which does not exist in model -> CommandException thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_INDEX,
                toDeleteIndex.getOneBased()));
        new DeleteCardCommand(toDeleteIndex).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws CommandException {
        MgtModelStubAcceptingAddDelete modelStub = new MgtModelStubAcceptingAddDelete();
        Index toDeleteIndex = Index.fromZeroBased(1000);

        // delete a card which does not exist in model -> CommandException thrown
        thrown.expectMessage(String.format(MESSAGE_INVALID_INDEX,
                toDeleteIndex.getOneBased()));
        new DeleteCardCommand(toDeleteIndex).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        Index toDeleteIndex = Index.fromZeroBased(0);
        DeleteCardCommand deleteCardCommand = new DeleteCardCommand(toDeleteIndex);

        // attempting to execute DeleteCardCommand on a QuizModel instead of a ManagementModel ->
        // CommandException thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MODEL);
        deleteCardCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        DeleteCardCommand deleteCardCommand1 = new DeleteCardCommand(toDeleteIndex1);
        DeleteCardCommand deleteCardCommand2 = new DeleteCardCommand(toDeleteIndex2);

        // same object -> returns true
        assertEquals(deleteCardCommand1, deleteCardCommand1);

        // same values -> returns true
        DeleteCardCommand deleteCardCommandCopy = new DeleteCardCommand(toDeleteIndex1);
        assertEquals(deleteCardCommand1, deleteCardCommandCopy);

        // different types -> returns false
        assertNotEquals(deleteCardCommand1, 1);

        // null -> returns false
        assertNotEquals(deleteCardCommand1, null);

        // different card -> returns false
        assertNotEquals(deleteCardCommand1, deleteCardCommand2);
    }

    /**
     * A ManagementModel stub which always reject card add and delete because no lesson is opened.
     */
    private class MgtModelStubWithNoOpenedLesson extends ManagementModelStub {
        @Override
        public boolean isThereOpenedLesson() {
            return false;
        }
    }

    /**
     * A ManagementModel stub which always accept card add and delete given that a lesson is opened.
     */
    private class MgtModelStubAcceptingAddDelete extends ManagementModelStub {
        private final ArrayList<Card> cards = new ArrayList<>();

        @Override
        public boolean isThereOpenedLesson() {
            return true;
        }

        /**
         * Gets the {@code Card} objects from the opened {@link Lesson} object.
         *
         * @return {@code Card} objects from the opened {@link Lesson} object.
         * Returns null if there are no cards found.
         */
        @Override
        public List<Card> getOpenedLessonCards() {
            return cards;
        }

        @Override
        public void addCardToOpenedLesson(Card card) {
            cards.add(card);
        }

        @Override
        public void deleteCardFromOpenedLesson(int index) {
            try {
                cards.remove(index);
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException(EXCEPTION_INVALID_INDEX + index);
            }
        }
    }
}
