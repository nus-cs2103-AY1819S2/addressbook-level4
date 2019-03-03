package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a restaurant to the food diary.
 */
public class AddNameCommand extends Command {

    public static final String COMMAND_WORD = "addName";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a names to your profile "
            + "Parameters: "
            + PREFIX_NAME + "NAME ";


    public static final String MESSAGE_SUCCESS = "Updated Profile: %1$s";


    private final String toAdd;

    /**
     * Creates an AddCommand update the name of the user
     */
    public AddNameCommand(String name) {
        toAdd = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.setName(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
