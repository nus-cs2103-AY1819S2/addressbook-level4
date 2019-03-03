package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the description of an existing expense entry in finance log.
 */
public class DescriptionCommand extends Command {

    public static final String COMMAND_WORD = "description";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the description of expense entry identified "
            + "by the index number used in the last expense entry listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "[DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_DESCRIPTION + "Father's birthday present.";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Description: %2$s";

    private final Index index;
    private final String description;

    /**
     * @param index Index of the expense entry in the filtered expense list to edit description
     * @param description description of the expense entry to be updated to
     */
    public DescriptionCommand(Index index, String description) {
        requireAllNonNull(index, description);

        this.index = index;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), description));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DescriptionCommand)) {
            return false;
        }

        DescriptionCommand e = (DescriptionCommand) other;
        return index.equals(e.index) && description.equals(e.description);
    }
}
