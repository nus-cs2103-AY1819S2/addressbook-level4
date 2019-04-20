package seedu.address.logic.commands.quiz;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.QuizModel;

/**
 * Represents a quiz command with hidden internal logic and the ability to be executed.
 */
public abstract class QuizCommand implements Command {
    public static final String MESSAGE_EXPECTED_MODEL =
            "Expected QuizModel but received ManagementModel instead.";

    /**
     * Checks that the specified object reference is {@link QuizModel}. This
     * method is designed primarily for doing parameter validation for methods using
     * {@link QuizModel}.
     *
     * @param obj the object reference to check if {@link QuizModel}.
     * @return {@code obj} if {@link QuizModel}
     * @throws CommandException if {@code obj} is not {@link QuizModel}.
     */
    protected static QuizModel requireQuizModel(Object obj) throws CommandException {
        // CommandException will be thrown if and only if LogicManager passes in the incorrect Model
        // In other words, only incorrect code will result in a CommandException being thrown
        if (!(obj instanceof QuizModel)) {
            throw new CommandException(MESSAGE_EXPECTED_MODEL);
        }

        return (QuizModel) obj;
    }
}
