package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.exceptions.CommandException.MESSAGE_EXPECTED_MGT_MODEL;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.management.ManagementModel;

/**
 * Lists all lessons.
 */
public class ListLessonsCommand implements Command {
    public static final String COMMAND_WORD = "listLessons";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all lessons in memory.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all lessons";
    public static final String MESSAGE_DELIMITER = ":\n";
    public static final String MESSAGE_NO_LESSONS = "There are no lessons yet.";

    /**
     * Builds a String representation of {@code List<Lesson> lessons} and returns it.
     * @param lessons the list of lessons
     * @return a String representing {@code lessons}
     */
    public String buildList(List<Lesson> lessons) {
        StringBuilder builder = new StringBuilder();
        builder.append(MESSAGE_SUCCESS).append(MESSAGE_DELIMITER);

        if (lessons.isEmpty()) {
            builder.append(MESSAGE_NO_LESSONS);
        } else {
            for (Lesson lesson : lessons) {
                builder.append(lesson.toStringSingleLine() + "\n");
            }
        }

        return builder.toString();
    }

    /**
     * Executes the command and returns the result message.
     * @param model which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // CommandException will be thrown if and only if LogicManager passes in the incorrect Model
        // In other words, only incorrect code will result in a CommandException being thrown
        if (!(model instanceof ManagementModel)) {
            throw new CommandException(MESSAGE_EXPECTED_MGT_MODEL);
        }

        ManagementModel mgtModel = (ManagementModel) model;

        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons.addAll(mgtModel.getLessons());

        return new CommandResult(buildList(lessons));
    }
}
