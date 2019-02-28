package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.categories.Cuisine;

public class SetCuisineCommand extends Command {

    public static final String COMMAND_WORD = "setCuisine";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets cuisine of the restaurant identified by the index number used in the displayed restaurant list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[CUISINE]\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SET_CUISINE_SUCCESS = "Cuisine Set for Restaurant: %1$s";

    private final Index index;
    private final Cuisine cuisine;

    public SetCuisineCommand(Index index, Cuisine cuisine) {
        requireNonNull(index);
        requireNonNull(cuisine);

        this.index = index;
        this.cuisine = cuisine;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
