/* @@author Carrein */
package seedu.address.logic.parser;

import static seedu.address.commons.core.Config.MAX_FILE_SIZE;
import static seedu.address.commons.core.Config.SAMPLE_FOLDER;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import seedu.address.commons.core.Messages;
import seedu.address.logic.ResourceWalker;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Album;
import seedu.address.model.image.Image;

/**
 * Parses input arguments and creates a new ImportImage object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    private final Album album = Album.getInstance();
    private final File directory = new File(album.getAssetsFilepath());

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

        File folder;
        File[] listOfFiles;
        try {
            switch (validPath(args)) {
            // TODO - Pending refactor.
            case 2:
                if (album.getImageList().size() > 0) {
                    throw new ParseException(Messages.MESSAGE_SAMPLE_IMPORT);
                }
                isDirectory = true;
                try {
                    ResourceWalker.walk(SAMPLE_FOLDER);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                isDirectory = true;
                folder = new File(args);
                listOfFiles = folder.listFiles();
                for (File f : listOfFiles) {
                    String path = f.getAbsolutePath();
                    // File must be valid and not hidden and not ridiculously large.
                    if (validFormat(path) && !isHidden(path) && !isLarge(path) && validImage(path)) {
                        Image image = new Image(path);
                        if (!duplicateFile(image)) {
                            try {
                                File file = new File(path);
                                album.addToImageList(path);
                                FileUtils.copyFileToDirectory(file, directory);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                break;
            case 0:
                // File must be valid and not hidden and not ridiculously large.
                if (validFormat(args) && !isHidden(args)) {
                    if (validImage(args)) {
                        if (!isLarge(args)) {
                            Image image = new Image(args);
                            if (!duplicateFile(image)) {
                                try {
                                    File file = new File(args);
                                    FileUtils.copyFileToDirectory(file, directory);
                                    album.addToImageList(args);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                throw new ParseException(Messages.MESSAGE_DUPLICATE_FILE);
                            }
                        } else {
                            throw new ParseException(Messages.MESSAGE_INVALID_SIZE);
                        }
                    } else {
                        throw new ParseException(Messages.MESSAGE_UNABLE_TO_READ_FILE);
                    }
                } else {
                    throw new ParseException(Messages.MESSAGE_INVALID_FORMAT);
                }
                break;
            default:
                throw new ParseException(Messages.MESSAGE_INVALID_PATH);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        album.refreshAlbum();
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
        if (url.equals("sample")) {
            return 2;
        }
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
        return album.imageExist(image.getName().fullName);
    }

    /**
     * Given a URL checks if the file is of an image type.
     *
     * @param url Path to a file or directory.
     * @return True if path is valid, false otherwise.
     * @throws IOException Throws exception if content cannot be probed.
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

    /**
     * Checks is url given is a valid Image file.
     *
     * @param url Path to file or directory
     * @return True if file is a valid image, false otherwise.
     * @throws IOException Throws exception if file cannot be opened.
     */
    public boolean validImage(String url) throws IOException {
        File file = new File(url);
        if (ImageIO.read(file) == null) {
            return false;
        }
        return true;
    }
}

