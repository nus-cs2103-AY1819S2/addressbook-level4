package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;

/**
 * Selects an activity identified using it's displayed index from the address book.
 */
public class ActivitySelectCommand extends ActivityCommand {
    public static final String COMMAND_WORD = "activitySelect";
    public static final String COMMAND_ALIAS = "aSelect";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the activity identified by the index number used in the displayed activity list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_ACTIVITY_SUCCESS = "Selected Activity: %1$s";

    private final Index targetIndex;

    public ActivitySelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Activity> filteredActivityList = model.getFilteredActivityList();

        if (targetIndex.getZeroBased() >= filteredActivityList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        model.setSelectedActivity(filteredActivityList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_ACTIVITY_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivitySelectCommand // instanceof handles nulls
                && targetIndex.equals(((ActivitySelectCommand) other).targetIndex)); // state check
    }
}
