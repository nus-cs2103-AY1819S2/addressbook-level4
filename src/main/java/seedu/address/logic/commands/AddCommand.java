package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;

/**
 * Adds a medicine to the inventory.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a medicine to the inventory. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_COMPANY + "COMPANY "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Paracetamol "
            + PREFIX_COMPANY + "Novartis "
            + PREFIX_TAG + "inflammation "
            + PREFIX_TAG + "fever";

    public static final String MESSAGE_SUCCESS = "New medicine added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEDICINE = "This medicine already exists in the inventory";

    private final Medicine toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Medicine}
     */
    public AddCommand(Medicine medicine) {
        requireNonNull(medicine);
        toAdd = medicine;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasMedicine(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEDICINE);
        }

        model.addMedicine(toAdd);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
