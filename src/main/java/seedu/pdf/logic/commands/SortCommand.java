package seedu.pdf.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.pdf.logic.CommandHistory;
import seedu.pdf.model.Model;
import seedu.pdf.model.PdfBook;
import seedu.pdf.model.pdf.Pdf;

/**
 * Sorts all PDF files in alphabetical or deadline or file size order.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all the PDFs based on certain criteria.\n"
            + "Parameters: CRITERIA (name/deadline) ORDER (up/down)\n"
            + "CRITERIA can only be either name, deadline or size\n"
            + "ORDER can only be either up or down corresponding to ascending or descending order\n"
            + "Example: " + COMMAND_WORD + " name up\n"
            + "Example: " + COMMAND_WORD + " deadline down\n"
            + "Example: " + COMMAND_WORD + " size up\n";

    public static final String MESSAGE_SUCCESS = "Sort success!";

    private final Comparator<Pdf> pdfComparator;

    public SortCommand(Comparator<Pdf> cm) {
        requireNonNull(cm);

        this.pdfComparator = cm;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        PdfBook newBook = new PdfBook();
        newBook.setPdfs(model.getPdfBook().getPdfList().sorted(pdfComparator));
        model.setPdfBook(newBook);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && pdfComparator.equals(((SortCommand) other).pdfComparator));
    }
}
