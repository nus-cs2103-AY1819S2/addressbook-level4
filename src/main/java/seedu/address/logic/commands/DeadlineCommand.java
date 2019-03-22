package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pdf.Deadline;
import seedu.address.model.pdf.Pdf;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_PDF_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PDFS;

public class DeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a deadline to a selected pdf indicated "
            + "by the index number used in the displayed pdf list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DEADLINE + "DEADLINE] (In dd-mm-yyyy format)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DEADLINE + "13-02-2020";

    private final Index index;
    private final Deadline deadline;

    public DeadlineCommand(Index index, Deadline deadline) {
        requireNonNull(index);
        requireNonNull(deadline);

        this.index = index;
        this.deadline = deadline;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Pdf> lastShownList = model.getFilteredPdfList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Pdf oPdf = lastShownList.get(this.index.getZeroBased());
        Pdf nPdf = DeadlineCommand.getPdfWithNewDeadline(oPdf, this.deadline);

        model.setPdf(oPdf, nPdf);
        model.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);
        model.commitPdfBook();

        return new CommandResult(String.format(MESSAGE_EDIT_PDF_SUCCESS, nPdf));
    }

    public static Pdf getPdfWithNewDeadline(Pdf old, Deadline deadline) {
        return new Pdf(old.getName(), old.getDirectory(), old.getSize(), old.getTags(), deadline);
    }
}
