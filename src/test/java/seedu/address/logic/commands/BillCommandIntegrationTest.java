package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import org.junit.Before;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class BillCommandIntegrationTest {
    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    }

    /**
    @Test
    public void execute_newDailyRevenue_success() {
        DailyRevenue validDailyRevenue = new StatisticsBuilder().build();
        Bill bill = new StatisticsBuilder().buildBill();

        Model expectedModel = new ModelManager(model.getRestOrRant(), new UserPrefs());
        expectedModel.addDailyRevenue(validDailyRevenue);

        assertCommandSuccess(Mode.BILL_MODE, new BillCommand(bill), model, commandHistory,
                String.format(BillCommand.MESSAGE_SUCCESS, bill), expectedModel);
    }
    **/
}
