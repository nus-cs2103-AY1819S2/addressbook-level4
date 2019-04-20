package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_LESSON_VIEW_COMMAND;
import static seedu.address.logic.parser.Syntax.PREFIX_HINT;
import static seedu.address.logic.parser.Syntax.PREFIX_LESSON_NAME;
import static seedu.address.logic.parser.Syntax.PREFIX_TEST;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link ManagementCommand} which adds a {@link Lesson} to the
 * {@code List<Lesson> lessons} loaded in memory.
 *
 * It requires a {@link ManagementModel} to be passed into the {@link #execute(Model, CommandHistory)}
 * command.
 */
public class AddLessonCommand extends ManagementCommand {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "addLesson";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson. "
            + "\nParameters: "
            + PREFIX_LESSON_NAME + "NAME "
            + PREFIX_TEST + "TEST "
            + PREFIX_TEST + "TEST "
            + "[" + PREFIX_TEST + "TEST]... "
            + "[" + PREFIX_HINT + "HINT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LESSON_NAME + "Capitals of the world "
            + PREFIX_TEST + "Country "
            + PREFIX_TEST + "Capital "
            + PREFIX_TEST + "Language "
            + PREFIX_HINT + "Hint\n"
            + "Note: You need to specify at least 2 TEST values.\n"
            + "By default, the first 2 TEST values (Country and Capital) will be tested.\n"
            + "Use the `" + SetLessonTestValuesCommand.COMMAND_WORD
            + "` command to set the TEST values to be tested.";
    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS = "Added lesson: %1$s";
    /**
     * Feedback message displayed to the user if there is already a lesson with the same name.
     */
    public static final String MESSAGE_DUPLICATE_NAME = "A lesson with the name: %1$s already exists.";
    /**
     * The lesson to be added when {@link #execute(Model, CommandHistory)} is called.
     */
    private final Lesson toAdd;
    /**
     * Constructs a {@link ManagementCommand} to add the specified {@link Lesson}.
     *
     * @param toAdd the {@link Lesson} to be added
     */
    public AddLessonCommand(Lesson toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    /**
     * Executes the command which adds a {@link Lesson} to the {@code List<Lesson> lessons}
     * loaded in memory.
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
        String lessonName = toAdd.getName();

        if (mgtModel.isThereOpenedLesson()) {
            throw new CommandException(MESSAGE_LESSON_VIEW_COMMAND);
        } else if (mgtModel.hasLessonWithName(lessonName)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_NAME, lessonName));
        }

        mgtModel.addLesson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), CommandResult.UpdateStorage.SAVE);
    }

    /**
     * Returns true if {@code other} is the same object or if it is also an {@link AddLessonCommand}
     * attempting to add the same lesson.
     *
     * @param other the other object to compare this object to
     * @return true if {@code other} is the same object or if it is also an {@link AddLessonCommand}
     * attempting to add the same lesson.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLessonCommand // instanceof handles nulls
                && toAdd.equals(((AddLessonCommand) other).toAdd));
    }
}
