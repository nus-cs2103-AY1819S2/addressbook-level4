package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;

/**
 * Finds and lists module(s) in module plan matching all given module code, module title,
 * semester taken or grade obtained (case-insensitive).
 */
public class FindCommandNew extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds and lists module(s) based on "
            + "module code, module title, semester taken or grade obtained (case-insensitive).\n"
            + "Code and title can be entered partially, but semester and grade must be exact.\n"
            + "Parameters: [c/MODULE_CODE] [t/MODULE_TITLE] [s/SEMESTER] [g/GRADE_OBTAINED]\n"
            + "Example: " + COMMAND_WORD + " s/y1s1 n/cs g/A";

    private final Predicate<Module> predicate;

    public FindCommandNew(Predicate<Module> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredModuleList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommandNew) other).predicate)); // state check
    }

    /**
     * Stores the details for finding a module. Each field can be empty.
     */
    public static class FindModuleDescriptor {
        private String code; // can be substring of exact code
        private String title; // can be substring of exact title
        private Semester semester;
        private Grade grade;

        public FindModuleDescriptor() {}

        /**
         * Copy constructor.
         */
        public FindModuleDescriptor(FindModuleDescriptor toCopy) {
            setCode(toCopy.code);
            setTitle(toCopy.title);
            setSemester(toCopy.semester);
            setGrade(toCopy.grade);
        }

        public boolean isAnyFieldEntered() {
            return CollectionUtil.isAnyNonNull(code, title, semester, grade);
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Optional<String> getCode() {
            return Optional.ofNullable(code);
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Optional<String> getTitle() {
            return Optional.ofNullable(title);
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

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof FindModuleDescriptor)) {
                return false;
            }

            FindModuleDescriptor other = (FindModuleDescriptor) object;
            return (code.equals(other.code) && title.equals(other.title)
                        && semester.equals(other.semester) && grade.equals(other.grade));
        }
    }
}
