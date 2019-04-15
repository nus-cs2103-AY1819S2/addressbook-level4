package seedu.pdf.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.pdf.logic.CommandHistory;
import seedu.pdf.model.Model;
import seedu.pdf.model.PdfBook;

/**
 * Clears the PDF book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "PDF book has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setPdfBook(new PdfBook());
        model.commitPdfBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
