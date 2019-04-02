package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;

/**
 * Edits the details of an existing task in the address book.
 */
public class TaskSortCommand extends Command {

    public static final String COMMAND_WORD = "taskedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sort tasks in place by start time,"
                                                            + " end time or priority. Sort ascendingly by default.\n"
                                                            + "Parameters: sortField [sortOrder]\n"
                                                            + "Example 1: tasksort starttime\n"
                                                            + "Example 2: tasksort priority desc";
    public static final String MESSAGE_SORT_TASK_SUCCESS = "Tasks sorted by %s in %s order";
    public static final String MESSAGE_SORT_TASK_WRONG_FILED = "Supporting fields are: starttime, endtime, priority.";


    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
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

        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

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

        model.sortTasks(sortComparator);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitAddressBook();
        return new CommandResult(
                String.format(MESSAGE_SORT_TASK_SUCCESS, sortField, isAscending ? "ascending" : "descending"));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Title updatedTitle = editTaskDescriptor.getTitle().orElse(taskToEdit.getTitle());
        DateCustom updatedStartDate = editTaskDescriptor.getStartDate().orElse(taskToEdit.getStartDate());
        DateCustom updatedEndDate = editTaskDescriptor.getEndDate().orElse(taskToEdit.getEndDate());

        return new Task(updatedTitle, updatedStartDate, updatedEndDate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskEditCommand)) {
            return false;
        }

        // state check
        TaskEditCommand e = (TaskEditCommand) other;
        return true;
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Title title;
        private DateCustom startDate;
        private DateCustom endDate;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setTitle(toCopy.title);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, startDate, endDate);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setStartDate(DateCustom startDate) {
            this.startDate = startDate;
        }

        public Optional<DateCustom> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(DateCustom endDate) {
            this.endDate = endDate;
        }

        public Optional<DateCustom> getEndDate() {
            return Optional.ofNullable(endDate);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate());
        }
    }
}
