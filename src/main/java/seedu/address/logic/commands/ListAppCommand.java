package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ListAppCommandParser.FORMAT_DAY;
import static seedu.address.logic.parser.ListAppCommandParser.PREFIX_DATE;
import static seedu.address.logic.parser.ListAppCommandParser.PREFIX_FORMAT;
import static seedu.address.logic.parser.ListAppCommandParser.PREFIX_NRIC;

import java.time.LocalDate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.patient.Nric;

/**
 * Lists all appointments in the address book to the user.
 */
public class ListAppCommand extends Command {

    public static final String COMMAND_WORD = "listapp";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all appointments. "
            + "Parameters: "
            + "[" + PREFIX_FORMAT + "FORMAT] "
            + "[" + PREFIX_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FORMAT + "day "
            + PREFIX_DATE + "2019-10-23\n"
            + "OR\n"
            + "[" + PREFIX_NRIC + "NRIC]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S9637645A";
    public static final String MESSAGE_SUCCESS = "Listed all appointments\n";

    private final String format;
    private final LocalDate date;
    private final Nric nric;

    public ListAppCommand() {
        format = FORMAT_DAY;
        date = LocalDate.now();
        nric = null;
    }

    public ListAppCommand(String format, LocalDate date) {
        this.format = format;
        this.date = date;
        nric = null;
    }

    public ListAppCommand(Nric nric) {
        this.nric = nric;
        format = "";
        date = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String result;
        if (!format.isEmpty()) {
            result = model.listApp();
        } else {
            result = model.listApp();
        }

        return new CommandResult(MESSAGE_SUCCESS + result, false, false);
    }
}
