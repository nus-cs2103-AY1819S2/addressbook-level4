package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATH;
import static seedu.address.commons.core.Messages.MESSAGE_SYSTEM_ERROR;

import java.io.File;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ShareCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShareCommand object
 */
public class ShareCommandParser implements Parser<ShareCommand> {

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the ShareCommand
     * and returns an ShareCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShareCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String path;

        if (trimmedArgs.isEmpty()) {
            path = getPathToSaveFile();
        } else {
            path = trimmedArgs;
        }

        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new ParseException(String.format(MESSAGE_INVALID_PATH, ShareCommand.MESSAGE_USAGE));
        }

        return new ShareCommand(path);
    }

    public String getPathToSaveFile() throws ParseException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.warning("unable to retrieve native system look and feel for File Explorer");
            throw new ParseException(MESSAGE_SYSTEM_ERROR);
        }

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Choose a directory to save your file: ");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.showSaveDialog(null);

        return jfc.getSelectedFile().toString();
    }
}
