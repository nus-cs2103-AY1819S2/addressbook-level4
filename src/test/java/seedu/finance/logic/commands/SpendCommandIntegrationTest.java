package seedu.finance.logic.commands;

import static seedu.finance.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import org.junit.Before;
import org.junit.Test;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;
import seedu.finance.model.record.Record;
import seedu.finance.testutil.RecordBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class SpendCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    }

    @Test
    public void execute_newRecord_success() {
        Record validRecord = new RecordBuilder().build();

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.addRecord(validRecord);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new SpendCommand(validRecord), model, commandHistory,
                String.format(SpendCommand.MESSAGE_SUCCESS, validRecord), expectedModel);
    }

    @Test
    public void execute_duplicateRecord_success() {
        Record recordInList = model.getFinanceTracker().getRecordList().get(0);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.addRecord(recordInList);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new SpendCommand(recordInList), model, commandHistory,
                String.format(SpendCommand.MESSAGE_SUCCESS, recordInList), expectedModel);
    }

}
