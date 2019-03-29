package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pdf.Directory;
import seedu.address.model.pdf.Name;
import seedu.address.model.pdf.Pdf;
import seedu.address.model.pdf.Size;

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

    private static final String MESSAGE_MERGE_PDF_SUCCESS = "Merged PDFs into new PDF: %1$s";
    private static final String MESSAGE_MERGE_PDF_FAIL = "Merging of PDFs encountered an error and stopped.";

    private static final int FIRST_INDEX = 0;

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

        if (targetIndexes.stream().anyMatch(index -> index.getZeroBased() >= lastShownList.size())){
            throw new CommandException(Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
        }

        ArrayList<File> pdfsToMerge = new ArrayList<>();
        targetIndexes.forEach(index -> {
            Pdf pdf = lastShownList.get(index.getZeroBased());
            pdfsToMerge.add(Paths.get(pdf.getDirectory().getDirectory(), pdf.getName().getFullName()).toFile());
        });

        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        String mergedPdfDirectory = lastShownList.get(FIRST_INDEX).getDirectory().getDirectory();
        String mergedPdfName = "merged" + hashCode();
        //Check that no duplicate file name (how on earth would it happen though)
        while (Paths.get(mergedPdfDirectory, mergedPdfName).toAbsolutePath().toFile().exists()){
            //Just throwing zeros at the back of the name until it is unique
            mergedPdfName += FIRST_INDEX;
        }
        PDFmerger.setDestinationFileName(String.format(mergedPdfDirectory, mergedPdfName));

        ArrayList<InputStream> sources = new ArrayList<>();
        for (File file : pdfsToMerge) {
            try {
                sources.add(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                throw new CommandException(String.format(Messages.MESSAGE_PDF_OPEN_FAIL, file.getName()));
            }
        }

        PDFmerger.addSources(sources);
        try {
            PDFmerger.mergeDocuments();
        } catch (IOException e) {
            throw new CommandException(MESSAGE_MERGE_PDF_FAIL);
        }

        //Add merged pdf to application
        assert Paths.get(mergedPdfDirectory, mergedPdfName).toAbsolutePath().toFile().exists();
        File mergedFile = Paths.get(mergedPdfDirectory, mergedPdfName).toAbsolutePath().toFile();
        Pdf mergedPdf = new Pdf(
                new Name(mergedFile.getName()),
                new Directory(mergedFile.getPath()),
                new Size(String.valueOf(mergedFile.length())),
                new HashSet<>());

        model.addPdf(mergedPdf);
        model.commitPdfBook();
        return new CommandResult(String.format(MESSAGE_MERGE_PDF_SUCCESS, mergedPdf));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeCommand // instanceof handles nulls
                && targetIndexes.equals(((MergeCommand) other).targetIndexes));
    }
}
