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
    public static final String MESSAGE_ASSETS_EMPTY = "No files in assets folder. Use import to add files.";
    private Album album = Album.getInstance();


    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history) {
        requireNonNull(currentEdit);
        String[] fileNames = album.getFileNames();

        if (fileNames.length == 0) {
            return new CommandResult(MESSAGE_ASSETS_EMPTY);
        }

        return new CommandResult(String.format(MESSAGE_LIST_FILES_HEADER, Arrays.toString(fileNames)
                + "\n" + MESSAGE_SUCCESS));
    }
}
