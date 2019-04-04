package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.core.Config.ASSETS_FILEPATH;

import java.io.File;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    private final Album album = Album.getInstance();

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.commitAddressBook();
        File dir = new File(album.getAssetsFilepath());
        model.clearAssetFolder(dir);
        album.populateAlbum();
        model.refreshAlbum();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
