package seedu.address.logic.commands;

/**
 * Represent a find command with hidden internal logic and the ability to be executed.
 */

public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = "To find employees, use: " + COMMAND_WORD
        + " employee/skill [ARGUMENTS]\n"
        + "To find projects, use: " + COMMAND_WORD + " project/all/deadline [ARGUMENTS]";
}
