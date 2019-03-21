/* @@author Carrein */
package seedu.address.logic.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.image.Image;

/**
 * Parses input arguments and creates a new ImportImage object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    private final String assetsFilePath = "src/main/resources/assets/";
    private final File directory = new File(assetsFilePath);

    /**
     * Parses the given {@code String} of arguments in the context
     * of the ImportCommand and returns an ImportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ImportCommand parse(String args) throws ParseException {

        // Boolean value to indicate if FomoFoto should print directory or file return message.
        boolean isDirectory = false;

        // Trim to prevent excess whitespace
        args = args.trim();

        try {
            switch (validPath(args)) {
            case 1:
                isDirectory = true;
                File folder = new File(args);
                File[] listOfFiles = folder.listFiles();
                for (File f : listOfFiles) {
                    String path = f.getAbsolutePath();
                    System.out.println(path);
                    if (validFormat(path)) {
                        Image image = new Image(path);
                        if (!duplicateFile(image)) {
                            try {
                                File file = new File(path);
                                FileUtils.copyFileToDirectory(file, directory);
                            } catch (IOException e) {
                                System.out.println(e.toString());
                            }
                        }
                    }
                }
                break;
            case 0:
                if (validFormat(args)) {
                    Image image = new Image(args);
                    if (!duplicateFile(image)) {
                        try {
                            File file = new File(args);
                            FileUtils.copyFileToDirectory(file, directory);
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
            System.out.println("I'm a directory");
            return 1;
        }
        if (file.isFile()) {
            System.out.println("I'm a file");
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
        return (new File(assetsFilePath + image.getName()).exists()) ? true : false;
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
}

