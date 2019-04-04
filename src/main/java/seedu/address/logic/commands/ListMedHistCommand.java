package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDHISTS;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.MedicalHistory;

/**
 * Lists all medical histories in the docX record to the user.
 */
public class ListMedHistCommand extends Command {

    public static final String COMMAND_WORD = "list-med-hist";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List all medical histories.\n"
            + COMMAND_WORD + " "
            + PREFIX_PATIENT_ID + "PATIENT-ID "
            + ": List all medical histories of a patient.\n"
            + "Example: " + COMMAND_WORD + " or "
            + COMMAND_WORD
            + PREFIX_PATIENT_ID + "1 ";

    public static final String MESSAGE_SUCCESS_FULL_LIST = "Listed all medical histories";

    public static final String MESSAGE_SUCCESS_PID = "Listed all medical histories of patient with pid ";

    private final Optional<String> targetPatientId;

    public ListMedHistCommand() {
        this.targetPatientId = Optional.empty();
    }

    public ListMedHistCommand(String targetPatientId) {
        this.targetPatientId = Optional.of(targetPatientId);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String messageSuccess;
        if (targetPatientId.isPresent()) {
            /** {@code Predicate} that always evaluate to true */
            Predicate<MedicalHistory> PREDICATE_SHOW_MEDHISTS_OF_PID =
                    x -> { return x.getPatientId().equals(targetPatientId.get());};
            model.updateFilteredMedHistList(PREDICATE_SHOW_MEDHISTS_OF_PID);
            messageSuccess = MESSAGE_SUCCESS_PID + targetPatientId.get();
        } else {
            model.updateFilteredMedHistList(PREDICATE_SHOW_ALL_MEDHISTS);
            messageSuccess = MESSAGE_SUCCESS_FULL_LIST;
        }
        return new CommandResult(messageSuccess, CommandResult.ShowPanel.MED_HIST_PANEL);
    }
}
