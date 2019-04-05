package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.logic.parser.CliSyntax;
import seedu.equipment.model.Model;
import seedu.equipment.model.equipment.Name;

/**
 * Adds an equipment to the equipment book.
 */
public class AddClientCommand extends Command {

    public static final String COMMAND_WORD = "add-c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a client to the Equipment Manager. "
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME ";

    public static final String MESSAGE_SUCCESS = "New client added: %1$s";
    public static final String MESSAGE_DUPLICATE_CLIENT = "Duplicated client name in client details.";

    private final Name toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Equipment}
     */
    public AddClientCommand(Name name) {
        requireNonNull(name);
        toAdd = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasClient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }

        model.addClient(toAdd);
        model.commitEquipmentManager();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddClientCommand // instanceof handles nulls
                && toAdd.equals(((AddClientCommand) other).toAdd));
    }
}
