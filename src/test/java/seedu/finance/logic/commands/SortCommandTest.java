package seedu.finance.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.finance.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.finance.testutil.TypicalRecords.APPLE;
import static seedu.finance.testutil.TypicalRecords.BANANA;
import static seedu.finance.testutil.TypicalRecords.CAP;
import static seedu.finance.testutil.TypicalRecords.DONUT;
import static seedu.finance.testutil.TypicalRecords.EARRINGS;
import static seedu.finance.testutil.TypicalRecords.FRUITS;
import static seedu.finance.testutil.TypicalRecords.GIFT;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import java.util.Arrays;

import org.junit.Test;

import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.logic.parser.RecordAmountComparator;
import seedu.finance.logic.parser.RecordCategoryComparator;
import seedu.finance.logic.parser.RecordDateComparator;
import seedu.finance.logic.parser.RecordNameComparator;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_sortByName_success() {
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.sortFilteredRecordList(new RecordNameComparator());
        expectedModel.commitFinanceTracker();
        assertCommandSuccess(new SortCommand(new RecordNameComparator()), model, commandHistory,
                SortCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(Arrays.asList(APPLE, BANANA, CAP, DONUT, EARRINGS, FRUITS, GIFT), model.getFilteredRecordList());
    }

    @Test
    public void execute_sortByAmount_success() {
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.sortFilteredRecordList(new RecordAmountComparator());
        expectedModel.commitFinanceTracker();
        assertCommandSuccess(new SortCommand(new RecordAmountComparator()), model, commandHistory,
                SortCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(Arrays.asList(GIFT, FRUITS, CAP, EARRINGS, BANANA, APPLE, DONUT), model.getFilteredRecordList());
    }

    @Test
    public void execute_sortByDate_success() {
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.sortFilteredRecordList(new RecordDateComparator());
        expectedModel.commitFinanceTracker();
        assertCommandSuccess(new SortCommand(new RecordDateComparator()), model, commandHistory,
                SortCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(Arrays.asList(GIFT, EARRINGS, CAP, APPLE, FRUITS, BANANA, DONUT), model.getFilteredRecordList());

    }

    @Test
    public void execute_sortByCategory_success() {
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.sortFilteredRecordList(new RecordCategoryComparator());
        expectedModel.commitFinanceTracker();
        assertCommandSuccess(new SortCommand(new RecordCategoryComparator()), model, commandHistory,
                SortCommand.MESSAGE_SUCCESS, expectedModel);

        assertEquals(Arrays.asList(CAP, EARRINGS, FRUITS, GIFT, BANANA, DONUT, APPLE), model.getFilteredRecordList());
    }

    @Test
    public void executeUndoRedo_listSortedByAmount_success() throws CommandException {
        assertEquals(Arrays.asList(APPLE, BANANA, CAP, DONUT, EARRINGS, FRUITS, GIFT), model.getFilteredRecordList());

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.sortFilteredRecordList(new RecordAmountComparator());
        expectedModel.commitFinanceTracker();

        SortCommand sortCommand = new SortCommand(new RecordAmountComparator());
        sortCommand.execute(model, commandHistory);

        assertEquals(Arrays.asList(GIFT, FRUITS, CAP, EARRINGS, BANANA, APPLE, DONUT), model.getFilteredRecordList());

        // undo -> reverse finance tracker back to previous state (before sort was executed)
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(Arrays.asList(APPLE, BANANA, CAP, DONUT, EARRINGS, FRUITS, GIFT), model.getFilteredRecordList());

        // redo -> list is sorted by amount again
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(Arrays.asList(GIFT, FRUITS, CAP, EARRINGS, BANANA, APPLE, DONUT), model.getFilteredRecordList());
    }

    // redo undo tests
    // reverse

}
