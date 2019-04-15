package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;
import static quickdocs.commons.util.StringUtil.fromPathToString;

import java.util.Arrays;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;

/**
 * A command to add new directory
 */
public class AddDirectoryCommand extends Command {

    public static final String COMMAND_WORD = "adddirec";
    public static final String COMMAND_ALIAS = "ad";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new directory to the directory specified. "
            + "Parameters: "
            + "[path of directory separated by \\] "
            + "[name of new directory]\n"
            + "Example: " + COMMAND_WORD + " "
            + "root\\TCM Herbs";

    public static final String MESSAGE_SUCCESS = "New directory with name \"%1$s\" added to %2$s\n";

    private final String[] path;
    private final String name;

    public AddDirectoryCommand(String[] path, String directoryName) {
        this.path = path;
        this.name = directoryName;
    }

    /**
     * Add a new directory with the given name to a given directory
     * @param model {@code Model} which the command should operate on.
     * @param commandHistory {@code CommandHistory} which the command should operate on.
     * @return A commandresult showing the result
     * @throws CommandException
     */
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        try {
            model.addDirectory(name, path);
            return new CommandResult(String.format(MESSAGE_SUCCESS, name, fromPathToString(path)));
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
    }

    /**
     * Checks whether other equals this
     * @param other the object to check against
     * @return if they are equal
     */
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddDirectoryCommand
                && Arrays.equals(this.path, ((AddDirectoryCommand) other).path))
                && this.name.equals(((AddDirectoryCommand) other).name);
    }
}
