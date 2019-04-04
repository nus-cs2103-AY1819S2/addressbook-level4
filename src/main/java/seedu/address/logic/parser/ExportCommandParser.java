package seedu.address.logic.parser;

import static seedu.address.commons.core.Config.MAX_FILE_SIZE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Album;
import seedu.address.model.image.Image;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    // Album to copy imported images to.
    private final Album album = Album.getInstance();
    // Directory to copy imported images to.
    private final File directory = new File(album.getAssetsFilepath());

    /**
     * Parses the given {@code String} of arguments in the context
     * of the ImportCommand and returns an ImportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ExportCommand parse(String args) throws ParseException {

        // Boolean value to indicate if FomoFoto should print directory or file return message.
        boolean isDirectory = false;

        // Trim to prevent excess whitespace.
        args = args.trim();

        if (validPath(args) == 1) {
            return new ExportCommand(args, true);
        } else if (validPath(args) == 0) {
            return new ExportCommand(args, false);
        } else {
            throw new ParseException(Messages.MESSAGE_FILE_NOT_FOUND);
        }
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

    /**
     * Given a Image checks if the file name already exists.
     *
     * @param image image to for checking.
     * @return True if image file name exist, false otherwise.
     */
    public boolean duplicateFile(Image image) {
        return (new File(album.getAssetsFilepath() + image.getName().toString()).exists()) ? true : false;
    }

    /**
     * Given a URL checks if the file is of an image type.
     *
     * @param url Path to a file or directory.
     * @return True if path is valid, false otherwise.
     */
    public boolean validFormat(String url) throws IOException {
        String mime = Files.probeContentType(Paths.get(url));
        return (mime != null && mime.split("/")[0].equals("image")) ? true : false;
    }

    /**
     * Given a URL checks if the file is hidden.
     * Uses isHidden() for DOS based machines and checks '.' character for UNIX based machines.
     *
     * @param url Path to a file or directory.
     * @return True if file is hidden, false otherwise.
     */
    public boolean isHidden(String url) {
        File file = new File(url);
        return (file.isHidden() || file.getName().substring(0, 1).equals(".")) ? true : false;
    }

    /**
     * Given a URL checks if the file is too large for bufferedImage to process.
     * Constant for
     *
     * @param url Path to a file or directory.
     * @return True if file is too large, false otherwise.
     */
    public boolean isLarge(String url) {
        File file = new File(url);
        return file.length() > MAX_FILE_SIZE;
    }
}

