package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_NEW;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pdf.Pdf;
import seedu.address.model.pdf.exceptions.DuplicatePdfException;

/**
 * Adds a PDF to the PDF book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a pdf to the PDF book. "
            + "Parameters: "
            + PREFIX_FILE + "FILEPATH "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "Desktop\\main\\target";

    public static final String MESSAGE_SUCCESS = "New PDF added: %1$s";
    public static final String MESSAGE_DUPLICATE_PDF = "This pdf already exists in the PDF book";
    public static final String MESSAGE_INVALID_SELECTION = "Selected file is not supported by PDF++";

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

        model.addPdf(toAdd);
        model.commitPdfBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
