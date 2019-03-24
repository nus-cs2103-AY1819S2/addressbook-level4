package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.Syntax.PREFIX_LESSON_CORE_HEADER;
import static seedu.address.logic.parser.Syntax.PREFIX_LESSON_NAME;
import static seedu.address.logic.parser.Syntax.PREFIX_LESSON_OPT_HEADER;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.management.ManagementModel;

/**
 * This implements a {@link Command} which executes a command to add a {@link Lesson} to the
 * {@code List<Lesson> lessons} loaded in memory. It requires a {@link ManagementModel}
 * to be passed into the {@link #execute(Model, CommandHistory)} command. The actual addition
 * of the {@link Lesson} is carried out in the {@link ManagementModel}.
 */
public class AddLessonCommand implements Command {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "addLesson";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson. "
            + "Parameters: "
            + PREFIX_LESSON_NAME + "NAME "
            + PREFIX_LESSON_CORE_HEADER + "CORE..."
            + "[" + PREFIX_LESSON_OPT_HEADER + "OPTIONAL]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LESSON_NAME + "Capitals of the world "
            + PREFIX_LESSON_CORE_HEADER + "Country "
            + PREFIX_LESSON_CORE_HEADER + "Capital "
            + PREFIX_LESSON_OPT_HEADER + "Hint";
    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS = "Added lesson: %1$s";
    /**
     * The lesson to be added when {@link #execute(Model, CommandHistory)} is called.
     */
    private final Lesson toAdd;

    /**
     * Creates an AddLessonCommand to add the specified {@link Lesson}
     *
     * @param toAdd the {@link Lesson} to be added
     */
    public AddLessonCommand(Lesson toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
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

        mgtModel.addLesson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLessonCommand // instanceof handles nulls
                && toAdd.equals(((AddLessonCommand) other).toAdd));
    }
}
