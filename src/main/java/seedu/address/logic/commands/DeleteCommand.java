package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

/**
 * @author Lookaz
 * Abstract class representing command to delete objects.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    protected final Index index;

    public DeleteCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    /**
     * Method to delete object from Model.
     * @param model Model to remove the object from.
     * @param toDelete Object to be removed.
     */
    public abstract void delete(Model model, Object toDelete);
}
