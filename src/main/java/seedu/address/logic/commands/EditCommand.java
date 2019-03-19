package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PDFS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import java.io.File;
import java.nio.file.Paths;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pdf.Name;
import seedu.address.model.pdf.Pdf;
import seedu.address.model.pdf.Size;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing pdf in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the pdf identified "
            + "by the index number used in the displayed pdf list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Tutorial3.pdf "
            + PREFIX_TAG + "Easy";

    public static final String MESSAGE_EDIT_PDF_SUCCESS = "Edited PDF: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This pdf already exists in the address book.";
    public static final String MESSAGE_EDIT_PDF_FAILURE = "Unable to Edit PDF.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the pdf in the filtered pdf list to edit
     * @param editPersonDescriptor details to edit the pdf with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Pdf> lastShownList = model.getFilteredPdfList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Pdf pdfToEdit = lastShownList.get(index.getZeroBased());
        Pdf editedPdf = createEditedPerson(pdfToEdit, editPersonDescriptor);

        File oFile = Paths.get(pdfToEdit.getDirectory().getDirectory(), pdfToEdit.getName().getFullName()).toFile();
        File nFile = Paths.get(editedPdf.getDirectory().getDirectory(), editedPdf.getName().getFullName()).toFile();
        boolean editSuccess = oFile.renameTo(nFile);

        if (!editSuccess) {
            throw new CommandException(MESSAGE_EDIT_PDF_FAILURE);
        }

        if (!pdfToEdit.isSamePdf(editedPdf) && model.hasPdf(editedPdf)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPdf(pdfToEdit, editedPdf);
        model.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);
        model.commitPdfBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PDF_SUCCESS, editedPdf));
    }

    /**
     * Creates and returns a {@code Pdf} with the details of {@code pdfToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Pdf createEditedPerson(Pdf pdfToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert pdfToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(pdfToEdit.getName());
        Size updatedSize = new Size(Long.toString(Paths.get(pdfToEdit.getDirectory().getDirectory(),
                pdfToEdit.getName().getFullName()).toFile().getTotalSpace()));
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(pdfToEdit.getTags());

        return new Pdf(updatedName, pdfToEdit.getDirectory(), updatedSize, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the pdf with. Each non-empty field value will replace the
     * corresponding field value of the pdf.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getTags().equals(e.getTags());
        }
    }
}
