package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.description.Description;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.Record;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;

/**
 * Sets a task identified using it's displayed index to be completed.
 * Adds a record to a patient linked to it if any.
 */
public class TaskDoneCommand extends Command {

    public static final String COMMAND_WORD = "taskdone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the task to be completed. Completing a task with a patient linked to it will automatically"
            + " add a new record to the patient's record list\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_TASK_DONE_SUCCESS = "Completed Task: \n%1$s";

    private final Index targetIndex;

    public TaskDoneCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();
        String patientRecordAddedMessage = "";

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToComplete = lastShownList.get(targetIndex.getZeroBased());
        if (taskToComplete.getPriority() == Priority.COMPLETED) {
            throw new CommandException("The task is already completed. ");
        }
        Task completedTask = new Task(taskToComplete,true);
        completedTask.setPriorityComplete();
        model.setTask(taskToComplete, completedTask);
        if (taskToComplete.getLinkedPatient() != null) {
            Optional<Patient> found = model.getAddressBook().getPersonList().stream().map(x -> (Patient) x)
                    .filter(y -> y.getNric().getNric().equals(taskToComplete.getLinkedPatient()
                            .getLinkedPatientNric())).findFirst();
            if (found.isPresent()) {
                Patient replacement = found.get();
                replacement.addRecord(new Record(new Description(completedTask.getTitle().title)));
                model.setPerson(found.get(), replacement);
                patientRecordAddedMessage = String.format("\n Added Record to Patient: %s ( %s )",
                        found.get().getName().fullName, found.get().getNric().getNric());
            } else {
                patientRecordAddedMessage = "\n Linked Patient not found. Record not added.";
            }
        } else {
            patientRecordAddedMessage = "\n Task not linked to any patients. No Records are added.";
        }
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_TASK_DONE_SUCCESS + patientRecordAddedMessage, completedTask));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDoneCommand // instanceof handles nulls
                && targetIndex.equals(((TaskDoneCommand) other).targetIndex)); // state check
    }
}
