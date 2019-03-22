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
 * Deletes multiple places ranging from start index to end index in the travel book.
 */
public class DeleteMultipleCommand extends Command {

    public static final String COMMAND_WORD = "deletem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes multiple places identified by the start and end index number in the last place listing.\n"
            + "Parameters: START_INDEX END_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1" + " 5";

    public static final String MESSAGE_DELETE_PLACE_SUCCESS = "Deleted Place: ";

    private Index targetStartIndex;
    private Index targetEndIndex;
    private StringBuilder deletedPlacesList;

    public DeleteMultipleCommand(Index startIndex, Index endIndex) {
        this.targetStartIndex = startIndex;
        this.targetEndIndex = endIndex;
        this.deletedPlacesList = new StringBuilder();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Place> lastShownList = model.getFilteredPlaceList();

        if (targetStartIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
        }

        if (targetEndIndex.getZeroBased() >= lastShownList.size()) {
            this.targetEndIndex = Index.fromOneBased(lastShownList.size());
        }

        int numOfPlacesToDelete = targetEndIndex.getOneBased() - targetStartIndex.getZeroBased();
        int startIndex = targetStartIndex.getZeroBased();

        for (int i = 0; i < numOfPlacesToDelete; i++) {
            Place placeToDelete = lastShownList.get(startIndex);
            model.deletePlace(placeToDelete);
            buildDeletedPlacesList(placeToDelete);
        }
        model.commitTravelBuddy();
        return new CommandResult(deletedPlacesList.toString());
    }

    private void buildDeletedPlacesList(Place target) {
        this.deletedPlacesList.append(MESSAGE_DELETE_PLACE_SUCCESS);
        this.deletedPlacesList.append(target);
        this.deletedPlacesList.append("\n");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMultipleCommand // instanceof handles nulls
                && targetStartIndex.equals(((DeleteMultipleCommand) other).targetStartIndex)) // state check
                && targetEndIndex.equals(((DeleteMultipleCommand) other).targetEndIndex); // state check
    }
}

