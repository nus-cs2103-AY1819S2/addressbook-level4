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
 * This implements a {@link Command} which executes a command to list all {@link Lesson} objects
 * in the {@code List<Lesson> lessons} loaded in memory. It requires a {@link ManagementModel}
 * to be passed into the {@link #execute(Model, CommandHistory)} command. The actual listing
 * of the {@link Lesson} objects is carried out in the {@link ManagementModel}.
 */
public class ListLessonsCommand implements Command {
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
    public static final String MESSAGE_SUCCESS = "Listed all lessons";
    /**
     * Used to separate {@link #MESSAGE_SUCCESS} and either {@link #MESSAGE_NO_LESSONS}
     * or list of lessons when forming the result message in {@link #buildList(List)}.
     */
    public static final String MESSAGE_DELIMITER = ":\n";
    /**
     * Shown when {@link #buildList(List)} is called and there are no lessons yet.
     */
    public static final String MESSAGE_NO_LESSONS = "There are no lessons yet.";

    /**
     * Executes the command and returns the result message.
     *
     * @param lessons the list of lessons
     * @return a String representing {@code lessons}
     */
    public String buildList(List<Lesson> lessons) {
        StringBuilder builder = new StringBuilder();
        builder.append(MESSAGE_SUCCESS).append(MESSAGE_DELIMITER);

        if (lessons.isEmpty()) {
            builder.append(MESSAGE_NO_LESSONS);
        } else {
            int i = 1;
            for (Lesson lesson : lessons) {
                builder.append(i).append(".\t").append(lesson).append("\n");
                i++;
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
