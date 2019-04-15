package seedu.pdf.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_FILE;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;

import seedu.pdf.logic.CommandHistory;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.model.pdf.exceptions.DuplicatePdfException;


/**
 * Adds a PDF to the PDF book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a pdf to the PDF book. "
            + "Parameters: "
            + PREFIX_FILE + "FILEPATH "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "Desktop\\main\\target.pdf";

    public static final String MESSAGE_SUCCESS = "New PDF added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_PDF = "This pdf already exists in the PDF book";
    public static final String MESSAGE_INVALID_SELECTION = "Selected file is not supported by PDF++";
    public static final String MESSAGE_FILE_NOT_LOADABLE = "Selected file is corrupted and cannot be"
        + " loaded into PDF++.";

    private final Pdf toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Pdf}
     */
    public AddCommand(Pdf pdf) {
        requireNonNull(pdf);
        toAdd = pdf;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPdf(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PDF, new DuplicatePdfException());
        }
        loadFile(toAdd);

        model.addPdf(toAdd);
        model.commitPdfBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString()));
    }

    /**
     * Tries to load {@code toAdd}
     */
    private void loadFile(Pdf toAdd) throws CommandException {
        try {
            PDDocument file = PDDocument.load(Paths.get(toAdd.getDirectory().getDirectory(),
                    toAdd.getName().getFullName()).toFile());
            file.close();
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FILE_NOT_LOADABLE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    public String toString() {
        return "" + toAdd.toString();
    }
}
