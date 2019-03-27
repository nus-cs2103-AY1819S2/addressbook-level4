package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pdf.Pdf;

/**
 * Opens a pdf specified by the user.
 */
public class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens the pdf identified "
            + "by the index number used in the displayed pdf list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_OPEN_PDF_SUCCESS = "Opened PDF: %1$s";
    public static final String MESSAGE_OPEN_PDF_FAILIUE = "Unable to Open PDF.";

    private final Index index;

    /**
     * @param index of the pdf in the filtered pdf list to edit
     */
    public OpenCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Pdf> lastShownList = model.getFilteredPdfList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
        }

        Pdf pdfToOpen = lastShownList.get(index.getZeroBased());

        try {
            Desktop.getDesktop().open(Paths.get(pdfToOpen.getDirectory().getDirectory(),
                    pdfToOpen.getName().getFullName()).toAbsolutePath().toFile());

        } catch (IOException e) {
            throw new CommandException(MESSAGE_OPEN_PDF_FAILIUE, e);
        }

        return new CommandResult(String.format(MESSAGE_OPEN_PDF_SUCCESS, pdfToOpen));
    }
}
