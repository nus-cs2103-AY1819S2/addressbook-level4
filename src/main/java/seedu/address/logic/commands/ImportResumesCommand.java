package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.Iterator;
import java.util.Set;

/**
 * Imports resume txt files from a given directory into slavefinder().
 */
public class ImportResumesCommand extends Command {

    public static final String COMMAND_WORD = "importResumes";
    public static final String COMMAND_ALIAS = "ir";
    public static final String MESSAGE_SUCCESS = "Resumes have been imported";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    Set<Person> toAdd;

    public ImportResumesCommand(Set<Person> people) {
        toAdd = people;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Iterator<Person> setIterator = toAdd.iterator();

        while (setIterator.hasNext()) {
            Person currentPerson = setIterator.next();
            if (model.hasPerson(currentPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }
            model.addPerson(currentPerson);
        }

        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportResumesCommand // instanceof handles nulls
                && toAdd.equals(((ImportResumesCommand) other).toAdd));
    }
}
