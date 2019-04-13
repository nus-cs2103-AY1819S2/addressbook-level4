package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;
import static quickdocs.logic.parser.ListAppCommandParser.PREFIX_DATE;
import static quickdocs.logic.parser.ListAppCommandParser.PREFIX_FORMAT;
import static quickdocs.logic.parser.ListAppCommandParser.PREFIX_NRIC;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;

/**
 * Lists filtered {@code Appointment}(s) on the main display of the UI.
 */
public class ListAppCommand extends Command {

    public static final String COMMAND_WORD = "listapp";
    public static final String COMMAND_ALIAS = "la";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists filtered appointments.\n"
            + "Parameters: "
            + PREFIX_FORMAT + "FORMAT "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FORMAT + "day "
            + PREFIX_DATE + "2019-10-23\n"
            + "OR\n"
            + "Parameters:" + PREFIX_NRIC + "NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S9637645A";
    public static final String MESSAGE_SUCCESS_BY_DATE =
            "Listing all appointments from %1$s to %2$s:\n"
            + "============================================\n";
    public static final String MESSAGE_SUCCESS_BY_NRIC =
            "Listing all appointments for %1$s:\n"
            + "============================================\n";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "No patient with the given NRIC found";

    private final LocalDate start;
    private final LocalDate end;
    private final Nric nric;

    /**
     * Creates a {@code ListAppCommand} to list {@code Appointment}(s) in the given search range of dates.
     */
    public ListAppCommand(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
        nric = null;
    }

    /**
     * Creates a {@code ListAppCommand} to list {@code Appointment}(s) made by a {@code Patient} with
     * the given {@code Nric}.
     */
    public ListAppCommand(Nric nric) {
        this.nric = nric;
        start = null;
        end = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (Optional.ofNullable(start).isPresent()) {
            // list appointments in given search range of dates
            assert end != null;

            String result = model.listApp(start, end);
            return new CommandResult(String.format(MESSAGE_SUCCESS_BY_DATE, start, end) + result, false, false);
        } else {
            // list appointments made by the given patient
            assert nric != null;

            Optional<Patient> patientToList = model.getPatientByNric(nric);
            if (!patientToList.isPresent()) {
                throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
            }

            String result = model.listApp(patientToList.get());
            return new CommandResult(String.format(MESSAGE_SUCCESS_BY_NRIC, patientToList.get().getName()) + result,
                    false, false);
        }
    }

    @Override
    public boolean equals(Object other) {
        // Objects.equals() to handle null fields
        return other == this // short circuit if same object
                || (other instanceof ListAppCommand // instanceof handles nulls
                && Objects.equals(nric, ((ListAppCommand) other).nric)
                && Objects.equals(start, ((ListAppCommand) other).start)
                && Objects.equals(end, ((ListAppCommand) other).end));
    }
}
