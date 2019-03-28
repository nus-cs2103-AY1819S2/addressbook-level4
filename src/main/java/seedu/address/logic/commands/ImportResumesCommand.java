package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Imports resume txt files from a given directory into slavefinder().
 */
public class ImportResumesCommand extends Command {

    public static final String COMMAND_WORD = "importResumes";
    public static final String COMMAND_ALIAS = "ir";
    public static final String MESSAGE_SUCCESS = "Resumes have been imported";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    Person toAdd;

    public ImportResumesCommand(Person p) {
        toAdd = p;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
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
