package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLOTHING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.apparel.Apparel;


/**
 * Adds a apparel to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a apparel to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_COLOR + "COLOR "
            + PREFIX_CLOTHING_TYPE + "TYPE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "blurr berry blex "
            + PREFIX_COLOR + "NAVY "
            + PREFIX_CLOTHING_TYPE + "TOP";

    public static final String MESSAGE_SUCCESS = "New apparel added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPAREL = "This apparel already exists in the address book";

    private final Apparel toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Apparel}
     */
    public AddCommand(Apparel apparel) {
        requireNonNull(apparel);
        toAdd = apparel;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasApparel(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPAREL);
        }

        model.addApparel(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
