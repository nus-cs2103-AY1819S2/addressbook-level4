package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Abstract class that represents to edit objects in the address book.
 * @author Lookaz
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    // abstract method to edit an object
    public abstract void edit(Model model, Object toEdit, Object edited);
}
