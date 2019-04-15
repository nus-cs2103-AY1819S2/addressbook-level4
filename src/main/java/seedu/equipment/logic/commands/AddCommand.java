package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.logic.parser.CliSyntax;
import seedu.equipment.model.Model;
import seedu.equipment.model.equipment.Equipment;

/**
 * Adds an equipment to the equipment book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add-e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an equipment to the Equipment Manager. "
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME "
            + CliSyntax.PREFIX_PHONE + "PHONE "
            + CliSyntax.PREFIX_PM + "PREVENTIVE MAINTENANCE DATE "
            + CliSyntax.PREFIX_ADDRESS + "ADDRESS "
            + CliSyntax.PREFIX_SERIALNUMBER + "SERIAL NUMBER "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Chua Chu Kang CC "
            + CliSyntax.PREFIX_PHONE + "67655001 "
            + CliSyntax.PREFIX_PM + "22-04-2019 "
            + CliSyntax.PREFIX_ADDRESS + "35 Teck Whye Ave, Singapore 688892 "
            + CliSyntax.PREFIX_SERIALNUMBER + "A006412X "
            + CliSyntax.PREFIX_TAG + "northwest "
            + CliSyntax.PREFIX_TAG + "urgent";

    public static final String MESSAGE_SUCCESS = "New equipment added: %1$s";
    public static final String MESSAGE_DUPLICATE_EQUIPMENT = "Duplicated equipment serial number, "
            + "this equipment already exists in the equipment manager.";

    private final Equipment toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Equipment}
     */
    public AddCommand(Equipment equipment) {
        requireNonNull(equipment);
        toAdd = equipment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasEquipment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EQUIPMENT);
        }

        model.addClient(toAdd.getName());
        model.addEquipment(toAdd);
        model.commitEquipmentManager();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
