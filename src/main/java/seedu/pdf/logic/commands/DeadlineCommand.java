package seedu.pdf.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DEADLINE_DONE;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DEADLINE_NEW;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DEADLINE_REMOVE;
import static seedu.pdf.model.Model.PREDICATE_SHOW_ALL_PDFS;

import java.util.List;

import seedu.pdf.commons.core.Messages;
import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Deadline;
import seedu.pdf.model.pdf.Pdf;

/**
 * Edits the deadline of an existing pdf in the pdf book.
 */
public class DeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the deadline of the selected pdf indicated "
            + "by the index number used in the displayed pdf list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) ACTION\n"
            + "There are 3 types of ACTION:\n"
            + "1. date/<DATE>\n"
            + "      sets/replaces the existing deadline of the selected pdf\n"
            + "      Example: " + COMMAND_WORD + " 1 " + PREFIX_DEADLINE_NEW + "13-02-2020\n"
            + "2. done\n"
            + "      marks the existing deadline of the selected pdf as DONE\n"
            + "      Example: " + COMMAND_WORD + " 2 " + PREFIX_DEADLINE_DONE + "\n"
            + "3. remove\n"
            + "      removes the existing deadline of the selected pdf\n"
            + "      Example: " + COMMAND_WORD + " 2 " + PREFIX_DEADLINE_REMOVE + "\n";
    public static final String MESSAGE_EDIT_PDF_DEADLINE_SUCCESS = "Edited deadline for Pdf:\n%1$s";

    private final Index index;
    private final Deadline deadline;
    private final DeadlineAction action;

    /**
     * Represents a Pdf deadline's  status in the pdf book.
     * Guarantees: immutable;
     */
    public enum DeadlineAction {
        NEW("NEW"),
        DONE("DONE"),
        REMOVE("REMOVE");

        private String status;

        DeadlineAction(String status) {
            this.status = status;
        }
    }

    public DeadlineCommand(Index index, Deadline deadline, DeadlineAction action) {
        requireNonNull(index);
        requireNonNull(deadline);
        requireNonNull(action);

        this.index = index;
        this.deadline = deadline;
        this.action = action;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Pdf> lastShownList = model.getFilteredPdfList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
        }

        Pdf oPdf = lastShownList.get(this.index.getZeroBased());
        Pdf nPdf;
        Deadline oDeadline = oPdf.getDeadline();

        if (action == DeadlineAction.NEW) {
            nPdf = getPdfWithNewDeadline(oPdf, deadline);
        } else if (action == DeadlineAction.DONE) {
            if (oPdf.getDeadline().exists()) {
                nPdf = getPdfWithNewDeadline(oPdf, Deadline.setDone(oDeadline));
            } else {
                throw new CommandException(Messages.MESSAGE_NO_DEADLINE_IN_PDF);
            }
        } else {
            if (oPdf.getDeadline().exists()) {
                nPdf = getPdfWithNewDeadline(oPdf, Deadline.setRemove(oDeadline));
            } else {
                throw new CommandException(Messages.MESSAGE_NO_DEADLINE_IN_PDF);
            }
        }

        model.setPdf(oPdf, nPdf);
        model.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);
        model.commitPdfBook();

        return new CommandResult(String.format(MESSAGE_EDIT_PDF_DEADLINE_SUCCESS, nPdf.toString()));
    }

    public static Pdf getPdfWithNewDeadline(Pdf old, Deadline deadline) {
        return new Pdf(old.getName(), old.getDirectory(), old.getSize(), old.getTags(), deadline);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeadlineCommand)) {
            return false;
        }

        // state check
        DeadlineCommand e = (DeadlineCommand) other;
        return index.equals(e.index)
                && deadline.equals(e.deadline)
                && action.equals(e.action);
    }
}
