package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Pins specific persons in the address book to the user.
 */
public class PinCommand extends Command {

    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pins the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned Person: %1$s";

    private final Index targetIndex;

    public PinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToPin = lastShownList.get(targetIndex.getZeroBased());

        return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, personToPin));
    }

    @Override
    public boolean requiresMainList() {
        return true;
    }

    @Override
    public boolean requiresArchiveList() {
        return false;
    }
}
