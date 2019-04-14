package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_CAP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_LAB_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_LECTURE_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_PREPARATION_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_PROJECT_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_TUTORIAL_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_CAP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_LAB_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_LECTURE_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_PREPARATION_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_PROJECT_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_TUTORIAL_HOUR;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.limits.SemesterLimit;
import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.Hour;

/**
 * Edits the details of an semester grade and workload limits in the address book.
 */
public class SetSemesterLimitCommand extends Command {

    public static final String COMMAND_WORD = "setlimit";

    public static final int NUMSEM = 10;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the "
            + "Grade and Workload limits of a semester. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: SEMESTER "
            + "[" + PREFIX_MIN_CAP + "MIN CAP] "
            + "[" + PREFIX_MAX_CAP + "MAX CAP] "
            + "[" + PREFIX_MIN_LECTURE_HOUR + "LECTURE HOUR] "
            + "[" + PREFIX_MAX_LECTURE_HOUR + "LECTURE HOUR] "
            + "[" + PREFIX_MIN_TUTORIAL_HOUR + "TUTORIAL HOUR] "
            + "[" + PREFIX_MAX_TUTORIAL_HOUR + "TUTORIAL HOUR] "
            + "[" + PREFIX_MIN_LAB_HOUR + "LAB HOUR] "
            + "[" + PREFIX_MAX_LAB_HOUR + "LAB HOUR] "
            + "[" + PREFIX_MIN_PROJECT_HOUR + "PROJECT HOUR] "
            + "[" + PREFIX_MAX_PROJECT_HOUR + "PROJECT HOUR] "
            + "[" + PREFIX_MIN_PREPARATION_HOUR + "PREPARATION HOUR] "
            + "[" + PREFIX_MAX_PREPARATION_HOUR + "PREPARATION HOUR]...\n"
            + "Example: " + COMMAND_WORD + " Y3S1 "
            + PREFIX_MIN_CAP + "3.27 "
            + PREFIX_MIN_LAB_HOUR + "2.5";

    public static final String MESSAGE_EDIT_LIMIT_SUCCESS = "Edited Semester: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditSemesterLimitDescriptor editSemesterLimitDescriptor;

