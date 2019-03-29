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
 * Selects a place identified using it's displayed index from the travel book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_ALIAS = "s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the place identified by the index number used in the displayed place list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PLACE_SUCCESS = "Selected Place: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Place> filteredPlaceList = model.getFilteredPlaceList();

        if (targetIndex.getZeroBased() >= filteredPlaceList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
        }

        model.setSelectedPlace(filteredPlaceList.get(targetIndex.getZeroBased()));
        model.setChartDisplayed(false);
        return new CommandResult(String.format(MESSAGE_SELECT_PLACE_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
