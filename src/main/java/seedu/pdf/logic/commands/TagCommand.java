package seedu.pdf.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_TAG_ADD;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_TAG_NAME;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_TAG_REMOVE;
import static seedu.pdf.model.Model.PREDICATE_SHOW_ALL_PDFS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.pdf.commons.core.Messages;
import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.model.tag.Tag;

/**
 * Tags a pdf identified using it's displayed index from the PDF book.
 */
public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets or removes tag(s) related to a selected pdf indicated "
            + "by the index number used in the displayed pdf list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG_ADD + "/" + PREFIX_TAG_REMOVE + " " + PREFIX_TAG_NAME + "[TAG]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_TAG_ADD + " "
            + PREFIX_TAG_NAME + "CS2103T " + PREFIX_TAG_NAME + "SE\n"
            + "Example: " + COMMAND_WORD + " 2 " + PREFIX_TAG_REMOVE + " " + PREFIX_TAG_NAME + "School\n";
    public static final String MESSAGE_TAG_PDF_SUCCESS = "Edited tag for Pdf:\n%1$s";

    private final Index index;
    private final Set<Tag> tags;
    private Boolean isAddTag;

    public TagCommand(Index index, Set<Tag> tags, Boolean isAddTag) {
        requireNonNull(index);
        requireNonNull(tags);
        requireNonNull(isAddTag);

        this.index = index;
        this.tags = tags;
        this.isAddTag = isAddTag;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Pdf> lastShownList = model.getFilteredPdfList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
        }

        Pdf oPdf = lastShownList.get(index.getZeroBased());
        Pdf nPdf;
        if (isAddTag) {
            nPdf = TagCommand.getPdfWithNewTag(oPdf, tags);
        } else {
            nPdf = TagCommand.getPdfWithRemovedTag(oPdf, tags);
        }

        model.setPdf(oPdf, nPdf);
        model.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);
        model.commitPdfBook();

        return new CommandResult(String.format(MESSAGE_TAG_PDF_SUCCESS, nPdf.toString()));
    }

    public static Pdf getPdfWithNewTag(Pdf old, Set<Tag> tags) {
        Set<Tag> oTags = old.getTags();
        Set<Tag> nTags = new HashSet<>(oTags);
        nTags.addAll(tags);
        return new Pdf(old.getName(), old.getDirectory(), old.getSize(), nTags, old.getDeadline());
    }

    public static Pdf getPdfWithRemovedTag(Pdf old, Set<Tag> tags) throws CommandException {
        Set<Tag> oTags = old.getTags();
        Set<Tag> nTags = new HashSet<>(oTags);
        boolean hasTag = nTags.removeAll(tags);
        if (hasTag) {
            return new Pdf(old.getName(), old.getDirectory(), old.getSize(), nTags, old.getDeadline());
        } else {
            throw new CommandException(Messages.MESSAGE_NO_TAG_IN_PDF);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        // state check
        TagCommand e = (TagCommand) other;
        return index.equals(e.index) && tags.equals(e.tags) && isAddTag == e.isAddTag;
    }
}
