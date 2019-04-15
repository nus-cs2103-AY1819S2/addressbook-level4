package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.StatsUtil;
import seedu.address.testutil.TestUtil;

public class OverallStatsCommandTest {
    private Model model = new ModelManager(TestUtil.typicalPocketProject(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    @Test
    public void execute() {
        Model expectedModel = new ModelManager(TestUtil.typicalPocketProject(), new UserPrefs());
        String expectedMessage = StatsUtil.overAllStatsString(model.getPocketProject().getEmployeeList(),
                model.getProjectList(), model.getCompletedProjectList());
        assertCommandSuccess(new OverallStatsCommand(), model, commandHistory, expectedMessage, expectedModel);
    }
}
