package seedu.pdf.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import seedu.pdf.commons.core.Messages;
import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Pdf;

/**
 * Opens a pdf specified by the user.
 */
public class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens the pdf identified "
            + "by the index number used in the displayed pdf list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_PDF_OPEN_SUCCESS = "Opened PDF:\n%1$s";
    public static final String MESSAGE_PDF_OPEN_FAILURE = "Unable to Open PDF.";

    private final Index index;

    /**
     * @param index of the pdf in the filtered pdf list to edit
     */
    public OpenCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @SuppressWarnings("Duplicates")
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
            throw new CommandException(MESSAGE_PDF_OPEN_FAILURE, e);
        }

        return new CommandResult(String.format(MESSAGE_PDF_OPEN_SUCCESS, pdfToOpen.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpenCommand // instanceof handles nulls
                && index.equals(((OpenCommand) other).index));
    }
}
