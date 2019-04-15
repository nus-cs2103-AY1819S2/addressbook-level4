package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s food diary to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoFoodDiary()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoFoodDiary();
        model.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
