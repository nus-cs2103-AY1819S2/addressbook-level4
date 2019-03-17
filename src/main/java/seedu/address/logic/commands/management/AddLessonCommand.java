package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.exceptions.CommandException.MESSAGE_EXPECTED_MGT_MODEL;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.management.ManagementModel;

/**
 * Adds a new lesson.
 */
public class AddLessonCommand implements Command {
    public static final String COMMAND_WORD = "addLesson";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Lesson toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddLessonCommand(Lesson lesson) {
        requireNonNull(lesson);
        toAdd = lesson;
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
