package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANIZATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.HealthWorker;
import seedu.address.model.tag.Specialisation;

/**
 * Adds a HealthWorker to the address book.
 * @author Lookaz
 */
public class AddHealthWorkerCommand extends AddCommand {

    public static final String COMMAND_OPTION = "1";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_OPTION
            + ": Add a health worker to the address book. Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_ORGANIZATION + "ORGANIZATION "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_SKILLS + "SKILLS..." + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Dog Terr "
            + PREFIX_NRIC + "S1234567A"
            + PREFIX_PHONE + "98765432 "
            + PREFIX_ORGANIZATION + "NUH "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_SKILLS + Specialisation.PHYSIOTHERAPY + " "
            + Specialisation.GENERAL_PRACTICE;

    public static final String MESSAGE_SUCCESS = "New health worker added: %1$s";
    public static final String DUPLICATE_HEALTH_WORKER = "This health worker "
            + "already exists in the address book";

    private final HealthWorker toAdd;

    public AddHealthWorkerCommand(HealthWorker toAdd) {
        super(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasHealthWorker(toAdd)) {
            throw new CommandException(DUPLICATE_HEALTH_WORKER);
        }

        model.addHealthWorker(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return (other == this) || (other instanceof AddHealthWorkerCommand
                && (this.toAdd.equals(((AddHealthWorkerCommand) other).toAdd)));
    }
}
