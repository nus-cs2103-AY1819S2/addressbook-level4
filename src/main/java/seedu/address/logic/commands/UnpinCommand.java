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
 * Unpins specific persons in the address book to the user.
 */
public class UnpinCommand extends Command {

    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unpins the person identified by the index number used in the displayed pinned person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNPIN_PERSON_SUCCESS = "Unpinned Person: %1$s";
    public static final String MESSAGE_UNPIN_PERSON_ALREADY = "Person: %1$s is already in the unpinned list";

    private final Index targetIndex;

    public UnpinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> pinnedPersonList = PinCommand.getPinnedPersonList();

        if (targetIndex.getZeroBased() >= pinnedPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnpin = lastShownList.get(targetIndex.getZeroBased());

        if (pinnedPersonList.contains(personToUnpin)) {
            pinnedPersonList.remove(personToUnpin);
            PinCommand.editPinnedPersonList(personToUnpin);
            model.addPerson(personToUnpin);
            model.commitAddressBook();
        } else {
            return new CommandResult(String.format(MESSAGE_UNPIN_PERSON_ALREADY, personToUnpin));
        }

        return new CommandResult(String.format(MESSAGE_UNPIN_PERSON_SUCCESS, personToUnpin));
    }
}
