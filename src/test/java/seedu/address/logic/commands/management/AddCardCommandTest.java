package seedu.address.logic.commands.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.management.ManagementCommand.MESSAGE_EXPECTED_MODEL;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.modelmanager.ManagementModelStub;
import seedu.address.model.modelmanager.QuizModelStub;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.TypicalCards;

/**
 * Unit tests for the {@link AddCardCommand}.
 */
public class AddCardCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCard_throwsNullPointerException() {
        // add null card -> NullPointerException thrown
        thrown.expect(NullPointerException.class);
        new AddCardCommand(null);
    }

    @Test
    public void execute_cardAcceptedByModel_addSuccessful() throws Exception {
        MgtModelStubAcceptingAdd modelStub = new MgtModelStubAcceptingAdd();
        Card validCard = new CardBuilder().build();

        // add valid card -> card added successfully
        CommandResult commandResult =
                new AddCardCommand(validCard).execute(modelStub, commandHistory);

        // card added successfully -> success feedback
        assertEquals(String.format(AddCardCommand.MESSAGE_SUCCESS, validCard),
                commandResult.getFeedbackToUser());
        // card added successfully -> card in cards
        assertEquals(Collections.singletonList(validCard), modelStub.cards);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_incorrectModel_throwsCommandException() throws Exception {
        QuizModelStub modelStub = new QuizModelStub();
        Card validCard = new CardBuilder().build();
        AddCardCommand addCardCommand = new AddCardCommand(validCard);

        // attempting to execute AddCardCommand on a QuizModel instead of a ManagementModel ->
        // CommandException thrown
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_EXPECTED_MODEL);
        addCardCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Card cardDefault = new CardBuilder(TypicalCards.CARD_JAPAN).build();
        Card cardTrueFalse = new CardBuilder(TypicalCards.CARD_CAT).build();
        AddCardCommand addCardDefCommand = new AddCardCommand(cardDefault);
        AddCardCommand addCardPropCommand = new AddCardCommand(cardTrueFalse);

        // same object -> returns true
        assertEquals(addCardDefCommand, addCardDefCommand);

        // same values -> returns true
        AddCardCommand addCardDefCommandCopy = new AddCardCommand(cardDefault);
        assertEquals(addCardDefCommand, addCardDefCommandCopy);

        // different types -> returns false
        assertNotEquals(addCardDefCommand, 1);

        // null -> returns false
        assertNotEquals(addCardDefCommand, null);

        // different card -> returns false
        assertNotEquals(addCardDefCommand, addCardPropCommand);
    }

    @Test
    public void isSaveRequired_isTrue() {
        assertTrue(new AddCardCommand(TypicalCards.CARD_JAPAN).isSaveRequired());
    }

    /**
     * A ManagementModel stub which always accept the card being added.
     */
    private class MgtModelStubAcceptingAdd extends ManagementModelStub {
        private final ArrayList<Card> cards = new ArrayList<>();

        @Override
        public void addCardToOpenedLesson(Card card) {
            cards.add(card);
        }
    }

    /**
     * A ManagementModel stub which always accept the card being added.
     */
    private class MgtModelStubNoOpenLesson extends ManagementModelStub {
        private final ArrayList<Card> cards = new ArrayList<>();

        @Override
        public void addCardToOpenedLesson(Card card) {
            cards.add(card);
        }
    }
}
