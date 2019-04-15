package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Clears the tables in the restaurant if all tables are not occupied.
 */
public class ClearTablesCommand extends Command {

    public static final String COMMAND_WORD = "clearTables";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Tables have been cleared";

    public static final String INVALID_RESTAURANT_STATE =
            "Restaurant is still occupied. Clearing of table(s) not allowed";

    public static final String MESSAGE_FAILURE = "There are no tables to clear.";

    /**
     * Creates a ClearTablesCommand to remove all table(s).
     */
    public ClearTablesCommand() { }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        StringBuilder sbFinalOutput = new StringBuilder(MESSAGE_SUCCESS);
        if (model.getRestOrRant().getTables().getTableList().isEmpty()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        if (!model.isRestaurantEmpty()) {
            throw new CommandException(INVALID_RESTAURANT_STATE);
        }

        model.setTables(new ArrayList<>());

        return new CommandResult(sbFinalOutput.toString());
    }
}
