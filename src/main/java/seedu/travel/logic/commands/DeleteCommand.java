package seedu.travel.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.travel.commons.core.Messages;
import seedu.travel.commons.core.index.Index;
import seedu.travel.logic.CommandHistory;
import seedu.travel.logic.commands.exceptions.CommandException;
import seedu.travel.model.Model;
import seedu.travel.model.place.Place;

/**
 * Deletes a place identified using it's displayed index from the travel book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the place identified by the index number used in the displayed place list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PLACE_SUCCESS = "Deleted Place: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Place> lastShownList = model.getFilteredPlaceList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
        }

        Place placeToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePlace(placeToDelete);
        model.commitTravelBuddy();
        return new CommandResult(String.format(MESSAGE_DELETE_PLACE_SUCCESS, placeToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
