package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PDFS;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pdf.Pdf;

/**
 * Encrypts the an existing pdf in the pdfBook.
 */
public class EncryptCommand extends Command {
    public static final String COMMAND_WORD = "encrypt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Encrypts the the pdf identified "
            + "by the index number used in the displayed pdf list. "
            + "Please ensure that the file is not encrypted.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PASSWORD + "PASSWORD]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PASSWORD + "NewSecuredPassword";
    private static final String MESSAGE_ENCRYPT_PDF_SUCCESS = "Encrypted PDF: %1$s";
    private static final String MESSAGE_ENCRYPT_PDF_FAILURE = "%1$s did not get encrypted successfully.\n"
            + "Please check if the file is not already encrypted and it exists.";
    private static final int ENCRYPTION_KEY_LENGTH = 128;

    private final Index index;
    private final String password;

    /**
     * @param index of the pdf in the filtered pdf list to encrypt
     * @param password of the pdf
     */
    public EncryptCommand(Index index, String password) {
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

        Pdf pdfToEncrypt = lastShownList.get(this.index.getZeroBased());
        Pdf pdfEncrypted = encryptPdf(pdfToEncrypt);

        model.setPdf(pdfToEncrypt, pdfEncrypted);
        model.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);
        model.commitPdfBook();

        return new CommandResult(String.format(MESSAGE_ENCRYPT_PDF_SUCCESS, pdfEncrypted));
    }

    /**
     * Encrypts and returns the encrypted {@code pdfToEncrypt}
     */
    private Pdf encryptPdf(Pdf pdfToEncrypt) throws CommandException {
        try {
            PDDocument file = PDDocument.load(Paths.get(pdfToEncrypt.getDirectory().getDirectory(),
                    pdfToEncrypt.getName().getFullName()).toFile());
            AccessPermission ap = new AccessPermission();
            StandardProtectionPolicy spp = new StandardProtectionPolicy(password, password, ap);

            spp.setEncryptionKeyLength(ENCRYPTION_KEY_LENGTH);
            spp.setPermissions(ap);
            file.protect(spp);
            file.save(Paths.get(pdfToEncrypt.getDirectory().getDirectory(),
                    pdfToEncrypt.getName().getFullName()).toFile());
            return getEncryptedPdf(pdfToEncrypt);
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_ENCRYPT_PDF_FAILURE, pdfToEncrypt.getName()));
        }
    }

    /**
     * Encrypts and returns the encrypted {@code pdfToEncrypt}
     */
    private Pdf getEncryptedPdf(Pdf pdfToEncrypt) {
        return new Pdf(pdfToEncrypt, true);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EncryptCommand)) {
            return false;
        }

        // state check
        EncryptCommand e = (EncryptCommand) other;
        return index.equals(e.index) && password.equals(e.password);
    }
}
