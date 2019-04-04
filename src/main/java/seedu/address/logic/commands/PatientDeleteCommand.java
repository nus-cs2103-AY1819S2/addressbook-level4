package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class PatientDeleteCommand extends Command {

    public static final String COMMAND_WORD = "patientdelete";
    public static final String COMMAND_WORD2 = "pdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD2
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;

    public PatientDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        checkLinkedTasks(model, personToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    /**
     * Method to check if there are any tasks linked to the person to be deleted
     * Lets user choose if
     *
     */
    private void checkLinkedTasks(Model model, Person deletedPerson) {
        Task[] linkedTasks = model.getAddressBook().getTaskList().stream()
                .filter(y -> y.getLinkedPatient() != null)
                .filter(z -> z.getLinkedPatient().getLinkedPatientNric().equals(((Patient) deletedPerson)
                        .getNric().getNric())).toArray(Task[]::new);
        if (linkedTasks.length > 0) {
            Alert alert = new Alert(Alert.AlertType.NONE,
                    "There is one or more tasks linked to this patient.\n"
                    + "Do you want to delete them as well?\n"
                    + "Choosing NO or closing this box will set the tasks to have no linked patient.",
                    ButtonType.YES, ButtonType.NO);
            alert.setHeaderText("Tasks linked to patient detect!");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
            alert.showAndWait();
            for (Task task : linkedTasks) {
                if (alert.getResult() == ButtonType.YES) {
                    model.deleteTask(task);
                } else {
                    Task replacement = task.isCopy() ? new Task(task) : new Task(task, true);
                    replacement.setNullLinkedPatient();
                    model.setTask(task, replacement);
                }
            }
        }
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PatientDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((PatientDeleteCommand) other).targetIndex)); // state check
    }
}