    /**
     * @param index of the moduleTaken in the filtered moduleTaken list to edit
     * @param editLimitDescriptor details to edit the moduleTaken with
     */
    public SetSemesterLimitCommand(Index index, EditSemesterLimitDescriptor editLimitDescriptor) {
        requireNonNull(index);
        requireNonNull(editLimitDescriptor);

        this.index = index;
        this.editSemesterLimitDescriptor = new EditSemesterLimitDescriptor(editLimitDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<SemesterLimit> lastShownList = model.getSemesterLimitList();

        if (index.getZeroBased() >= NUMSEM) {
            throw new CommandException(Messages.MESSAGE_INVALID_SEMESTER_LIMIT);
        }

        SemesterLimit semesterLimitToEdit = lastShownList.get(index.getZeroBased());
        SemesterLimit editedSemesterLimit = createEditedLimit(semesterLimitToEdit, editSemesterLimitDescriptor);

        if (editedSemesterLimit.getMinCap().getCapLimit()
                > editedSemesterLimit.getMaxCap().getCapLimit()) {
            throw new CommandException(Messages.MESSAGE_CAP_LIMIT_OUT_OF_ORDER);
        }

        if (editedSemesterLimit.getMinLectureHour().getHour()
                > editedSemesterLimit.getMaxLectureHour().getHour()) {
            throw new CommandException(Messages.MESSAGE_LECTURE_HOUR_LIMIT_OUT_OF_ORDER);
        }

        if (editedSemesterLimit.getMinTutorialHour().getHour()
                > editedSemesterLimit.getMaxTutorialHour().getHour()) {
            throw new CommandException(Messages.MESSAGE_TUTORIAL_HOUR_LIMIT_OUT_OF_ORDER);
        }

        if (editedSemesterLimit.getMinLabHour().getHour()
                > editedSemesterLimit.getMaxLabHour().getHour()) {
            throw new CommandException(Messages.MESSAGE_LAB_HOUR_LIMIT_OUT_OF_ORDER);
        }

        if (editedSemesterLimit.getMinProjectHour().getHour()
                > editedSemesterLimit.getMaxProjectHour().getHour()) {
            throw new CommandException(Messages.MESSAGE_PROJECT_HOUR_LIMIT_OUT_OF_ORDER);
        }

        if (editedSemesterLimit.getMinPreparationHour().getHour()
                > editedSemesterLimit.getMaxPreparationHour().getHour()) {
            throw new CommandException(Messages.MESSAGE_PREPARATION_HOUR_LIMIT_OUT_OF_ORDER);
        }

        model.setSemesterLimit(index.getZeroBased(), editedSemesterLimit);
        model.commitGradTrak();
        return new CommandResult(String.format(MESSAGE_EDIT_LIMIT_SUCCESS, editedSemesterLimit));
    }

    /**
     * Creates and returns a {@code SemesterLimit} with the details of {@code semesterLimitToEdit}
     * edited with {@code EditSemesterLimitDescriptor}.
     */
    private static SemesterLimit createEditedLimit(SemesterLimit semesterLimitToEdit,
                                                   EditSemesterLimitDescriptor editSemesterLimitDescriptor) {
        assert semesterLimitToEdit != null;

        CapAverage updatedMinCap = editSemesterLimitDescriptor
                .getMinCap().orElse(semesterLimitToEdit.getMinCap());
        CapAverage updatedMaxCap = editSemesterLimitDescriptor
                .getMaxCap().orElse(semesterLimitToEdit.getMaxCap());
        Hour updatedMinLectureHour = editSemesterLimitDescriptor
                .getMinLectureHour().orElse(semesterLimitToEdit.getMinLectureHour());
        Hour updatedMaxLectureHour = editSemesterLimitDescriptor
                .getMaxLectureHour().orElse(semesterLimitToEdit.getMaxLectureHour());
        Hour updatedMinTutorialHour = editSemesterLimitDescriptor
                .getMinTutorialHour().orElse(semesterLimitToEdit.getMinTutorialHour());
        Hour updatedMaxTutorialHour = editSemesterLimitDescriptor
                .getMaxTutorialHour().orElse(semesterLimitToEdit.getMaxTutorialHour());
        Hour updatedMinLabHour = editSemesterLimitDescriptor
                .getMinLabHour().orElse(semesterLimitToEdit.getMinLabHour());
        Hour updatedMaxLabHour = editSemesterLimitDescriptor
                .getMaxLabHour().orElse(semesterLimitToEdit.getMaxLabHour());
        Hour updatedMinProjectHour = editSemesterLimitDescriptor
                .getMinProjectHour().orElse(semesterLimitToEdit.getMinProjectHour());
        Hour updatedMaxProjectHour = editSemesterLimitDescriptor
                .getMaxProjectHour().orElse(semesterLimitToEdit.getMaxProjectHour());
        Hour updatedMinPreparationHour = editSemesterLimitDescriptor
                .getMinPreparationHour().orElse(semesterLimitToEdit.getMinPreparationHour());
        Hour updatedMaxPreparationHour = editSemesterLimitDescriptor
                .getMaxPreparationHour().orElse(semesterLimitToEdit.getMaxPreparationHour());

        return new SemesterLimit(updatedMinCap, updatedMaxCap,
                updatedMinLectureHour, updatedMaxLectureHour, updatedMinTutorialHour, updatedMaxTutorialHour,
                updatedMinLabHour, updatedMaxLabHour, updatedMinProjectHour, updatedMaxProjectHour,
                updatedMinPreparationHour, updatedMaxPreparationHour);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetSemesterLimitCommand)) {
            return false;
        }

