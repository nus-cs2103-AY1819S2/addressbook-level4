package seedu.pdf.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DIRECTORY;
import static seedu.pdf.model.Model.PREDICATE_SHOW_ALL_PDFS;

import java.io.File;
import java.nio.file.Paths;

import java.util.List;

import seedu.pdf.commons.core.Messages;
import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Directory;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.model.pdf.exceptions.DuplicatePdfException;

/**
 * Moves a PDF listed in PDF++ to another specified location.
 */
public class MoveCommand extends Command {

    public static final String COMMAND_WORD = "move";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Moves one specified pdf identified "
            + "by the index number used in the displayed pdf list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[NEW_DIRECTORY]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DIRECTORY + "C:\\Users\\[username]\\Desktop\\[target]";

    public static final String MESSAGE_MOVE_PDF_SUCCESS = "Moved PDF:\n%1$s";
    public static final String MESSAGE_NOT_MOVED_SAME = "You have indicated the same directory as the file.";
    public static final String MESSAGE_NOT_MOVED_DUPLICATE = "File with same name at location.";
    public static final String MESSAGE_MOVE_PDF_FAIL = "PDF failed to be moved.";

    private final Index index;
    private final Directory movePdfDirectory;

    /**
     * @param index of the pdf in the filtered pdf list to move
     * @param movePdfDirectory details to move the pdf with
     */
    public MoveCommand(Index index, Directory movePdfDirectory) {
        requireNonNull(index);
        requireNonNull(movePdfDirectory);

        this.index = index;
        this.movePdfDirectory = movePdfDirectory;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Pdf> lastShownList = model.getFilteredPdfList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
        }

        Pdf pdfToEdit = lastShownList.get(index.getZeroBased());
        if (movePdfDirectory.equals(pdfToEdit.getDirectory())) {
            throw new CommandException(MESSAGE_NOT_MOVED_SAME);
        }

        Pdf editedPdf = new Pdf(pdfToEdit.getName(), movePdfDirectory, pdfToEdit.getSize(), pdfToEdit.getTags(),
                pdfToEdit.getDeadline(), pdfToEdit.getIsEncrypted());

        if (editedPdf.isValidPdf()) {
            throw new CommandException(MESSAGE_NOT_MOVED_DUPLICATE, new DuplicatePdfException());
        } else {

            File oFile = Paths.get(pdfToEdit.getDirectory().getDirectory(), pdfToEdit.getName().getFullName()).toFile();
            File nFile = Paths.get(editedPdf.getDirectory().getDirectory(), editedPdf.getName().getFullName()).toFile();
            boolean editSuccess = oFile.renameTo(nFile);

            if (!editSuccess) {
                throw new CommandException(MESSAGE_MOVE_PDF_FAIL);
            }
        }

        model.setPdf(pdfToEdit, editedPdf);
        model.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);
        model.commitPdfBook();
        return new CommandResult(String.format(MESSAGE_MOVE_PDF_SUCCESS, editedPdf.toString()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MoveCommand)) {
            return false;
        }

        // state check
        MoveCommand e = (MoveCommand) other;
        return index.equals(e.index)
                && movePdfDirectory.equals(e.movePdfDirectory);
    }
}
