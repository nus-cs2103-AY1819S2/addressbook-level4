package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CLEAR_SUCCESS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    private final Album album = Album.getInstance();

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history) {
        requireNonNull(model);
        album.clearAlbum();
        album.refreshAlbum();
        return new CommandResult(MESSAGE_CLEAR_SUCCESS);
    }
}
