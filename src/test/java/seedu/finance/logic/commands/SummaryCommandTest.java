package seedu.finance.logic.commands;

import static seedu.finance.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.finance.logic.commands.SummaryCommand.MESSAGE_NOT_IMPLEMENTED_YET;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import org.junit.Test;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;

/**
 * Contains integration tests (interaction with Model) and unit test for SummaryCommand
 */
public class SummaryCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute(){
        assertCommandFailure(new SummaryCommand(), model, new CommandHistory(), MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
