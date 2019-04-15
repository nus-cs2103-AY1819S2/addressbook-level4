package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.moduletaken.FindModulePredicate;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.Semester;

/**
 * Finds {@code ModuleTaken} in {@code GradTrak} matching all given {@code ModuleInfoCode},
 * {@code Semester}, {@code Grade} or finished status (case-insensitive).
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds modules matching all given parameters.\n"
            + "Parameters: [c/MODULE_CODE] [s/SEMESTER] [g/GRADE] [f/IS_FINISHED]\n"
            + "Example: " + COMMAND_WORD + " s/y1s1 c/cs g/A f/y";

    private final FindModuleDescriptor descriptor;

    public FindCommand(FindModuleDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        FindModulePredicate predicate = new FindModulePredicate(descriptor, model.getCurrentSemester());
        model.updateFilteredModulesTakenList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_MODULETAKEN_LISTED_OVERVIEW,
                        model.getFilteredModulesTakenList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && descriptor.equals(((FindCommand) other).descriptor)); // state check
    }

    /**
     * Stores details for finding a {@code ModuleTaken} in {@code GradTrak}.
     * There must be at least one filled field.
     */
    public static class FindModuleDescriptor {
        private String subCode; // can be substring of exact code
        private Semester semester;
        private Grade grade;
        private Boolean isFinished;

        public FindModuleDescriptor() {}

        /**
         * Copy constructor.
         */
        public FindModuleDescriptor(FindModuleDescriptor toCopy) {
            setSubCode(toCopy.subCode);
            setSemester(toCopy.semester);
            setGrade(toCopy.grade);
            setFinished(toCopy.isFinished);
        }

        public void setSubCode(String subCode) {
            this.subCode = subCode.toLowerCase();
        }

        public Optional<String> getSubCode() {
            return Optional.ofNullable(subCode);
        }

        public void setSemester(Semester semester) {
            this.semester = semester;
        }

        public Optional<Semester> getSemester() {
            return Optional.ofNullable(semester);
        }

        public void setGrade(Grade grade) {
            this.grade = grade;
        }

        public Optional<Grade> getGrade() {
            return Optional.ofNullable(grade);
        }

        public Optional<Boolean> isFinished() {
            return Optional.ofNullable(isFinished);
        }

        public void setFinished(boolean isFinished) {
            this.isFinished = isFinished;
        }

        @Override
        public String toString() {
            String feedback = "";

            if (subCode != null) {
                feedback += "Code: " + subCode + " | ";
            }
            if (semester != null) {
                feedback += "Semester: " + semester + " | ";
            }
            if (grade != null) {
                feedback += "Grade: " + grade + " | ";
            }
            if (isFinished != null) {
                feedback += "Finished: " + isFinished + " | ";
            }

            return feedback;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof FindModuleDescriptor)) {
                return false;
            }

            FindModuleDescriptor other = (FindModuleDescriptor) object;
            return getSubCode().equals(other.getSubCode())
                    && getSemester().equals(other.getSemester())
                    && getGrade().equals(other.getGrade())
                    && isFinished().equals(other.isFinished());
        }
    }
}
