package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileName;
import seedu.address.commons.util.pdf.PdfWrapper;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Prints a selected medicine identified using it's displayed index from the inventory.
 */
public class LabelCommand extends Command {

    public static final String COMMAND_WORD = "label";
    public static final String DEFAULT_FILENAME = "to_print";
    public static final int MAX_LENGTH_FILENAME = 60;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Prints the Medicine name and description in PDF format using its index.\n"
            + "Parameters: INDEX [f/(filename)]\n"
            + "Example: " + COMMAND_WORD + " 1" + " f/printThisFile.\n";

    public static final String MESSAGE_LABEL_MEDICINE_SUCCESS = "Successfully printed the medicine at index: %1$s"
            + " in PDF format";

    private final Index targetIndex;
    private final FileName fileName;

    /**
     * Creates an LabelCommand to label a specific medicine information using {@code Index}
     * onto a PDF file with the name {@code FileName}
     */
    public LabelCommand(Index targetIndex, FileName fileName) {
        this.targetIndex = targetIndex;
        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        PdfWrapper pdfCreation = new PdfWrapper(this.targetIndex, this.fileName, model);
        pdfCreation.label();

        return new CommandResult(String.format(MESSAGE_LABEL_MEDICINE_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LabelCommand // instanceof handles nulls
                && targetIndex.equals(((LabelCommand) other).targetIndex)); // state check
    }

}
