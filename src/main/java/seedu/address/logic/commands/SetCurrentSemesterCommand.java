package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ModuleTaken;
import seedu.address.model.person.Semester;

/**
 * Sets the current semester of the user.
 */
public class SetCurrentSemesterCommand extends Command {

    public static final String COMMAND_WORD = "cursem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the current semester of the "
            + "user while ensuring all the grades have been finalized in the previous semesters.\n"
            + "Parameters: SEMESTER \n "
            + "Example: " + COMMAND_WORD + " Y3S3 ";

    public static final String MESSAGE_EDIT_LIMIT_SUCCESS = "Edited Semester: %1$s";

    private final Semester semester;

    /**
     * @param semester to set as the current semester
     */
    public SetCurrentSemesterCommand(Semester semester) {
        requireNonNull(semester);

        this.semester = semester;
    }

    /**
     * @param semester to set as the current semester
     * @return indication if the current list of modules taken have all the grades finalized before the given semester
     */
    public boolean checkGrades(List<ModuleTaken> lastShownList, Semester semester) {
        for (int i = 0; i < lastShownList.size(); i++) {
            boolean isModuleTakenBeforeGivenSemester =
                    lastShownList.get(i).getSemester().getIndex() < semester.getIndex();
            boolean isModuleGradeFinalized =
                    lastShownList.get(i).getExpectedMinGrade().equals(lastShownList.get(i).getExpectedMaxGrade());
            if (isModuleTakenBeforeGivenSemester && !isModuleGradeFinalized) {
                return false;
            }
        }
        return true;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<ModuleTaken> lastShownList = model.getFilteredPersonList();
        boolean hasValidGradesTillSemester = checkGrades(lastShownList, semester);

        if (!hasValidGradesTillSemester) {
            throw new CommandException(Messages.MESSAGE_GRADES_NOT_FINALIZED_BEFORE_SEMESTER);
        }

        model.setCurrentSemester(semester);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_LIMIT_SUCCESS, semester));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetCurrentSemesterCommand)) {
            return false;
        }

        // state check
        SetCurrentSemesterCommand e = (SetCurrentSemesterCommand) other;
        return semester.equals(e.semester);
    }
}
