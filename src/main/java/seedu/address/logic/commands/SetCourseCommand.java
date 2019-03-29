package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.course.CourseName;

/**
 * Adds a moduleTaken to the GradTrak.
 */
public class SetCourseCommand extends Command {

    public static final String COMMAND_WORD = "study";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Sets the user's course. \n"
            + "Parameters: \n"
            + "Computer Science Algorithms - sets course to Computer Science algorithm\n"
            + "Computer Science AI - sets course to Computer Science AI\n"
            + "Computer Science Software Eng - sets course to Computer Science Software Engineering\n";

    public static final String MESSAGE_SUCCESS = "Course is set to %s";
    public static final String MESSAGE_CANNOT_FIND_COURSE = "Course %s is not found";
    private final CourseName courseName;

    /**
     * Sets course to specified {@code Course}
     */
    public SetCourseCommand(CourseName course) {
        requireNonNull(course);
        this.courseName = course;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.hasCourse(courseName)) {
            throw new CommandException(String.format(MESSAGE_CANNOT_FIND_COURSE, courseName));
        }
        model.setCourse(courseName);
        return new CommandResult(String.format(MESSAGE_SUCCESS, courseName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetCourseCommand // instanceof handles nulls
                && courseName.equals(((SetCourseCommand) other).courseName));
    }
}
