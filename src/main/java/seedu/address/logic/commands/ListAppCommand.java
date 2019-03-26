package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ListAppCommandParser.PREFIX_DATE;
import static seedu.address.logic.parser.ListAppCommandParser.PREFIX_FORMAT;
import static seedu.address.logic.parser.ListAppCommandParser.PREFIX_NRIC;

import java.time.LocalDate;
import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

/**
 * Lists all appointments in the address book to the user.
 */
public class ListAppCommand extends Command {

    public static final String COMMAND_WORD = "listapp";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all appointments.\n"
            + "Parameters: "
            + "[" + PREFIX_FORMAT + "FORMAT] "
            + "[" + PREFIX_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FORMAT + "day "
            + PREFIX_DATE + "2019-10-23\n"
            + "OR\n"
            + "Parameters: [" + PREFIX_NRIC + "NRIC]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S9637645A";
    public static final String MESSAGE_SUCCESS_BY_DATE = "Listed all appointments from %1$s to %2$s\n";
    public static final String MESSAGE_SUCCESS_BY_NRIC = "Listed all appointments for %1$s\n";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "No patient with the given nric found";

    private final LocalDate start;
    private final LocalDate end;
    private final Nric nric;

    public ListAppCommand() {
        start = LocalDate.now();
        end = LocalDate.now();
        nric = null;
    }

    public ListAppCommand(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
        nric = null;
    }

    public ListAppCommand(Nric nric) {
        this.nric = nric;
        start = null;
        end = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (start != null) {
            System.out.println(start);
            System.out.println(end);
            String result = model.listApp(start, end);
            return new CommandResult(String.format(MESSAGE_SUCCESS_BY_DATE, start, end) + result, false, false);
        } else {
            Optional<Patient> patientToList = model.getPatientWithNric(nric);
            if (!patientToList.isPresent()) {
                throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
            }
            String result = model.listApp(patientToList.get());
            return new CommandResult(String.format(MESSAGE_SUCCESS_BY_NRIC, patientToList.get().getName()) + result,
                    false, false);
        }
    }
}
