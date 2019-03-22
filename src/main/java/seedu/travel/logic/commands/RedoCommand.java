package seedu.travel.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.travel.model.Model.PREDICATE_SHOW_ALL_PLACES;

import seedu.travel.logic.CommandHistory;
import seedu.travel.logic.commands.exceptions.CommandException;
import seedu.travel.model.Model;

/**
 * Reverts the {@code model}'s travel book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String COMMAND_ALIAS = "r";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoTravelBuddy()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoTravelBuddy();
        model.updateFilteredPlaceList(PREDICATE_SHOW_ALL_PLACES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
