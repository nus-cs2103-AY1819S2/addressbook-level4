package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.StatsCommand.MESSAGE_STATISTICS_FORMAT;
import static seedu.address.testutil.TypicalFlashcards.getTypicalCardCollection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Statistics;

public class StatsCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalCardCollection(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardCollection(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute() throws Exception {
        double successRate = getOverallSuccessRateFromModel(model);
        String feedbackToUser = String.format(MESSAGE_STATISTICS_FORMAT, successRate);
        CommandResult expectedCommandResult = new CommandResult(feedbackToUser, false, false);
        assertCommandSuccess(new StatsCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }

    private double getOverallSuccessRateFromModel(Model model) {
        Statistics stats = new Statistics();
        for (Flashcard card : model.getCardCollection().getFlashcardList()) {
            stats = stats.merge(card.getStatistics());
        }
        return stats.getSuccessRate() * 100;
    }
}
