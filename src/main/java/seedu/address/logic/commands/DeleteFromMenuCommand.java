package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.MenuItem;

/**
 * Removes a menu item from the menu.
 */
public class DeleteFromMenuCommand extends Command {

    public static final String COMMAND_WORD = "deleteFromMenu";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Removes the menu item from the menu item. " + "Parameters: ITEM_CODE\n"
                    + "Example: " + COMMAND_WORD + " W09";

    public static final String MESSAGE_SUCCESS = "Menu item deleted:\n%1$s";
    public static final String MESSAGE_INVALID_ITEM_CODE = "The item code [%1$s] is invalid";
    public static final String INVALID_RESTAURANT_STATE =
            "The restaurant is still occupied. Deletion of menu items not allowed.";

    private final Code itemCode;

    /**
     * Creates a DeleteFromMenuCommand to remove the menu item specified by the item code.
     */
    public DeleteFromMenuCommand(Code itemCode) {
        requireNonNull(itemCode);
        this.itemCode = itemCode;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(mode, model);

        Optional<MenuItem> itemToDelete = model.getRestOrRant().getMenu().getItemFromCode(itemCode);
        if (!itemToDelete.isPresent()) {
            throw new CommandException(String.format(MESSAGE_INVALID_ITEM_CODE, itemCode));
        }

        if (!model.getRestOrRant().getTables().isRestaurantEmpty()) {
            throw new CommandException(INVALID_RESTAURANT_STATE);
        }

        model.deleteMenuItem(itemToDelete.get());

        return new CommandResult(String.format(MESSAGE_SUCCESS, itemToDelete.get()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                       || (other instanceof DeleteFromMenuCommand // instanceof handles nulls
                                   && itemCode.equals(((DeleteFromMenuCommand) other).itemCode));
    }

}
