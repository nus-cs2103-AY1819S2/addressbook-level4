package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRICNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAROFSTUDY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class MemberAddCommand extends MemberCommand {

    public static final String COMMAND_WORD = "memberAdd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a member to Club Manager. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_MATRICNUMBER + "MATRICNUMBER "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_GENDER + "GENDER "
            + PREFIX_YEAROFSTUDY + "YEAROFSTUDY "
            + PREFIX_MAJOR + "MAJOR "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_MATRICNUMBER + "A0123456Z "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_GENDER + "Male "
            + PREFIX_YEAROFSTUDY + "Year 2 "
            + PREFIX_MAJOR + "Chemistry "
            + PREFIX_TAG + "Swimming";

    public static final String MESSAGE_SUCCESS = "New member added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This member already exists in Club Manager";
    public static final String MESSAGE_DUPLICATE_MATRICNUMBER = "This matricNumber already exists in Club Manager";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public MemberAddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } else if (model.hasMatricNumber(toAdd.getMatricNumber())) {
            throw new CommandException(MESSAGE_DUPLICATE_MATRICNUMBER);
        }
        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemberAddCommand // instanceof handles nulls
                && toAdd.equals(((MemberAddCommand) other).toAdd));
    }
}
