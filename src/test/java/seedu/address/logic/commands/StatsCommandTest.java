package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_BOUND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_BOUND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.StatsCommand.MESSAGE_STATISTICS_FORMAT;
import static seedu.address.testutil.TypicalFlashcards.EMAIL;
import static seedu.address.testutil.TypicalFlashcards.HELLO;
import static seedu.address.testutil.TypicalFlashcards.getTypicalCardCollection;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.FlashcardPredicate;
import seedu.address.model.flashcard.Statistics;

public class StatsCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalCardCollection(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardCollection(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute() {
        // overall flashcards
        double successRate = getOverallSuccessRateFromModel(model);
        String feedbackToUser = String.format(MESSAGE_STATISTICS_FORMAT, successRate);
        CommandResult expectedCommandResult = new CommandResult(feedbackToUser, false, false);
        assertCommandSuccess(new StatsCommand(), model, commandHistory, expectedCommandResult, expectedModel);

        // run stats command with some predicates.
        double[] successRateRange = {VALID_MIN_BOUND, VALID_MAX_BOUND};
        FlashcardPredicate predicate = new FlashcardPredicate(
                Arrays.asList(HELLO.getFrontFace().text),
                Arrays.asList(EMAIL.getBackFace().text),
                Collections.emptyList(),
                successRateRange);

        expectedModel.updateFilteredFlashcardList(predicate);
        successRate = getFilteredSuccessRateFromModel(expectedModel);
        feedbackToUser = String.format(MESSAGE_STATISTICS_FORMAT, successRate);
        expectedCommandResult = new CommandResult(feedbackToUser, false, false);
        assertCommandSuccess(new StatsCommand(predicate), model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        StatsCommand command = new StatsCommand();

        // no predicate stats
        assertEquals(command, new StatsCommand());

        // with predicate stats
        double[] successRateRange = {VALID_MIN_BOUND, VALID_MAX_BOUND};
        FlashcardPredicate predicate1 = new FlashcardPredicate(
                Arrays.asList("Klungs", "Robin"), Arrays.asList("Sergio"), Arrays.asList("Luca"), successRateRange
        );

        assertEquals(new StatsCommand(predicate1), new StatsCommand(predicate1));
    }

    private double getFilteredSuccessRateFromModel(Model model) {
        Statistics stats = new Statistics();
        for (Flashcard card : model.getFilteredFlashcardList()) {
            stats = stats.merge(card.getStatistics());
        }
        return stats.getSuccessRate() * 100;
    }

    private double getOverallSuccessRateFromModel(Model model) {
        Statistics stats = new Statistics();
        for (Flashcard card : model.getCardCollection().getFlashcardList()) {
            stats = stats.merge(card.getStatistics());
        }
        return stats.getSuccessRate() * 100;
    }
}
