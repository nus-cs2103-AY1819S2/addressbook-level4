package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
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
    public static final String MESSAGE_PIN_PERSON_ALREADY = "Person %1$s is already in the pin list\n";
    public static final String MESSAGE_PINLIST_FULL = "Unable to pin person: %1$s. \n" +
            "The pin list contains up to 5 people\n";
    public static final int MAX_SIZE = 5;
    private  static FilteredList<Person> pinnedPersonList = null;

    private final Index targetIndex;

    public static FilteredList<Person> getPinnedPersonList() {
        return pinnedPersonList;
    }

    public static FilteredList<Person> editPinnedPersonList(Person person) {
        return pinnedPersonList;
    }

    public PinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToPin = lastShownList.get(targetIndex.getZeroBased());

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (pinnedPersonList != null && pinnedPersonList.size() == MAX_SIZE) {
            return new CommandResult(String.format(MESSAGE_PINLIST_FULL, personToPin));
        } else if (pinnedPersonList != null && pinnedPersonList.contains(personToPin)) {
            return new CommandResult(String.format(MESSAGE_PIN_PERSON_ALREADY, personToPin));
        } else {
            //pinnedPersonList.add(personToPin);
            //model.pinPerson(personToPin);
            model.deletePerson(personToPin);
            model.commitAddressBook();

            return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, personToPin));
        }

    }
}
