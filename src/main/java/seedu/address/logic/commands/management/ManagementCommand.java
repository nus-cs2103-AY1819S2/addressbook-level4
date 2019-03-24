package seedu.address.logic.commands.management;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.management.ManagementModel;

/**
 * Represents a management command with hidden internal logic and the ability to be executed.
 */
public abstract class ManagementCommand implements Command {
    public static final String MESSAGE_EXPECTED_MGT_MODEL =
            "Expected ManagementModel but received QuizModel instead.";

    /**
     * Checks that the specified object reference is {@link ManagementModel}. This
     * method is designed primarily for doing parameter validation for methods using
     * {@link ManagementModel}.
     *
     * @param obj the object reference to check if {@link ManagementModel}.
     * @return {@code obj} if {@link ManagementModel}
     * @throws CommandException if {@code obj} is not {@link ManagementModel}.
     */
    static ManagementModel requireManagementModel(Object obj) throws CommandException {
        // CommandException will be thrown if and only if LogicManager passes in the incorrect Model
        // In other words, only incorrect code will result in a CommandException being thrown
        if (!(obj instanceof ManagementModel)) {
            throw new CommandException(MESSAGE_EXPECTED_MGT_MODEL);
        }

        return (ManagementModel) obj;
    }
}
