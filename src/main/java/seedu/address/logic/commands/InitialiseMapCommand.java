package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAP_SIZE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Initialise map to size input by user.
 */
public class InitialiseMapCommand extends Command {

    public static final String COMMAND_WORD = "init";
    public static final String COMMAND_ALIAS = "i";

    public static final int MAXIMUM_MAP_SIZE = 10;
    public static final int MINIMUM_MAP_SIZE = 5;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Initialise the map to specified size. "
            + "Parameters: "
            + PREFIX_MAP_SIZE + "MAPSIZE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MAP_SIZE + "8 ";

    public static final String MESSAGE_SUCCESS = "Map initialised to size: %d";
    public static final String MESSAGE_INVALID_MAP_SIZE = "Map size must be between %d to %d, inclusive.";

    private final int mapSize;

    /**
     * Initialise map command
     */
    public InitialiseMapCommand(int size) {
        mapSize = size;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (mapSize > MAXIMUM_MAP_SIZE || mapSize < MINIMUM_MAP_SIZE) {
            throw new CommandException(String.format(MESSAGE_INVALID_MAP_SIZE, MINIMUM_MAP_SIZE, MAXIMUM_MAP_SIZE));
        }

        model.getMapGrid().initialise(mapSize);
        model.updateUi();

        return new CommandResult(String.format(MESSAGE_SUCCESS, mapSize));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InitialiseMapCommand); // instanceof handles nulls
                //&& toAdd.equals(((InitialiseMapCommand) other).toAdd));
    }
}
