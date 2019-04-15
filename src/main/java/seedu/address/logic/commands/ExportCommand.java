/* @@author randytqw */
package seedu.address.logic.commands;

import static org.apache.commons.io.FileUtils.copyFileToDirectory;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.image.Image;


/**
 * Allows the user to export the current image to his own PC
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Exports an image in the album to a specified directory in user PC.\n"
        + "Parameters: [f/FILENAME TO EXPORT] [d/DIRECTORY TO EXPORT TO]\n"
        + "Example: " + COMMAND_WORD + " f/iu.jpg d/C:\\Users\\randy\\Desktop";

    private String filename;
    private String path;
    private Album album = Album.getInstance();

    public ExportCommand(String path, String filename) {
        this.path = path;
        this.filename = filename;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory commandHistory)
        throws CommandException {

        if (validPath(path) != 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATH);
        }
        File directory = new File(path);
        Image image = album.getImageFromList(filename);
        if (image == null) {
            throw new CommandException(Messages.MESSAGE_FILE_NOT_FOUND);
        }
        File toExport = new File(image.getUrl());
        try {
            copyFileToDirectory(toExport, directory, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CommandResult(Messages.MESSAGE_EXPORT_SUCCESS);
    }
    /**
     * Given a URL checks if the given path is valid.
     *
     * @param url Path to a file or directory.
     * @return 1 if directory, 0 if file, -1 otherwise.
     */
    public int validPath(String url) {
        // Trim url to remove trailing whitespace
        File file = new File(url.trim());
        if (file.isDirectory()) {
            return 1;
        }
        if (file.isFile()) {
            return 0;
        }
        return -1;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExportCommand // instanceof handles nulls
            && isValuesEqual(other));
    }
    private boolean isValuesEqual(Object other) {
        ExportCommand temp = (ExportCommand) other;
        return this.filename.equals(temp.filename) && this.path.equals(temp.path);
    }
}
