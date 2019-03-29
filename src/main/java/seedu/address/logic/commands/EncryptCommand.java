package seedu.address.logic.commands;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pdf.Pdf;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

public class EncryptCommand extends Command {
    public static final String COMMAND_WORD = "encrypt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Encrypts the the pdf identified "
            + "by the index number used in the displayed pdf list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PASSWORD + "PASSWORD]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PASSWORD + "Tutorial3.pdf";

    private final Index index;
    private final String password;

    /**
     * @param index of the pdf in the filtered pdf list to edit
     */
    public EncryptCommand(Index index, String password) {
        requireNonNull(index);
        requireNonNull(password);

        this.index = index;
        this.password = password;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Pdf> lastShownList = model.getFilteredPdfList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
        }

        Pdf pdfToEncrypt = lastShownList.get(this.index.getZeroBased());
        Pdf pdfEncrypted = encryptPdf(pdfToEncrypt);


        return null;
    }

    private Pdf encryptPdf(Pdf pdfToEncrypt) {
        AccessPermission ap = new AccessPermission();
        //StandardProtectionPolicy spp = new StandardProtectionPolicy();
        
        PDDocument pdDocument = new PDDocument();
        PDPage pdPage = new PDPage();

        pdDocument.addPage(pdPage);
        return pdfToEncrypt;
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
        return index.equals(e.index)
                && password.equals(e.password);
    }
}
