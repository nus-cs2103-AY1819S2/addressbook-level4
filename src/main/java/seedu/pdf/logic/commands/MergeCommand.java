package seedu.pdf.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import seedu.pdf.commons.core.Messages;
import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Directory;
import seedu.pdf.model.pdf.Name;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.model.pdf.Size;

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

    private static final String MESSAGE_MERGE_PDF_SUCCESS = "Merged PDFs into new PDF:\n%1$s";
    private static final String MESSAGE_MERGE_PDF_ENCRYPT = "One or more of selected PDFs is encrypted.";
    private static final String MESSAGE_MERGE_PDF_FAIL = "Merging of PDFs encountered an error and stopped.";

    private static final int FIRST_INDEX = 0;

    private static final String PDF_SUFFIX = ".pdf";

    private final ArrayList<Index> targetIndexes;

    public MergeCommand(Index... args) {
        this.targetIndexes = new ArrayList<>();
        for (Index i : args) {
            targetIndexes.add(i);
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Pdf> lastShownList = model.getFilteredPdfList();

        if (targetIndexes.stream().anyMatch(index -> index.getZeroBased() >= lastShownList.size())) {
            throw new CommandException(Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
        }

        ArrayList<File> pdfsToMerge = new ArrayList<>();
        targetIndexes.forEach(index -> {
            Pdf pdf = lastShownList.get(index.getZeroBased());
            pdfsToMerge.add(Paths.get(pdf.getDirectory().getDirectory(), pdf.getName().getFullName()).toFile());
        });

        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        String mergedPdfDirectory = lastShownList.get(targetIndexes.get(FIRST_INDEX)
                .getZeroBased()).getDirectory().getDirectory();
        String mergedPdfName = "merged" + hashCode();
        //Check that no duplicate file name (how on earth would it happen though)
        while (Paths.get(mergedPdfDirectory, mergedPdfName + PDF_SUFFIX).toAbsolutePath().toFile().exists()) {
            //Just throwing zeros at the back of the name until it is unique
            mergedPdfName += FIRST_INDEX;
        }
        mergedPdfName += PDF_SUFFIX;
        pdfMerger.setDestinationFileName(new File(mergedPdfDirectory, mergedPdfName).toString());

        try {
            ArrayList<PDDocument> pdfSources = new ArrayList<>();
            for (File file : pdfsToMerge) {
                pdfSources.add(PDDocument.load(file));
                pdfMerger.addSource(file);
            }

            pdfMerger.mergeDocuments(org.apache.pdfbox.io.MemoryUsageSetting.setupMainMemoryOnly());
            for (PDDocument doc : pdfSources) {
                doc.close();
            }

        } catch (InvalidPasswordException p) {
            throw new CommandException(MESSAGE_MERGE_PDF_ENCRYPT);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_MERGE_PDF_FAIL);
        }

        //Add merged pdf to application
        assert Paths.get(mergedPdfDirectory, mergedPdfName).toAbsolutePath().toFile().exists();
        File mergedFile = Paths.get(mergedPdfDirectory, mergedPdfName).toAbsolutePath().toFile();
        Pdf mergedPdf = new Pdf(
                new Name(mergedFile.getName()),
                new Directory(mergedFile.getParent()),
                new Size(String.valueOf(mergedFile.length())),
                new HashSet<>());

        model.addPdf(mergedPdf);
        model.commitPdfBook();
        return new CommandResult(String.format(MESSAGE_MERGE_PDF_SUCCESS, mergedPdf.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeCommand // instanceof handles nulls
                && targetIndexes.equals(((MergeCommand) other).targetIndexes));
    }
}
