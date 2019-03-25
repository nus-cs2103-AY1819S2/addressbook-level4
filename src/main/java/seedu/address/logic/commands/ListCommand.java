package seedu.address.logic.commands;

import seedu.address.logic.commands.request.RequestCommand;

/**
 * Abstract class describing a command that involves listing objects.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_UAGE = "The different modes for the list command are as follows:\n"
            + COMMAND_WORD + " " + RequestCommand.COMMAND_OPTION + ": " + "Lists all existing requests.\n"
            + COMMAND_WORD + " " + HealthWorkerCommand.COMMAND_OPTION + ": " + "Lists all existing health workers";
}
