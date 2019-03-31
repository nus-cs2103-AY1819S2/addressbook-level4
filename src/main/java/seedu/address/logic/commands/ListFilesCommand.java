/* @@author itszp */
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * Lists all files inside assets folder.
 */

public class ListFilesCommand extends Command {
    public static final String COMMAND_WORD = "listfiles";
    public static final String MESSAGE_LIST_FILES_HEADER = "Files in assets folder: %1$s";
    public static final String MESSAGE_SUCCESS = "Listed all files in assets folder.";


    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history) {
        requireNonNull(currentEdit);
        Album album = Album.getInstance();
        String[] fileNames = album.getFileNames();
        return new CommandResult(String.format(MESSAGE_LIST_FILES_HEADER, Arrays.toString(fileNames)
                + "\n" + MESSAGE_SUCCESS));
    }
}
