package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pdf.Pdf;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Merge 2 or more pdf files identified using it's displayed index from the pdf book.
 * The content of the files will be directly appended to each other into a single
 * file, which will be placed in the same directory as the first file index.
 */
public class MergeCommand extends Command {

    public static final String COMMAND_WORD = "merge";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Merges the PDF identified by the index number used in the displayed PDF list."
            + "The pdfs will be merged into one file and placed into the same directory as"
            + "the first file specified.\n"
            + "Parameters: INDEX1 (must be a positive integer) INDEX2 ...\n"
            + "Example: " + COMMAND_WORD + " 1 9 7 3";

    public static final String MESSAGE_MERGE_PDF_SUCCESS = "Merged PDFs: %1$s";
    public static final String MESSAGE_MERGE_PDF_SINGLE = "More than one file index has to be specified.";

    private final ArrayList<Index> targetIndexes;

    public MergeCommand(Index... args) {
        this.targetIndexes = new ArrayList<>();
        for (Index i : args){
            targetIndexes.add(i);
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Pdf> lastShownList = model.getFilteredPdfList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
        }

        Pdf pdfToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePdf(pdfToDelete);

        if (deleteType == DeleteType.Hard) {
            File dFile = Paths.get(pdfToDelete.getDirectory().getDirectory(),
                    pdfToDelete.getName().getFullName()).toFile();
            if (!dFile.delete()) {
                throw new CommandException(MESSAGE_DELETE_HARD_FAIL);
            }
        }

        model.commitPdfBook();
        return new CommandResult(String.format(MESSAGE_MERGE_PDF_SUCCESS, pdfToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeCommand // instanceof handles nulls
                && targetIndexes.equals(((MergeCommand) other).targetIndexes));
    }
}
