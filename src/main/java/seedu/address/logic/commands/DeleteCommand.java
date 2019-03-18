package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pdf.Pdf;

import java.io.File;
import java.nio.file.Paths;

/**
 * Deletes a pdf identified using it's displayed index from the pdf book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public enum DELETE_TYPE {
        HARD, SOFT
    }

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the PDF identified by the index number used in the displayed PDF list.\n"
            + "Hard or soft delete can be chosen (soft delete selected by default).\n"
            + "Parameters(Soft Delete): INDEX (must be a positive integer)\n"
            + "Parameters(Hard Delete): INDEX (must be a positive integer) hard\n"
            + "Example: " + COMMAND_WORD + " 1 | " + COMMAND_WORD + " 1 hard";

    public static final String MESSAGE_DELETE_PDF_SUCCESS = "Deleted PDF: %1$s";
    public static final String MESSAGE_DELETE_HARD_FAIL = "PDF unable to be deleted at filesystem.";

    private final Index targetIndex;

    private final DELETE_TYPE deleteType;

    public DeleteCommand(Index targetIndex, DELETE_TYPE Type) {
        this.targetIndex = targetIndex;
        this.deleteType = Type;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Pdf> lastShownList = model.getFilteredPdfList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Pdf pdfToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePdf(pdfToDelete);

        if(deleteType == DELETE_TYPE.HARD){
            File dFile = Paths.get(pdfToDelete.getDirectory().getDirectory(), pdfToDelete.getName().getFullName()).toFile();
            if(!dFile.delete()){
                throw new CommandException(MESSAGE_DELETE_HARD_FAIL);
            }
        }

        model.commitPdfBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PDF_SUCCESS, pdfToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)
                && deleteType.equals((((DeleteCommand) other).deleteType))); // state check
    }
}
