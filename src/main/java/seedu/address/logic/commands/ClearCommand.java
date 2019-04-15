package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UiCommandInteraction;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PersonnelDatabase;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Personnel database has been cleared!";

    public final String userName;

    public ClearCommand(String userName) {
        this.userName = userName;
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setPersonnelDatabase(new PersonnelDatabase());
        model.commitPersonnelDatabase();
        if ("Admin".equals(userName)) {
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_SUCCESS, UiCommandInteraction.EXIT);
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);
    }
}
