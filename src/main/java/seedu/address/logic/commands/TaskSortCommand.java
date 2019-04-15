package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.Comparator;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.datetime.TimeCustom;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;

/**
 * Edits the details of an existing task in the address book.
 */
public class TaskSortCommand extends Command {

    public static final String COMMAND_WORD = "tasksort";
    public static final String COMMAND_WORD2 = "tsort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD2
            + ": sort tasks in place by start time,"
            + " end time or priority. Sort ascendingly by default.\n"
            + "Parameters: sortField [sortOrder]\n"
            + "Example 1: tasksort starttime\n"
            + "Example 2: tasksort priority desc";
    public static final String MESSAGE_SORT_TASK_SUCCESS = "Tasks sorted by %s in %s order";
    public static final String MESSAGE_SORT_TASK_WRONG_FILED = "Supporting fields are: starttime, endtime, priority.";

    private final String sortField;
    private final boolean isAscending;
    private final Comparator<Task> sortComparator;

    /**
     * @param sortField start date, end date or priority
     * @param isAscending true for ascending order
     */
    public TaskSortCommand(String sortField, boolean isAscending) throws ParseException {
        requireNonNull(sortField);

        this.sortField = sortField;
        this.isAscending = isAscending;
        this.sortComparator = createComparator(sortField, isAscending);
    }

    /**
     *
     * @param sortField
     * @param isAscending
     * @return the corresponding comparator to sortfield and is Ascending
     * @throws CommandException
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
     *
     * @param d1 Date of task1
     * @param d2 Date of task2
     * @param t1 Time of task1
     * @param t2 Time of task2
     * @return 1 if task1 is later, -1 earlier, 0 the same time
     */
    private int compareDateTime(DateCustom d1, DateCustom d2, TimeCustom t1, TimeCustom t2) {
        int dateCompare = d1.compareTo(d2);
        if (dateCompare == 0) {
            return t1.compareTo(t2);
        }
        return dateCompare;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.sortTasks(sortComparator);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitAddressBook();
        return new CommandResult(
                String.format(MESSAGE_SORT_TASK_SUCCESS, sortField, isAscending ? "ascending" : "descending"));
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskSortCommand)) {
            return false;
        }

        // state check
        TaskSortCommand s = (TaskSortCommand) other;
        return s.sortField.equals(sortField) && s.isAscending == isAscending;
    }
}
