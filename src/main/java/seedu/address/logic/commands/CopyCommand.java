package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.PersonIsNotPatient;
import seedu.address.model.person.Person;

/**
 * Copy a temporary person to the address book.
 */
public class CopyCommand extends Command {

    public static final String COMMAND_WORD = "copy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Have a temporary duplicate person in the addressbook. "
            + "Parameters: Index (Must be an integer) [Number of Copies]"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Person copied: %1$s";

    private final Index index;

    private final int numOfCopies;

    /**
     * Creates an CopyCommand to add the specified {@code Person}
     */
    public CopyCommand(Index index, int numOfCopies) {
        requireNonNull(index);
        this.index = index;
        this.numOfCopies = numOfCopies;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToCopy = lastShownList.get(index.getZeroBased());
        Person copyPerson;

        requireNonNull(personToCopy);
        if (personToCopy instanceof Patient) {
            copyPerson = personToCopy.copy();
        } else {
            throw new PersonIsNotPatient();
        }

        for (int i = 0; i < numOfCopies; i++) {
            model.addPerson(copyPerson);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, copyPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CopyCommand // instanceof handles nulls
                && index.equals(((CopyCommand) other).index));
    }
}
