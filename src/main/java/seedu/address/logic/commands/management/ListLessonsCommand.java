package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link ManagementCommand} which lists all {@link Lesson} objects
 * in the {@code List<Lesson> lessons} loaded in memory.
 *
 * It requires a {@link ManagementModel} to be passed into the {@link #execute(Model, CommandHistory)}
 * command. The addition of the {@link Lesson} is carried out in the {@link ManagementModel}.
 */
public class ListLessonsCommand extends ManagementCommand {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "listLessons";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all lessons in memory.\n"
            + "Example: " + COMMAND_WORD;
    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS = "Listed %1$s lesson(s):\n";
    /**
     * Feedback message displayed to the user if there are no lessons found and hence no listing.
     */
    public static final String MESSAGE_NO_LESSONS = "No lesson found.";

    /**
     * Constructs a list of {@link Lesson} objects for display purposes.
     *
     * @param lessons the list of lessons
     * @return a String representing {@code lessons}
     */
    public String buildList(List<Lesson> lessons) {
        if (lessons.isEmpty()) {
            return MESSAGE_NO_LESSONS;
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format(MESSAGE_SUCCESS, lessons.size()));

            int i = 1;
            for (Lesson lesson : lessons) {
                builder.append(i).append(".\t").append(lesson.getName()).append("\n");
                i++;
            }

            return builder.toString();
        }
    }

    /**
     * Executes the command which lists all {@link Lesson} objects in the
     * {@code List<Lesson> lessons} loaded in memory.
     *
     * @param model the {@link ManagementModel} the command should operate on.
     * @param history {@link CommandHistory} which the command should operate on.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException if the {@code model} passed in is not a {@link ManagementModel}
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ManagementModel mgtModel = requireManagementModel(model);

        ArrayList<Lesson> lessons = new ArrayList<>(mgtModel.getLessons());

        return new CommandResult(buildList(lessons));
    }

    /**
     * Returns true if {@code other} is the same object or if it is also an {@link ListLessonsCommand}.
     * All {@link ListLessonsCommand} objects are the same.
     *
     * @param other the other object to compare this object to
     * @return true if {@code other} is the same object or if it is also an {@link ListLessonsCommand}.
     * All {@link ListLessonsCommand} objects are the same.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListLessonsCommand);
    }
}