        // state check
        SetSemesterLimitCommand e = (SetSemesterLimitCommand) other;
        return index.equals(e.index)
                && editSemesterLimitDescriptor.equals(e.editSemesterLimitDescriptor);
    }

    /**
     * Stores the details to edit the limit with. Each non-empty field value will replace the
     * corresponding field value of the limit.
     */
    public static class EditSemesterLimitDescriptor {
        private CapAverage minCap;
        private CapAverage maxCap;
        private Hour minLectureHour;
        private Hour maxLectureHour;
        private Hour minTutorialHour;
        private Hour maxTutorialHour;
        private Hour minLabHour;
        private Hour maxLabHour;
        private Hour minProjectHour;
        private Hour maxProjectHour;
        private Hour minPreparationHour;
        private Hour maxPreparationHour;

        public EditSemesterLimitDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditSemesterLimitDescriptor(EditSemesterLimitDescriptor toCopy) {
            setMinCap(toCopy.minCap);
            setMaxCap(toCopy.maxCap);
            setMinLectureHour(toCopy.minLectureHour);
            setMaxLectureHour(toCopy.maxLectureHour);
            setMinTutorialHour(toCopy.minTutorialHour);
            setMaxTutorialHour(toCopy.maxTutorialHour);
            setMinLabHour(toCopy.minLabHour);
            setMaxLabHour(toCopy.maxLabHour);
            setMinProjectHour(toCopy.minProjectHour);
            setMaxProjectHour(toCopy.maxProjectHour);
            setMinPreparationHour(toCopy.minPreparationHour);
            setMaxPreparationHour(toCopy.maxPreparationHour);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(minCap, maxCap, minLectureHour, maxLectureHour,
                    minTutorialHour, maxTutorialHour, minLabHour, maxLabHour, minProjectHour, maxProjectHour,
                    minPreparationHour, maxPreparationHour);
        }

        public void setMinCap(CapAverage minCap) {
            this.minCap = minCap;
        }

        public Optional<CapAverage> getMinCap() {
            return Optional.ofNullable(minCap);
        }

        public void setMaxCap(CapAverage maxCap) {
            this.maxCap = maxCap;
        }

        public Optional<CapAverage> getMaxCap() {
            return Optional.ofNullable(maxCap);
        }

        public void setMinLectureHour(Hour minLectureHour) {
            this.minLectureHour = minLectureHour;
        }

        public Optional<Hour> getMinLectureHour() {
            return Optional.ofNullable(minLectureHour);
        }

        public void setMaxLectureHour(Hour maxLectureHour) {
            this.maxLectureHour = maxLectureHour;
        }

        public Optional<Hour> getMaxLectureHour() {
            return Optional.ofNullable(maxLectureHour);
        }

        public void setMinTutorialHour(Hour minTutorialHour) {
            this.minTutorialHour = minTutorialHour;
        }

        public Optional<Hour> getMinTutorialHour() {
            return Optional.ofNullable(minTutorialHour);
        }

        public void setMaxTutorialHour(Hour maxTutorialHour) {
            this.maxTutorialHour = maxTutorialHour;
        }

        public Optional<Hour> getMaxTutorialHour() {
            return Optional.ofNullable(maxTutorialHour);
        }

        public void setMinLabHour(Hour minLabHour) {
            this.minLabHour = minLabHour;
        }

        public Optional<Hour> getMinLabHour() {
            return Optional.ofNullable(minLabHour);
        }

        public void setMaxLabHour(Hour maxLabHour) {
            this.maxLabHour = maxLabHour;
        }

        public Optional<Hour> getMaxLabHour() {
            return Optional.ofNullable(maxLabHour);
        }

        public void setMinProjectHour(Hour minProjectHour) {
            this.minProjectHour = minProjectHour;
        }

        public Optional<Hour> getMinProjectHour() {
            return Optional.ofNullable(minProjectHour);
        }

        public void setMaxProjectHour(Hour maxProjectHour) {
            this.maxProjectHour = maxProjectHour;
        }

        public Optional<Hour> getMaxProjectHour() {
            return Optional.ofNullable(maxProjectHour);
        }

        public void setMinPreparationHour(Hour minPreparationHour) {
            this.minPreparationHour = minPreparationHour;
        }

        public Optional<Hour> getMinPreparationHour() {
            return Optional.ofNullable(minPreparationHour);
        }

        public void setMaxPreparationHour(Hour maxPreparationHour) {
            this.maxPreparationHour = maxPreparationHour;
        }

        public Optional<Hour> getMaxPreparationHour() {
            return Optional.ofNullable(maxPreparationHour);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditSemesterLimitDescriptor)) {
                return false;
            }

            // state check
            EditSemesterLimitDescriptor e = (EditSemesterLimitDescriptor) other;

            return getMinCap().equals(e.getMinCap())
                    && getMaxCap().equals(e.getMaxCap())
                    && getMinLectureHour().equals(e.getMinLectureHour())
                    && getMaxLectureHour().equals(e.getMaxLectureHour())
                    && getMinTutorialHour().equals(e.getMinTutorialHour())
                    && getMaxTutorialHour().equals(e.getMaxTutorialHour())
                    && getMinLabHour().equals(e.getMinLabHour())
                    && getMaxLabHour().equals(e.getMaxLabHour())
                    && getMinProjectHour().equals(e.getMinProjectHour())
                    && getMaxProjectHour().equals(e.getMaxProjectHour())
                    && getMinPreparationHour().equals(e.getMinPreparationHour())
                    && getMaxPreparationHour().equals(e.getMaxPreparationHour());
        }
    }
}
