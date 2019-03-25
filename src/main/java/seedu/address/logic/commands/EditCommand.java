package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

/**
 * Abstract class that represents to edit objects in the address book.
 * @author Lookaz
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_USAGE = "The different modes for editing an existing item are as follows:\n";

    protected final Index index;

    protected EditCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    // abstract method to edit an object
    public abstract void edit(Model model, Object toEdit, Object edited);
}
