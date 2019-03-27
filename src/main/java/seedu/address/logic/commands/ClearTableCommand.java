package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ClearTableCommand extends Command {

    public static final String COMMAND_WORD = "clearTable";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove table(s) to the RestOrRant application.\n"
            + "Restaurant must be fully unoccupied when this command is being used."
            + "Parameters: TABLE_NUMBER [TABLE_NUMBER]...\n" + "Example: " + COMMAND_WORD + " 2 3 4";

    public static final String MESSAGE_SUCCESS = "Tables have been cleared";

    public static final String INVALID_RESTORRANT_STATE =
            "Restaurant is still occupied. Clearing of table(s) not allowed";

    /**
     * Creates a ClearTableCommand to remove all table(s).
     */
    public ClearTableCommand() { }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        StringBuilder sbFinalOutput = new StringBuilder(MESSAGE_SUCCESS);
        if (!model.isRestaurantEmpty()) {
            throw new CommandException(INVALID_RESTORRANT_STATE);
        }

        model.setTables(new ArrayList<>());

        return new CommandResult(sbFinalOutput.toString());
    }
}
