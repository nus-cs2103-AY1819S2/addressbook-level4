package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.PdfBook;
import seedu.address.model.pdf.Pdf;


/**
 * Sorts all PDF files in alphabetical or deadline order.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all the PDFs based on certain criteria.\n"
            + "Parameters: CRITERIA (name/date) ORDER (up/down)\n"
            + "CRITERIA can only be either name or date corresponding to alphabetical or deadline order\n"
            + "ORDER can only be either up or down corresponding to ascending or descending order\n"
            + "Example: " + COMMAND_WORD + " name up\n"
            + "Example: " + COMMAND_WORD + " date down\n";

    public static final String MESSAGE_SUCCESS = "Sort success!";

    private final Comparator<Pdf> pdfComparator;

    public SortCommand(Comparator<Pdf> cm) {
        this.pdfComparator = cm;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        PdfBook newBook = new PdfBook();
        newBook.setPdfs(model.getPdfBook().getPdfList().sorted(pdfComparator));
        model.setPdfBook(newBook);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }

}
