package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Abstract class representing a command to add objects to the addressbook.
 * @author Lookaz
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = "The different modes for registering a new item are as follows:\n";

    public abstract void add(Model model, Object toAdd); // method for adding the respective object
}
