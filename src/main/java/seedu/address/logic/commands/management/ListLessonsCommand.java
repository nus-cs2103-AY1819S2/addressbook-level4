package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.exceptions.CommandException.MESSAGE_EXPECTED_MGT_MODEL;

import java.util.ArrayList;

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

        StringBuilder builder = new StringBuilder();
        builder.append(MESSAGE_SUCCESS).append(":\n");

        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons.addAll(mgtModel.getLessons());

        if (lessons.isEmpty()) {
            builder.append("There are no lessons yet.");
        } else {
            for (Lesson lesson : lessons) {
                builder.append(lesson.toStringSingleLine() + "\n");
            }
        }

        return new CommandResult(builder.toString());
    }
}
