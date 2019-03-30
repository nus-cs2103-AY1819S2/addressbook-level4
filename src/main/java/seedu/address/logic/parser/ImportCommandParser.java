/* @@author Carrein */
package seedu.address.logic.parser;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.core.Config.MAX_FILE_SIZE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Album;
import seedu.address.model.image.Image;

/**
 * Parses input arguments and creates a new ImportImage object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    // Directory to copy imported images to.
    private final File directory = new File(ASSETS_FILEPATH);

    // Album to copy imported images to.
    private final Album album = Album.getInstance();

    /**
     * Parses the given {@code String} of arguments in the context
     * of the ImportCommand and returns an ImportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ImportCommand parse(String args) throws ParseException {

        // Boolean value to indicate if FomoFoto should print directory or file return message.
        boolean isDirectory = false;

        // Trim to prevent excess whitespace.
        args = args.trim();

        try {
            switch (validPath(args)) {
            case 1:
                isDirectory = true;
                File folder = new File(args);
                File[] listOfFiles = folder.listFiles();
                for (File f : listOfFiles) {
                    String path = f.getAbsolutePath();
                    // File must be valid and not hidden and not ridiculously large.
                    if (validFormat(path) && !isHidden(path) && !isLarge(path)) {
                        Image image = new Image(path);
                        if (!duplicateFile(image)) {
                            try {
                                // Add each image to Album.
                                album.addImage(image);

                                File file = new File(path);
                                FileUtils.copyFileToDirectory(file, directory);
                                System.out.println("✋ IMPORTED: " + path);
                            } catch (IOException e) {
                                System.out.println(e.toString());
                            }
                        }
                    }
                }
                break;
            case 0:
                // File must be valid and not hidden and not ridiculously large.
                if (validFormat(args) && !isHidden(args) && !isLarge(args)) {
                    Image image = new Image(args);
                    if (!duplicateFile(image)) {
                        try {
                            // Add each image to Album.
                            album.addImage(image);

                            File file = new File(args);
                            FileUtils.copyFileToDirectory(file, directory);

                            System.out.println("✋ IMPORTED: " + args);
                        } catch (IOException e) {
                            System.out.println(e.toString());
                        }
                    } else {
                        throw new ParseException(Messages.MESSAGE_DUPLICATE_FILE);
                    }
                } else {
                    throw new ParseException(Messages.MESSAGE_INVALID_TYPE);
                }
                break;
            default:
                throw new ParseException(Messages.MESSAGE_INVALID_PATH);
            }

        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return new ImportCommand(isDirectory);
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
        return (new File(ASSETS_FILEPATH + image.getName().name).exists()) ? true : false;
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

