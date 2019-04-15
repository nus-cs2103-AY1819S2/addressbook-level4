package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TaskSortCommand.MESSAGE_SORT_TASK_SUCCESS;
import static seedu.address.logic.commands.TaskSortCommand.MESSAGE_SORT_TASK_WRONG_FILED;
import static seedu.address.testutil.TypicalData.CLEANING;
import static seedu.address.testutil.TypicalData.EXTRACT;
import static seedu.address.testutil.TypicalData.REORG;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.datetime.TimeCustom;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;

public class TaskSortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_sortParameters_orderDefault() throws ParseException {
        execute_sortTaskParameter("starttime", true, Arrays.asList(CLEANING, EXTRACT, REORG));
        execute_sortTaskParameter("endtime", true, Arrays.asList(EXTRACT, CLEANING, REORG));
        execute_sortTaskParameter("priority", true, Arrays.asList(CLEANING, EXTRACT, REORG));
    }

    @Test
    public void execute_sortParameters_orderReverse() throws ParseException {
        execute_sortTaskParameter("starttime", false, Arrays.asList(REORG, EXTRACT, CLEANING));
        execute_sortTaskParameter("endtime", false, Arrays.asList(REORG, CLEANING, EXTRACT));
        execute_sortTaskParameter("priority", false, Arrays.asList(REORG, EXTRACT, CLEANING));
    }

    /**
     * Wrap up method for testing tasksort
     * @param sortField
     * @param isAscending
     * @param expectedList
     * @throws ParseException
     */
    private void execute_sortTaskParameter(String sortField, boolean isAscending,
                                              List<Task> expectedList) throws ParseException {
        String expectedMessage = String.format(MESSAGE_SORT_TASK_SUCCESS, sortField,
                isAscending ? "ascending" : "descending");
        TaskSortCommand command = new TaskSortCommand(sortField, isAscending);
        Comparator<Task> taskComparator = createComparator(sortField, isAscending);
        model.sortTasks(taskComparator);
        model.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, model);
        assertEquals(expectedList, model.getFilteredTaskList());
    }

    /**
     * Tasksort helper method
     * @param sortField
     * @param isAscending
     * @return
     * @throws ParseException
     */
    private Comparator<Task> createComparator(String sortField, boolean isAscending) throws ParseException {
        Comparator<Task> t;
        int ascendingCoefficient = isAscending ? 1 : -1;
        if (sortField.equals("starttime")) {
            t = (Task o1, Task o2) -> {
                DateCustom d1 = o1.getStartDate();
                DateCustom d2 = o2.getStartDate();
                TimeCustom t1 = o1.getStartTime();
                TimeCustom t2 = o2.getStartTime();
                return compareDateTime(d1, d2, t1, t2) * ascendingCoefficient;
            };
        } else if (sortField.equals("endtime")) {
            t = (Task o1, Task o2) -> {
                DateCustom d1 = o1.getEndDate();
                DateCustom d2 = o2.getEndDate();
                TimeCustom t1 = o1.getEndTime();
                TimeCustom t2 = o2.getEndTime();
                return compareDateTime(d1, d2, t1, t2) * ascendingCoefficient;
            };
        } else if (sortField.equals("priority")) {
            t = (Task o1, Task o2) -> {
                Priority p1 = o1.getPriority();
                Priority p2 = o2.getPriority();
                return p1.compareTo(p2) * ascendingCoefficient;
            };
        } else {
            throw new ParseException(MESSAGE_SORT_TASK_WRONG_FILED);
        }

        return t;
    }

    /**
     * Task sort helper method
     * @param d1
     * @param d2
     * @param t1
     * @param t2
     * @return
     */
    private int compareDateTime(DateCustom d1, DateCustom d2, TimeCustom t1, TimeCustom t2) {
        int dateCompare = d1.compareTo(d2);
        if (dateCompare == 0) {
            return t1.compareTo(t2);
        }
        return dateCompare;
    }

}
