package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Abstract class representing a command to add objects to the addressbook.
 * @author Lookaz
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public abstract void add(Model model, Object toAdd); // method for adding the respective object
}
