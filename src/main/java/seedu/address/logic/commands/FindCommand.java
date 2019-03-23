package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.FindModulePredicate;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Semester;

/**
 * Finds module(s) in the module plan matching all given module code,
 * semester, grade or finished status (case-insensitive).
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds module(s) matching all given parameters.\n"
            + "Parameters: [c/MODULE_CODE] [s/SEMESTER] [g/GRADE] [f/IS_FINISHED]\n"
            + "Example: " + COMMAND_WORD + " s/y1s1 c/cs g/A";

    private final FindModulePredicate predicate;

    public FindCommand(FindModulePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }

    /**
     * Stores details for finding a module in the module plan.
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
