package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATH;
import static seedu.address.commons.core.Messages.MESSAGE_SYSTEM_ERROR;

import java.io.File;

import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String path;

        if (trimmedArgs.isEmpty()) {
            path = getFilePath();
        } else {
            path = trimmedArgs;
        }

        File file = new File(path);
        if (!file.exists() || file.isDirectory() || !path.endsWith(".txt")) {
            throw new ParseException(String.format(MESSAGE_INVALID_PATH, ImportCommand.MESSAGE_USAGE));
        }

        return new ImportCommand(file);
    }

    public String getFilePath() throws ParseException {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.warning("unable to retrieve native system look and feel for File Explorer");
            throw new ParseException(MESSAGE_SYSTEM_ERROR);
        }

        File selectedFile = null;
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFile();
        }

        return selectedFile.getAbsolutePath();
    }

}
