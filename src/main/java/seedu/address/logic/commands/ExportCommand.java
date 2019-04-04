package seedu.address.logic.commands;

import static org.apache.commons.io.FileUtils.copyFile;
import static org.apache.commons.io.FileUtils.copyFileToDirectory;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;


/**
 * Allows the user to export the current image to his own PC
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "Export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + "Exports the current image to a specified directory in user PC.\n"
        + "Parameters: [absolute path of file] "
        + "Example: " + COMMAND_WORD + " C:/Users/Fomo/Pictures/sample.jpg";

    private File toExport;
    private File path;
    private boolean isDirectory;

    public ExportCommand(String path, boolean isDirectory) {
        this.path = new File(path);
        this.isDirectory = isDirectory;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory commandHistory)
        throws CommandException {
        if (currentEdit.tempImageExist()) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        if (isDirectory) {
            try {
                toExport = new File(currentEdit.getTempImage().getUrl());
                copyFileToDirectory(toExport, path, false);
            } catch (IOException e) {
                throw new CommandException(Messages.MESSAGE_INVALID_PATH);
            }
        }
        try {
            toExport = new File(currentEdit.getTempImage().getUrl());
            copyFile(toExport, path);
        } catch (IOException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATH);
        }

        return new CommandResult(Messages.MESSAGE_EXPORT_SUCCESS);
    }

    @Override
    public String toString() {
        return COMMAND_WORD + " -> " + path;
    }
}
