package seedu.address.logic.commands;

import java.util.ArrayList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class ClearMenuCommand extends Command {

    public static final String COMMAND_WORD = "clearMenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Menu has been cleared";

    public static final String INVALID_RESTAURANT_STATE =
            "Restaurant is still occupied. Clearing of menu not allowed";

    public static final String MESSAGE_FAILURE = "Menu is already empty.";

    /**
     * Creates a ClearTableCommand to remove all table(s).
     */
    public ClearMenuCommand() { }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(mode, model);

        if (!model.isRestaurantEmpty()) {
            throw new CommandException(INVALID_RESTAURANT_STATE);
        }
        if (model.getRestOrRant().getMenu().getMenuItemList().isEmpty()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.setMenuItems(new ArrayList<>());

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
