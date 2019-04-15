package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_CLASH;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_TASK;
import static seedu.address.commons.core.Messages.MESSAGE_TIME_CLASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.datetime.TimeCustom;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.task.LinkedPatient;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;

/**
 * Edits the details of an existing task in the address book.
 */
public class TaskEditCommand extends Command {

    public static final String COMMAND_WORD = "taskedit";
    public static final String COMMAND_WORD2 = "tedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD2
            + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_STARTDATE + "START DATE] "
            + "[" + PREFIX_ENDDATE + "END DATE] "
            + "[" + PREFIX_STARTTIME + "START TIME] "
            + "[" + PREFIX_ENDTIME + "END TIME] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TITLE + "Follow up process for John Doe "
            + PREFIX_ENDDATE + "25-12-2020";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public TaskEditCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor, model);

        if (editedTask.hasDateClash()) {
            throw new CommandException(MESSAGE_DATE_CLASH);
        }

        if (editedTask.hasTimeClash()) {
            throw new CommandException(MESSAGE_TIME_CLASH);
        }

        if (!taskToEdit.isSameTask(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.setTask(taskToEdit, editedTask);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask.getTitle()));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor, Model model)
            throws CommandException {
        assert taskToEdit != null;

        Title updatedTitle = editTaskDescriptor.getTitle().orElse(taskToEdit.getTitle());
        DateCustom updatedStartDate = editTaskDescriptor.getStartDate().orElse(taskToEdit.getStartDate());
        DateCustom updatedEndDate = editTaskDescriptor.getEndDate().orElse(taskToEdit.getEndDate());
        TimeCustom updatedStartTime = editTaskDescriptor.getStartTime().orElse(taskToEdit.getStartTime());
        TimeCustom updatedEndTime = editTaskDescriptor.getEndTime().orElse(taskToEdit.getEndTime());
        Priority updatedPriority = editTaskDescriptor.getPriority().orElse(taskToEdit.getPriority());
        LinkedPatient updatedLinkedPatient = taskToEdit.getLinkedPatient();
        Index targetIndex = editTaskDescriptor.getPatientIndex().orElse(null);
        if (targetIndex != null) {
            if (targetIndex.getZeroBased() != 0) {
                int actualIndex = targetIndex.getZeroBased() - 1;
                List<Person> lastShownList = model.getFilteredPersonList();
                if (actualIndex >= lastShownList.size()) {
                    throw new CommandException(LinkedPatient.MESSAGE_CONSTRAINTS);
                }
                Person targetPerson = lastShownList.get(actualIndex);
                Patient targetPatient = (Patient) targetPerson;
                updatedLinkedPatient = new LinkedPatient(targetPatient.getName(), ((Patient) targetPerson).getNric());
            } else {
                updatedLinkedPatient = null;
            }
        }

        return new Task(updatedTitle, updatedStartDate, updatedEndDate, updatedStartTime,
                updatedEndTime, updatedPriority, updatedLinkedPatient);
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
        return index.equals(e.index)
                && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Title title;
        private DateCustom startDate;
        private DateCustom endDate;
        private TimeCustom startTime;
        private TimeCustom endTime;
        private Priority priority;
        private Index patientIndex;
        private LinkedPatient linkedPatient;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setTitle(toCopy.title);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
            setStartTime(toCopy.startTime);
            setEndTime(toCopy.endTime);
            setPriority(toCopy.priority);
            setPatientIndex(toCopy.patientIndex);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, startDate, endDate, startTime, endTime, priority, patientIndex);
        }

        /**
         * Returns true if the start date and end dates clash
         */

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

        public void setStartTime(TimeCustom startTime) {
            this.startTime = startTime;
        }

        public Optional<TimeCustom> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setEndTime(TimeCustom endTime) {
            this.endTime = endTime;
        }

        public Optional<TimeCustom> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Index> getPatientIndex() {
            return Optional.ofNullable(patientIndex);
        }

        public void setPatientIndex(Index index) {
            this.patientIndex = index;
        }

        //For testing
        public void setLinkedPatient(LinkedPatient linkedPatient) {
            this.linkedPatient = linkedPatient;
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
