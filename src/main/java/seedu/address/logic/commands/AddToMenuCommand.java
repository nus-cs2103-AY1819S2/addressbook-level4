package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.menu.MenuItem;

/**
 * Adds a menu item to the Menu.
 */
public class AddToMenuCommand extends Command {
    public static final String COMMAND_WORD = "addToMenu";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a menu item to the menu. " + "Parameters: "
            + PREFIX_CODE + "CODE " + PREFIX_NAME + "NAME " + PREFIX_PRICE + "PRICE " + "Example: " + COMMAND_WORD + " "
            + PREFIX_CODE + "A02 " + PREFIX_NAME + "French Fries " + PREFIX_PRICE + "2.00";
    public static final String MESSAGE_SUCCESS = "New menu item added: %1$s";
    public static final String MESSAGE_DUPLICATE_MENU_ITEM = "This item already exists in the menu";
    public static final String MESSAGE_INCORRECT_MODE = "Incorrect Mode, unable to execute command. Enter menuMode";
    public static final String MESSAGE_TOO_MANY_ITEMS = "RestOrRant can only accommodate 200 menu items!\n"
                                                                + "Currently there are %1$s menu items.";
    public static final int MAX_ALLOWED_ITEMS = 200;

    private final MenuItem toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddToMenuCommand(MenuItem item) {
        requireNonNull(item);
        toAdd = item;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(mode, model);

        if (!mode.equals(Mode.MENU_MODE)) {
            throw new CommandException(MESSAGE_INCORRECT_MODE);
        }

        if (model.hasMenuItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MENU_ITEM);
        }

        if (model.getRestOrRant().getMenu().getMenuItemList().size() >= MAX_ALLOWED_ITEMS) {
            throw new CommandException(String.format(MESSAGE_TOO_MANY_ITEMS,
                    String.valueOf(model.getRestOrRant().getMenu().getMenuItemList().size())));
        }

        model.addMenuItem(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddToMenuCommand // instanceof handles nulls
                && toAdd.equals(((AddToMenuCommand) other).toAdd));
    }
}
