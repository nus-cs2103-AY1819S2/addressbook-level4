package seedu.address.logic.commands.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.management.ManagementCommand.MESSAGE_EXPECTED_MODEL;
import static seedu.address.model.lesson.Lesson.EXCEPTION_CORE_SIZE_MISMATCH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public void execute_noCores_throwsCommandException() throws Exception {
        MgtModelStubAcceptingAdd modelStub = new MgtModelStubAcceptingAdd();
        Card invalidCard = new Card(new ArrayList<String>(), new ArrayList<String>());

        // add invalid card -> command exception thrown
        thrown.expect(CommandException.class);
        new AddCardCommand(invalidCard).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_missingCore_throwsCommandException() throws Exception {
        MgtModelStubAcceptingAdd modelStub = new MgtModelStubAcceptingAdd();
        // Create a list of cores with 1 less core than required
        ArrayList<String> cores = new ArrayList<>();
        for (int i = 1; i < modelStub.getOpenedLessonCoreHeaders().size() - 1; i++) {
            cores.add("Test core");
        }

        // Create an invalid card with 1 missing core
        Card invalidCard = new Card(cores, new ArrayList<String>());

        // add invalid card -> command exception thrown
        thrown.expect(CommandException.class);
        new AddCardCommand(invalidCard).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_missingOpenedLesson_throwsCommandException() throws Exception {
        MgtModelStubNoOpenLesson modelStub = new MgtModelStubNoOpenLesson();
        Card validCard = new CardBuilder().build();

        // add valid card but there is no open lesson -> command exception thrown
        thrown.expect(CommandException.class);
        new AddCardCommand(validCard).execute(modelStub, commandHistory);
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
        private final ArrayList<String> coreHeaders;
        private final ArrayList<Card> cards = new ArrayList<>();

        public MgtModelStubAcceptingAdd() {
            coreHeaders = new ArrayList<>();
            coreHeaders.add("Question");
            coreHeaders.add("Answer");
        }

        @Override
        public List<String> getOpenedLessonCoreHeaders() {
            return coreHeaders;
        }

        @Override
        public void addCardToOpenedLesson(Card card) {
            // All Card objects added must have the same number of cores as the number of core headers.
            if (card.getCores().size() != coreHeaders.size()) {
                throw new IllegalArgumentException(EXCEPTION_CORE_SIZE_MISMATCH);
            }

            for (String field: card.getCores()) {
                if (field.isEmpty()) {
                    throw new IllegalArgumentException(EXCEPTION_CORE_SIZE_MISMATCH);
                }
            }

            cards.add(card);
        }
    }

    /**
     * A ManagementModel stub which always fail when attempting to add
     * card given there is no open lesson.
     */
    private class MgtModelStubNoOpenLesson extends ManagementModelStub {
        @Override
        public void addCardToOpenedLesson(Card card) {
            // No open lesson available
            throw new NullPointerException();
        }
    }
}
