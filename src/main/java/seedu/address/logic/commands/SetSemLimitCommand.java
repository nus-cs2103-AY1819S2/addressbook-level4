package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_LECTURE_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_LECTURE_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.SemLimit;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Hour;

/**
 * Edits the details of an semester grade and workload limits in the address book.
 */
public class SetSemLimitCommand extends Command {

    public static final String COMMAND_WORD = "setlimit";

    public static final int NUMSEM = 10;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the "
            + "Grade and Workload limits of a semester. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SEMESTER + "SEMESTER] "
            + "[" + PREFIX_MIN_GRADE + "EXPECTED MIN GRADE] "
            + "[" + PREFIX_MAX_GRADE + "EXPECTED MAX GRADE] "
            + "[" + PREFIX_MIN_LECTURE_HOUR + "LECTURE HOUR] "
            + "[" + PREFIX_MAX_LECTURE_HOUR + "TUTORIAL HOUR]...\n "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SEMESTER + "Y3S1 "
            + PREFIX_MIN_GRADE + "B";

    public static final String MESSAGE_EDIT_LIMIT_SUCCESS = "Edited Semester: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditSemLimitDescriptor editSemLimitDescriptor;

    /**
     * @param index of the moduleTaken in the filtered moduleTaken list to edit
     * @param editLimitDescriptor details to edit the moduleTaken with
     */
    public SetSemLimitCommand(Index index, EditSemLimitDescriptor editLimitDescriptor) {
        requireNonNull(index);
        requireNonNull(editLimitDescriptor);

        this.index = index;
        this.editSemLimitDescriptor = new EditSemLimitDescriptor(editLimitDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<SemLimit> lastShownList = model.getSemLimitList();

        if (index.getZeroBased() >= NUMSEM) {
            throw new CommandException(Messages.MESSAGE_INVALID_SEMESTER_LIMIT);
        }

        SemLimit semLimitToEdit = lastShownList.get(index.getZeroBased());
        SemLimit editedSemLimit = createEditedLimit(semLimitToEdit, editSemLimitDescriptor);

        model.setSemesterLimit(index.getZeroBased(), editedSemLimit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_LIMIT_SUCCESS, editedSemLimit));
    }

    /**
     * Creates and returns a {@code SemLimit} with the details of {@code semLimitToEdit}
     * edited with {@code EditSemLimitDescriptor}.
     */
    private static SemLimit createEditedLimit(SemLimit semLimitToEdit,
                                                  EditSemLimitDescriptor editSemLimitDescriptor) {
        assert semLimitToEdit != null;

        Grade updatedMinGrade = editSemLimitDescriptor
                .getMinGrade().orElse(semLimitToEdit.getMinGrade());
        Grade updatedMaxGrade = editSemLimitDescriptor
                .getMaxGrade().orElse(semLimitToEdit.getMaxGrade());
        Hour updatedMinLectureHour = editSemLimitDescriptor
                .getMinLectureHour().orElse(semLimitToEdit.getMinLectureHour());
        Hour updatedMaxLectureHour = editSemLimitDescriptor
                .getMaxLectureHour().orElse(semLimitToEdit.getMaxLectureHour());

        return new SemLimit(updatedMinGrade, updatedMaxGrade,
                updatedMinLectureHour, updatedMaxLectureHour);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetSemLimitCommand)) {
            return false;
        }

        // state check
        SetSemLimitCommand e = (SetSemLimitCommand) other;
        return index == e.index
                && editSemLimitDescriptor.equals(e.editSemLimitDescriptor);
    }

    /**
     * Stores the details to edit the limit with. Each non-empty field value will replace the
     * corresponding field value of the limit.
     */
    public static class EditSemLimitDescriptor {
        private Grade minGrade;
        private Grade maxGrade;
        private Hour minLectureHour;
        private Hour maxLectureHour;

        public EditSemLimitDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditSemLimitDescriptor(EditSemLimitDescriptor toCopy) {
            setMinGrade(toCopy.minGrade);
            setMaxGrade(toCopy.maxGrade);
            setMinLectureHour(toCopy.minLectureHour);
            setMaxLectureHour(toCopy.maxLectureHour);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(minGrade, maxGrade, minLectureHour, maxLectureHour);
        }

        public void setMinGrade(Grade minGrade) {
            this.minGrade = minGrade;
        }

        public Optional<Grade> getMinGrade() {
            return Optional.ofNullable(minGrade);
        }

        public void setMaxGrade(Grade maxGrade) {
            this.maxGrade = maxGrade;
        }

        public Optional<Grade> getMaxGrade() {
            return Optional.ofNullable(maxGrade);
        }

        public void setMinLectureHour(Hour minlectureHour) {
            this.minLectureHour = minLectureHour;
        }

        public Optional<Hour> getMinLectureHour() {
            return Optional.ofNullable(minLectureHour);
        }

        public void setMaxLectureHour(Hour maxlectureHour) {
            this.maxLectureHour = maxLectureHour;
        }

        public Optional<Hour> getMaxLectureHour() {
            return Optional.ofNullable(maxLectureHour);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditSemLimitDescriptor)) {
                return false;
            }

            // state check
            EditSemLimitDescriptor e = (EditSemLimitDescriptor) other;

            return getMinGrade().equals(e.getMinGrade())
                    && getMaxGrade().equals(e.getMaxGrade())
                    && getMinLectureHour().equals(e.getMinLectureHour())
                    && getMaxLectureHour().equals(e.getMaxLectureHour());
        }
    }
}
