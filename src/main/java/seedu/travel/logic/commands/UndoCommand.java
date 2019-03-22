package seedu.travel.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.travel.model.Model.PREDICATE_SHOW_ALL_PLACES;

import seedu.travel.logic.CommandHistory;
import seedu.travel.logic.commands.exceptions.CommandException;
import seedu.travel.model.Model;

/**
 * Reverts the {@code model}'s travel book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_ALIAS = "u";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoTravelBuddy()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoTravelBuddy();
        model.updateFilteredPlaceList(PREDICATE_SHOW_ALL_PLACES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
