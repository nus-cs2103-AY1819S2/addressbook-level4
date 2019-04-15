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
 * Copy a temporary patient to the address book.
 */
public class PatientCopyCommand extends Command {

    public static final String COMMAND_WORD = "patientcopy";
    public static final String COMMAND_WORD2 = "pcopy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD2
            + ": Create a temporary duplicate patient in the patient list."
            + "Parameters: Index (Must be an integer) [Number of Copies]"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Person copied: %1$s";

    private final Index index;

    private final int numOfCopies;

    /**
     * Creates an PatientCopyCommand to add the specified {@code Person}
     */
    public PatientCopyCommand(Index index, int numOfCopies) {
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
        Person copyPerson = null;

        requireNonNull(personToCopy);

        for (int i = 0; i < numOfCopies; i++) {
            if (personToCopy instanceof Patient) {
                copyPerson = personToCopy.copy();
            } else {
                throw new PersonIsNotPatient();
            }
            model.addPerson(copyPerson);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, copyPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PatientCopyCommand // instanceof handles nulls
                && index.equals(((PatientCopyCommand) other).index));
    }
}
