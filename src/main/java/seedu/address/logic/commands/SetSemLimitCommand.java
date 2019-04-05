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
import seedu.address.model.SemLimit;
import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.Hour;

/**
 * Edits the details of an semester grade and workload limits in the address book.
 */
public class SetSemLimitCommand extends Command {

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
            + "[" + PREFIX_MAX_PREPARATION_HOUR + "PREPARATION HOUR]...\n "
            + "Example: " + COMMAND_WORD + " Y3S1 "
            + PREFIX_MIN_CAP + "3.27 "
            + PREFIX_MIN_LAB_HOUR + "2.5";

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
        model.commitGradTrak();
        return new CommandResult(String.format(MESSAGE_EDIT_LIMIT_SUCCESS, editedSemLimit));
    }

    /**
     * Creates and returns a {@code SemLimit} with the details of {@code semLimitToEdit}
     * edited with {@code EditSemLimitDescriptor}.
     */
    private static SemLimit createEditedLimit(SemLimit semLimitToEdit,
                                                  EditSemLimitDescriptor editSemLimitDescriptor) {
        assert semLimitToEdit != null;

        CapAverage updatedMinCap = editSemLimitDescriptor
                .getMinCap().orElse(semLimitToEdit.getMinCap());
        CapAverage updatedMaxCap = editSemLimitDescriptor
                .getMaxCap().orElse(semLimitToEdit.getMaxCap());
        Hour updatedMinLectureHour = editSemLimitDescriptor
                .getMinLectureHour().orElse(semLimitToEdit.getMinLectureHour());
        Hour updatedMaxLectureHour = editSemLimitDescriptor
                .getMaxLectureHour().orElse(semLimitToEdit.getMaxLectureHour());
        Hour updatedMinTutorialHour = editSemLimitDescriptor
                .getMinTutorialHour().orElse(semLimitToEdit.getMinTutorialHour());
        Hour updatedMaxTutorialHour = editSemLimitDescriptor
                .getMaxTutorialHour().orElse(semLimitToEdit.getMaxTutorialHour());
        Hour updatedMinLabHour = editSemLimitDescriptor
                .getMinLabHour().orElse(semLimitToEdit.getMinLabHour());
        Hour updatedMaxLabHour = editSemLimitDescriptor
                .getMaxLabHour().orElse(semLimitToEdit.getMaxLabHour());
        Hour updatedMinProjectHour = editSemLimitDescriptor
                .getMinProjectHour().orElse(semLimitToEdit.getMinProjectHour());
        Hour updatedMaxProjectHour = editSemLimitDescriptor
                .getMaxProjectHour().orElse(semLimitToEdit.getMaxProjectHour());
        Hour updatedMinPreparationHour = editSemLimitDescriptor
                .getMinPreparationHour().orElse(semLimitToEdit.getMinPreparationHour());
        Hour updatedMaxPreparationHour = editSemLimitDescriptor
                .getMaxPreparationHour().orElse(semLimitToEdit.getMaxPreparationHour());

        return new SemLimit(updatedMinCap, updatedMaxCap,
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

        public EditSemLimitDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditSemLimitDescriptor(EditSemLimitDescriptor toCopy) {
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
            return CollectionUtil.isAnyNonNull(minCap, maxCap, minLectureHour, maxLectureHour);
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
            if (!(other instanceof EditSemLimitDescriptor)) {
                return false;
            }

            // state check
            EditSemLimitDescriptor e = (EditSemLimitDescriptor) other;

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
