package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;
import static quickdocs.logic.parser.FreeAppCommandParser.PREFIX_DATE;
import static quickdocs.logic.parser.FreeAppCommandParser.PREFIX_FORMAT;

import java.time.LocalDate;

import quickdocs.logic.CommandHistory;
import quickdocs.model.Model;

/**
 * Lists all free appointment slots in QuickDocs on the main display of the UI.
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
    public static final String MESSAGE_NO_FREE_SLOTS = "There are no free appointment slots from %1$s to %2$s\n";

    private final LocalDate start;
    private final LocalDate end;

    /**
     * Creates a {@code FreeAppCommand} to list free appointment slots within given search range.
     */
    public FreeAppCommand(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        String result = model.freeApp(start, end);
        if (result.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_FREE_SLOTS, start, end) + result + "\n",
                    false, false);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, start, end) + result + "\n",
                false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FreeAppCommand // instanceof handles nulls
                && start.equals(((FreeAppCommand) other).start)
                && end.equals(((FreeAppCommand) other).end));
    }
}
