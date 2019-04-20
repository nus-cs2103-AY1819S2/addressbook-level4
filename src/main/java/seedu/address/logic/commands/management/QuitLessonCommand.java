package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_LESSON_COMMANDS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link ManagementCommand} which saves changes made to the {@link Lesson}
 * opened in Card View, before returning to Lesson View.
 *
 * It requires a {@link ManagementModel} to be passed into the {@link #execute(Model, CommandHistory)}
 * command.
 */
public class QuitLessonCommand extends ManagementCommand {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "quit";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Quits Card View and returns to Lesson View.\n"
            + "Example: " + COMMAND_WORD
            + "\nNote: Changes made in Card View will be saved.";
    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS = "Changes saved for lesson: %1$s\n"
            + MESSAGE_LESSON_COMMANDS;

    /**
     * Executes the command which closes the opened {@link Lesson} in the {@code List<Lesson> lessons}
     * loaded in memory.
     *
     * @param model the {@link ManagementModel} the command should operate on.
     * @param history {@link CommandHistory} which the command should operate on.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException if there is no opened lesson and hence no need to close lesson or
     * if the {@code model} passed in is not a {@link ManagementModel}
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ManagementModel mgtModel = requireManagementModel(model);

        try {
            String lessonName = mgtModel.closeLesson();
            return new CommandResult(String.format(MESSAGE_SUCCESS, lessonName), CommandResult.UpdateStorage.SAVE);
        } catch (NullPointerException e) {
            throw new CommandException(Messages.MESSAGE_CARD_VIEW_COMMAND, e);
        }
    }

    /**
     * Returns true if {@code other} is the same object or if it is also an {@link QuitLessonCommand}.
     * All {@link QuitLessonCommand} objects are the same.
     *
     * @param other the other object to compare this object to
     * @return true if {@code other} is the same object or if it is also an {@link QuitLessonCommand}.
     * All {@link QuitLessonCommand} objects are the same.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuitLessonCommand);
    }
}
