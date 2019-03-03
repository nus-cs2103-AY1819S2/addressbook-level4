package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_FILE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATH;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TYPE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.image.Image;

/**
 * Parses input arguments and creates a new ImportImage object
 */
public class ImportCommandParser implements Parser<ImportCommand> {
    /**
     * Parses the given {@code String} of arguments in the context
     * of the ImportCommand and returns an ImportCommand object for execution.
     *
     * @throws ParseException   if the user input does not conform the expected format.
     */
    public ImportCommand parse(String args) throws ParseException {
        args = args.trim();
        File file = new File(args);
        Image image = null;
        File directory = new File("src/main/resources/assets");

        // File must exist.
        if (file.isFile()) {
            // File must be an image type.
            try {
                String mime = Files.probeContentType(file.toPath());
                if (mime != null && mime.split("/")[0].equals("image")) {
                    image = new Image(args);
                    if (!new File("src/main/resources/assets/" + image.getName()).exists()) {
                        try {
                            FileUtils.copyFileToDirectory(file, directory);
                        } catch (IOException e) {
                            System.out.println(e.toString());
                        }
                    } else {
                        throw new ParseException(String.format(MESSAGE_DUPLICATE_FILE, ImportCommand.MESSAGE_USAGE));
                    }
                } else {
                    throw new ParseException(String.format(MESSAGE_INVALID_TYPE, ImportCommand.MESSAGE_USAGE));
                }
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_PATH, ImportCommand.MESSAGE_USAGE));
        }
        return new ImportCommand(image);
    }
}
