package seedu.pdf.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.pdf.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pdf.model.Model.PREDICATE_SHOW_ALL_PDFS;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.pdf.commons.core.Messages;
import seedu.pdf.commons.core.index.Index;
import seedu.pdf.commons.util.CollectionUtil;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Deadline;
import seedu.pdf.model.pdf.Name;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.model.pdf.Size;
import seedu.pdf.model.tag.Tag;

/**
 * Renames an existing pdf in the pdfBook.
 */
public class RenameCommand extends Command {

    public static final String COMMAND_WORD = "rename";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Renames the  pdf identified "
            + "by the index number used in the displayed pdf list. "
            + "Existing name will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Tutorial3.pdf";

    public static final String MESSAGE_EDIT_PDF_SUCCESS = "Renamed Pdf:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PDF = "This pdf already exists in the pdf book.";
    public static final String MESSAGE_DUPLICATE_PDF_DIRECTORY = "There exists another %s within %s.";
    public static final String MESSAGE_RENAME_PDF_FAILURE = "Unable to Edit PDF.";

    private final Index index;
    private final EditPdfDescriptor editPdfDescriptor;

    /**
     * @param index of the pdf in the filtered pdf list to edit
     * @param editPdfDescriptor details to edit the pdf with
     */
    public RenameCommand(Index index, EditPdfDescriptor editPdfDescriptor) {
        requireNonNull(index);
        requireNonNull(editPdfDescriptor);

        this.index = index;
        this.editPdfDescriptor = new EditPdfDescriptor(editPdfDescriptor);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Pdf> lastShownList = model.getFilteredPdfList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
        }

        Pdf pdfToEdit = lastShownList.get(index.getZeroBased());
        Pdf editedPdf = createEditedPdf(pdfToEdit, editPdfDescriptor);

        if (!pdfToEdit.getName().getFullName().equals(editedPdf.getName().getFullName())
                && Paths.get(editedPdf.getDirectory().getDirectory(), editedPdf.getName().getFullName())
                  .toAbsolutePath().toFile().exists()) {

            throw new CommandException(String.format(MESSAGE_DUPLICATE_PDF_DIRECTORY,
                editedPdf.getName().getFullName(), pdfToEdit.getDirectory().getDirectory()));
        }

        if (pdfToEdit.isSamePdf(editedPdf) || model.hasPdf(editedPdf)) {
            throw new CommandException(MESSAGE_DUPLICATE_PDF);
        }

        File oldFile = Paths.get(pdfToEdit.getDirectory().getDirectory(), pdfToEdit.getName().getFullName()).toFile();
        File newFile = Paths.get(editedPdf.getDirectory().getDirectory(), editedPdf.getName().getFullName()).toFile();

        if (!oldFile.renameTo(newFile)) {
            throw new CommandException(MESSAGE_RENAME_PDF_FAILURE);
        }

        model.setPdf(pdfToEdit, editedPdf);
        model.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);
        model.commitPdfBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PDF_SUCCESS, editedPdf.toString()));
    }

    /**
     * Returns true is the file with the updated name exists in the directory, false otherwise.
     */
    private static boolean isFileExists(Pdf editedPdf) {
        String[] files = Paths.get(editedPdf.getDirectory().getDirectory()).toFile().list();
        return Arrays.stream(files).anyMatch(x -> editedPdf.getName().getFullName().equals(x));
    }

    /**
     * Creates and returns a {@code Pdf} with the details of {@code pdfToEdit}
     * edited with {@code editPdfDescriptor}.
     */
    private static Pdf createEditedPdf(Pdf pdfToEdit, EditPdfDescriptor editPdfDescriptor) {
        assert pdfToEdit != null;

        Name updatedName = editPdfDescriptor.getName().orElse(pdfToEdit.getName());
        Size updatedSize = new Size(Long.toString(Paths.get(pdfToEdit.getDirectory().getDirectory(),
                pdfToEdit.getName().getFullName()).toFile().length()));
        Set<Tag> updatedTags = editPdfDescriptor.getTags().orElse(pdfToEdit.getTags());
        Deadline updatedDeadline = pdfToEdit.getDeadline();

        return new Pdf(updatedName, pdfToEdit.getDirectory(), updatedSize,
                updatedTags, updatedDeadline, pdfToEdit.getIsEncrypted());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RenameCommand)) {
            return false;
        }

        // state check
        RenameCommand e = (RenameCommand) other;
        return index.equals(e.index)
                && editPdfDescriptor.equals(e.editPdfDescriptor);
    }

    /**
     * Stores the details to edit the pdf with. Each non-empty field value will replace the
     * corresponding field value of the pdf.
     */
    public static class EditPdfDescriptor {
        private Name name;
        private Size size;
        private Set<Tag> tags;

        public EditPdfDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPdfDescriptor(EditPdfDescriptor toCopy) {
            setName(toCopy.name);
            setSize(toCopy.size);
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

        public void setSize(Size size) {
            this.size = size;
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
            if (!(other instanceof EditPdfDescriptor)) {
                return false;
            }

            // state check
            EditPdfDescriptor e = (EditPdfDescriptor) other;

            return getName().equals(e.getName())
                    && getTags().equals(e.getTags());
        }
    }
}
