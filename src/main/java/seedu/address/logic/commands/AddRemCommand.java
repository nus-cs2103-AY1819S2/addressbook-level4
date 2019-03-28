package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.AddRemCommandParser.PREFIX_COMMENT;
import static seedu.address.logic.parser.AddRemCommandParser.PREFIX_DATE;
import static seedu.address.logic.parser.AddRemCommandParser.PREFIX_END;
import static seedu.address.logic.parser.AddRemCommandParser.PREFIX_START;
import static seedu.address.logic.parser.AddRemCommandParser.PREFIX_TITLE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;

/**
 * Adds a reminder to quickdocs.
 */
public class AddRemCommand extends Command {

    public static final String COMMAND_WORD = "addrem";
    public static final String COMMAND_ALIAS = "ar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to quickdocs. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DATE + "DATE "
            + PREFIX_START + "START "
            + "[" + PREFIX_END + "END] "
            + "[" + PREFIX_COMMENT + "COMMENT]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Refill MedicineA "
            + PREFIX_DATE + "2019-05-22 "
            + PREFIX_START + "12:00 "
            + PREFIX_END + "13:00 "
            + PREFIX_COMMENT + "<any comments>\n";


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
