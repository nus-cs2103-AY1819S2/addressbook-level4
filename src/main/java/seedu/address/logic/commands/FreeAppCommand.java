package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ListAppCommandParser.PREFIX_DATE;
import static seedu.address.logic.parser.ListAppCommandParser.PREFIX_FORMAT;

import java.time.LocalDate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all free appointment slots in quickdocs to the user.
 */
public class FreeAppCommand extends Command {

    public static final String COMMAND_WORD = "freeapp";
    public static final String COMMAND_ALIAS = "fa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all free appointment slots.\n"
            + "Parameters: "
            + "[" + PREFIX_FORMAT + "FORMAT] "
            + "[" + PREFIX_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FORMAT + "day "
            + PREFIX_DATE + "2019-10-23";
    public static final String MESSAGE_SUCCESS = "Listed all free appointment slots from %1$s to %2$s\n";
    public static final String MESSAGE_ALL_FREE = "All appointment slots are free from %1$s to %2$s\n";

    private final LocalDate start;
    private final LocalDate end;

    public FreeAppCommand() {
        start = LocalDate.now();
        end = LocalDate.now();
    }

    public FreeAppCommand(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        String result = model.freeApp(start, end);
        if (result.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_ALL_FREE, start, end) + result, false, false);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, start, end) + result, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FreeAppCommand // instanceof handles nulls
                && start.equals(((FreeAppCommand) other).start)
                && end.equals(((FreeAppCommand) other).end));
    }
}
