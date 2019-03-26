package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.PersonIsNotPatient;
import seedu.address.model.person.Person;

/**
 * Adds a patient to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient to the dental book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_SEX + "SEX "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_YEAR + "DOB \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_SEX + "M "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_YEAR + "30-12-1990 ";

    public static final String MESSAGE_SUCCESS = "New patient added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient already exists in the dental book";

    private final Patient toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Patient}
     * @param patient the patient to be added.
     */
    public AddCommand(Patient patient) {
        requireNonNull(patient);
        toAdd = patient;
    }

    /**
     * Creates an AddCommand to add the specified {@code Person}
     * @param person the person to be added.
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        if (person instanceof Patient) {
            toAdd = (Patient) person;
        } else {
            throw new PersonIsNotPatient();
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
