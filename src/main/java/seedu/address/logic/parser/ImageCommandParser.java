package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seedu.address.logic.commands.ImageCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UploadCommand object
 */
public class ImageCommandParser implements Parser<ImageCommand> {

    public static final String PATH_MESSAGE_CONSTRAINT = "File not found.";
    public static final String FILE_MESSAGE_CONSTRAINT = "File should be an image file.";

    /**
     * Checks whether an image exists at the given path.
     */
    private boolean fileIsValidImage(File file) {
        try {
            BufferedImage imageRead = ImageIO.read(file);
            if (imageRead == null) {
                throw new IOException();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the UploadCommand
     * and returns an UploadCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImageCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE));
        }

        File file = new File(trimmedArgs);

        if (file.isDirectory() || !file.exists()) {
            throw new ParseException(PATH_MESSAGE_CONSTRAINT);
        }
        if (!fileIsValidImage(file)) {
            throw new ParseException(FILE_MESSAGE_CONSTRAINT);
        }

        return new ImageCommand(file);
    }

}
