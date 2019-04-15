package seedu.pdf.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.pdf.model.Model.PREDICATE_SHOW_ALL_PDFS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import seedu.pdf.commons.core.Messages;
import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Pdf;

/**
 * Encrypts the an existing pdf in the pdfBook.
 */
public class DecryptCommand extends Command {
    public static final String COMMAND_WORD = "decrypt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Decrypts the the pdf identified "
            + "by the index number used in the displayed pdf list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PASSWORD + "PASSWORD]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PASSWORD + "SecuredPassword";

    public static final String MESSAGE_DECRYPT_PDF_SUCCESS = "Decrypted PDF:\n%1$s";
    public static final String MESSAGE_DECRYPT_PDF_FAILURE = "%1$sdid not get decrypted successfully.\n"
            + "Please check your if the file exists and the password is correct.";
    public static final String MESSAGE_DECRYPT_PDF_ALREADY_DECRYPTED_FAILURE = "%1$sis already decrypted.";

    private final Index index;
    private final String password;

    /**
     * @param index of the pdf in the filtered pdf list to decrypt
     * @param password of the pdf
     */
    public DecryptCommand(Index index, String password) {
        requireNonNull(index);
        requireNonNull(password);

        this.index = index;
        this.password = password;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Pdf> lastShownList = model.getFilteredPdfList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
        }

        Pdf pdfToDecrypt = lastShownList.get(this.index.getZeroBased());
        Pdf decryptedPdf = decryptPdf(pdfToDecrypt);

        model.setPdf(pdfToDecrypt, decryptedPdf);
        model.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);
        //model.commitPdfBook();

        return new CommandResult(String.format(MESSAGE_DECRYPT_PDF_SUCCESS, decryptedPdf.toString()));
    }

    /**
     * Encrypts and returns the decrypted {@code pdfToDecrypt}
     */
    private Pdf decryptPdf(Pdf pdfToDecrypt) throws CommandException {
        try {
            PDDocument file = PDDocument.load(new File(pdfToDecrypt.getDirectory().getDirectory(),
                    pdfToDecrypt.getName().getFullName()), this.password);
            if (!file.isEncrypted()) {
                throw new CommandException(String.format(MESSAGE_DECRYPT_PDF_ALREADY_DECRYPTED_FAILURE,
                        pdfToDecrypt.getName()));
            }
            file.setAllSecurityToBeRemoved(true);
            file.save(Paths.get(pdfToDecrypt.getDirectory().getDirectory(),
                    pdfToDecrypt.getName().getFullName()).toFile());
            file.close();
            return getDecryptedPdf(pdfToDecrypt);
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_DECRYPT_PDF_FAILURE, pdfToDecrypt.getName()));
        }
    }

    /**
     * Decrypts and returns the decrypted {@code pdfToDecrypt}
     */
    private Pdf getDecryptedPdf(Pdf pdfToDecrypt) {
        return new Pdf(pdfToDecrypt, false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DecryptCommand)) {
            return false;
        }

        // state check
        DecryptCommand e = (DecryptCommand) other;
        return index.equals(e.index) && password.equals(e.password);
    }
}
