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
 * Selects an archived person identified using its displayed index from the archive book list.
 */
public class ArchiveSelectCommand extends Command {

    public static final String COMMAND_WORD = "archiveselect";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the archived person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Archived Person: %1$s";

    private final Index targetIndex;

    public ArchiveSelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> filteredArchivedPersonList = model.getFilteredArchivedPersonList();

        if (targetIndex.getZeroBased() >= filteredArchivedPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.setSelectedPinPerson(null);
        model.setSelectedArchivedPerson(filteredArchivedPersonList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()),
                false, false, true);

    }

    @Override
    public boolean requiresMainList() {
        return false;
    }

    @Override
    public boolean requiresArchiveList() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArchiveSelectCommand // instanceof handles nulls
                && targetIndex.equals(((ArchiveSelectCommand) other).targetIndex)); // state check
    }
}
