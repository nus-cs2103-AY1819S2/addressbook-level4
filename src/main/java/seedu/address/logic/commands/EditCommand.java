package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.QuizState;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Face;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.ImagePath;
import seedu.address.model.flashcard.Statistics;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing flashcard in the card collection.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the flashcard identified "
        + "by the index number used in the displayed flashcard list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_FRONT_FACE + "FRONTFACE] "
        + "[" + PREFIX_BACK_FACE + "BACKFACE] "
        + "[" + PREFIX_IMAGE + "IMAGE] "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_FRONT_FACE + "Hola "
        + PREFIX_BACK_FACE + "你好";

    public static final String MESSAGE_EDIT_FLASHCARD_SUCCESS = "Edited Flashcard: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the card collection.";
    private static final String MESSAGE_IN_QUIZ = "Cannot edit in quiz mode";

    private final Index index;
    private final EditFlashcardDescriptor editFlashcardDescriptor;

    /**
     * @param index                   of the flashcard in the filtered flashcard list to edit
     * @param editFlashcardDescriptor details to edit the flashcard with
     */
    public EditCommand(Index index, EditFlashcardDescriptor editFlashcardDescriptor) {
        requireNonNull(index);
        requireNonNull(editFlashcardDescriptor);

        this.index = index;
        this.editFlashcardDescriptor = new EditFlashcardDescriptor(editFlashcardDescriptor);
    }

    /**
     * Creates and returns a {@code Flashcard} with the details of {@code flashcardToEdit}
     * edited with {@code editFlashcardDescriptor}.
     */
    private static Flashcard createEditedFlashcard(Flashcard flashcardToEdit,
                                                   EditFlashcardDescriptor editFlashcardDescriptor) {
        assert flashcardToEdit != null;

        Face updatedFrontFace = editFlashcardDescriptor.getFrontFace().orElse(flashcardToEdit.getFrontFace());
        Face updatedBackFace = editFlashcardDescriptor.getBackFace().orElse(flashcardToEdit.getBackFace());
        ImagePath updatedImagePath = editFlashcardDescriptor.getImagePath().orElse(flashcardToEdit.getImagePath());
        Set<Tag> updatedTags = editFlashcardDescriptor.getTags().orElse(flashcardToEdit.getTags());

        Statistics statistics = flashcardToEdit.getStatistics(); // statistics cannot be edited
        return new Flashcard(updatedFrontFace, updatedBackFace, updatedImagePath, statistics, updatedTags);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getQuizMode() != QuizState.NOT_QUIZ_MODE) {
            throw new CommandException(MESSAGE_IN_QUIZ);
        }
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Flashcard flashcardToEdit = lastShownList.get(index.getZeroBased());
        Flashcard editedFlashcard = createEditedFlashcard(flashcardToEdit, editFlashcardDescriptor);

        if (!flashcardToEdit.isSameFlashcard(editedFlashcard) && model.hasFlashcard(editedFlashcard)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        model.setFlashcard(flashcardToEdit, editedFlashcard);
        model.commitCardCollection();
        return new CommandResult(String.format(MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard));
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
            && editFlashcardDescriptor.equals(e.editFlashcardDescriptor);
    }

    /**
     * Stores the details to edit the flashcard with. Each non-empty field value will replace the
     * corresponding field value of the flashcard.
     */
    public static class EditFlashcardDescriptor {
        private Face frontFace;
        private Face backFace;
        private ImagePath imagePath;
        private Set<Tag> tags;

        public EditFlashcardDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditFlashcardDescriptor(EditFlashcardDescriptor toCopy) {
            setFrontFace(toCopy.frontFace);
            setBackFace(toCopy.backFace);
            setImagePath(toCopy.imagePath);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(frontFace, backFace, imagePath, tags);
        }

        public Optional<Face> getFrontFace() {
            return Optional.ofNullable(frontFace);
        }

        public void setFrontFace(Face frontFace) {
            this.frontFace = frontFace;
        }

        public Optional<Face> getBackFace() {
            return Optional.ofNullable(backFace);
        }

        public void setBackFace(Face backFace) {
            this.backFace = backFace;
        }

        public Optional<ImagePath> getImagePath() {
            return Optional.ofNullable(imagePath);
        }

        public void setImagePath(ImagePath imagePath) {
            this.imagePath = imagePath;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof EditFlashcardDescriptor)) {
                return false;
            }
            EditFlashcardDescriptor that = (EditFlashcardDescriptor) o;

            // Due to the strange nature of constructing these, it is necessary
            // to check if the image path actually points to a valid image when
            // comparing edit commands.
            if (!getImagePath().isPresent() && !that.getImagePath().isPresent()) {
                return getFrontFace().equals(that.getFrontFace())
                    && getBackFace().equals(that.getBackFace())
                    && getTags().equals(that.getTags());
            } else if (getImagePath().isPresent() && !that.getImagePath().isPresent()) {
                if (getImagePath().get().hasImagePath()) {
                    return false;
                } else {
                    return getFrontFace().equals(that.getFrontFace())
                        && getBackFace().equals(that.getBackFace())
                        && getTags().equals(that.getTags());
                }
            } else if (!getImagePath().isPresent() && that.getImagePath().isPresent()) {
                if (!that.getImagePath().get().hasImagePath()) {
                    return false;
                } else {
                    return getFrontFace().equals(that.getFrontFace())
                        && getBackFace().equals(that.getBackFace())
                        && getTags().equals(that.getTags());
                }
            } else {
                return getFrontFace().equals(that.getFrontFace())
                    && getBackFace().equals(that.getBackFace())
                    && getImagePath().equals(that.getImagePath())
                    && getTags().equals(that.getTags());
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(getFrontFace(), getBackFace(), getImagePath(), getTags());
        }
    }
}
