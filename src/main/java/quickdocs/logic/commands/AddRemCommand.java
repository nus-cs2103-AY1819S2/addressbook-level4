package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.parser.AddRemCommandParser;
import quickdocs.model.reminder.Reminder;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;

/**
 * Adds a reminder to quickdocs.
 */
public class AddRemCommand extends Command {

    public static final String COMMAND_WORD = "addrem";
    public static final String COMMAND_ALIAS = "ar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to quickdocs. "
            + "Parameters: "
            + AddRemCommandParser.PREFIX_TITLE + "TITLE "
            + AddRemCommandParser.PREFIX_DATE + "DATE "
            + AddRemCommandParser.PREFIX_START + "START "
            + "[" + AddRemCommandParser.PREFIX_END + "END] "
            + "[" + AddRemCommandParser.PREFIX_COMMENT + "COMMENT]\n"
            + "Example: " + COMMAND_WORD + " "
            + AddRemCommandParser.PREFIX_TITLE + "Refill MedicineA "
            + AddRemCommandParser.PREFIX_DATE + "2019-05-22 "
            + AddRemCommandParser.PREFIX_START + "12:00 "
            + AddRemCommandParser.PREFIX_END + "13:00 "
            + AddRemCommandParser.PREFIX_COMMENT + "<any comments>\n";


    public static final String MESSAGE_SUCCESS = "Reminder added:\n%1$s\n";
    public static final String MESSAGE_DUPLICATE_REM = "This reminder has already been added";

    private final Reminder toAdd;

    /**
     * Creates an AddRemCommand to add the specified {@code Reminder}
     */
    public AddRemCommand(Reminder toAdd) {
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.duplicateRem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_REM);
        }

        model.addRem(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRemCommand // instanceof handles nulls
                && toAdd.equals(((AddRemCommand) other).toAdd));
    }
}
