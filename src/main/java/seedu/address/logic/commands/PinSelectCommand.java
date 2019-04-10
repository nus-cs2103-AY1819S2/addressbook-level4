package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Selects a person identified using it's displayed index from the pin book.
 */
public class PinSelectCommand extends Command {

    public static final String COMMAND_WORD = "pinselect";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the pinned person identified by the index number used in the displayed pin list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PIN_PERSON_SUCCESS = "Selected Pin Person: %1$s\n"
            + "Displaying main person list!";

    private final Index targetIndex;

    public PinSelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> pinnedPersonList = model.getFilteredPinnedPersonList();

        if (targetIndex.getZeroBased() >= pinnedPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.setSelectedPerson(null);
        model.setSelectedArchivedPerson(null);
        model.setSelectedPinPerson(pinnedPersonList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_PIN_PERSON_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean requiresMainList() {
        return false;
    }

    @Override
    public boolean requiresArchiveList() {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PinSelectCommand // instanceof handles nulls
                && targetIndex.equals(((PinSelectCommand) other).targetIndex)); // state check
    }

}
