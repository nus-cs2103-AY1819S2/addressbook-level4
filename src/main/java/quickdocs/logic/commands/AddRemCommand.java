package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;
import static quickdocs.logic.parser.AddRemCommandParser.PREFIX_COMMENT;
import static quickdocs.logic.parser.AddRemCommandParser.PREFIX_DATE;
import static quickdocs.logic.parser.AddRemCommandParser.PREFIX_END;
import static quickdocs.logic.parser.AddRemCommandParser.PREFIX_START;
import static quickdocs.logic.parser.AddRemCommandParser.PREFIX_TITLE;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.reminder.Reminder;

/**
 * Adds a {@code Reminder} to QuickDocs.
 */
public class AddRemCommand extends Command {

    public static final String COMMAND_WORD = "addrem";
    public static final String COMMAND_ALIAS = "ar";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to QuickDocs. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DATE + "DATE "
            + PREFIX_START + "START "
            + "[" + PREFIX_END + "END] "
            + "[" + PREFIX_COMMENT + "COMMENT]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Check financial records "
            + PREFIX_DATE + "2019-05-22 "
            + PREFIX_START + "18:00 "
            + PREFIX_END + "18:30 "
            + PREFIX_COMMENT + "Weekly check\n";

    public static final String MESSAGE_SUCCESS = "Reminder added:\n%1$s\n";
    public static final String MESSAGE_DUPLICATE_REM = "This reminder has already been added";

    private final Reminder toAdd;

    /**
     * Creates an {@code AddRemCommand} to add the specified {@code Reminder}
     */
    public AddRemCommand(Reminder toAdd) {
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // check if there is a duplicate reminder already added
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
