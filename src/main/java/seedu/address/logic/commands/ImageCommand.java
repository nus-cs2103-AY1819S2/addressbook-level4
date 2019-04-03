package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Config.IMAGE_DIRECTORY;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Imports an image into the working directory.
 */
public class ImageCommand extends Command {

    public static final String COMMAND_WORD = "image";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports an image into the working directory. "
        + "Parameters: "
        + "FILEPATH \n"
        + "Example: " + COMMAND_WORD + " "
        + "C:\\Users\\Alice\\Desktop\\lasagna.png";

    public static final String MESSAGE_SUCCESS = "Image %s successfully imported into: %s.";
    public static final String MESSAGE_INVALID_FILE = "Image %s not found!";
    public static final String MESSAGE_DUPLICATE_NAME = "Image with filename %s already exists in working directory.";
    public static final String MESSAGE_IMPORT_FAIL = "Failed to import the image. Please check your permissions."
        + "settings.";

    private final File toImport;

    /**
     * Creates an ImageCommand to import the specified image from {@code File}
     */
    public ImageCommand(File file) {
        requireNonNull(file);
        toImport = file;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        String fileName = toImport.getName();
        File workingDirectoryFile = new File(IMAGE_DIRECTORY.concat(fileName));

        if (workingDirectoryFile.exists()) {
            throw new CommandException(MESSAGE_DUPLICATE_NAME);
        }

        //make a copy of the image
        try {
            InputStream inputStream = new FileInputStream(toImport);
            OutputStream outputStream = new FileOutputStream(workingDirectoryFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            //ImageCommandParser should have already made this check;
            //so this should not be possible. But use redundancy for defensive coding
            throw new CommandException(String.format(MESSAGE_INVALID_FILE, toImport));
        } catch (IOException | NullPointerException e) {
            throw new CommandException(MESSAGE_IMPORT_FAIL);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toImport, workingDirectoryFile));
    }
}
