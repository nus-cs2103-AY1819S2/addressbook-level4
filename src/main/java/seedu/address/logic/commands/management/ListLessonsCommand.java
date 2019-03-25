package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link ManagementCommand} which executes a command to list all {@link Lesson} objects
 * in the {@code List<Lesson> lessons} loaded in memory. It requires a {@link ManagementModel}
 * to be passed into the {@link #execute(Model, CommandHistory)} command. The actual listing
 * of the {@link Lesson} objects is carried out in the {@link ManagementModel}.
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
     * Executes the command and returns the result message.
     *
     * @param lessons the list of lessons
     * @return a String representing {@code lessons}
     */
    public String buildList(List<Lesson> lessons) {
        if (lessons.isEmpty()) {
            return Messages.MESSAGE_NO_LESSONS;
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
     * Executes the command and returns the result message.
     * @param model which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ManagementModel mgtModel = requireManagementModel(model);

        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons.addAll(mgtModel.getLessonList());

        return new CommandResult(buildList(lessons));
    }
}
