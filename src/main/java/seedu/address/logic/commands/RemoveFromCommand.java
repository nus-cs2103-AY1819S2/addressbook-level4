package seedu.address.logic.commands;

/**
 * Represents a RemoveFrom command with hidden internal logic and the ability to be executed.
 */
public abstract class RemoveFromCommand extends Command {
    public static final String COMMAND_WORD = "removefrom";


    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME employee/milestone/projecttask/userstory"
            + ": removes the employee/milestone/projecttask/userstory identified by the index number used in"
            + " the displayed employee/milestone/userstory list.\n"
            + RemoveEmployeeFromCommand.MESSAGE_USAGE + "\n"
            + RemoveMilestoneFromCommand.MESSAGE_USAGE + "\n"
            + RemoveProjectTaskFromCommand.MESSAGE_USAGE + "\n"
            + RemoveUserStoryFromCommand.MESSAGE_USAGE;
}
