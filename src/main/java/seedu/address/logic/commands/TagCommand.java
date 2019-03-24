package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the file that you wish to tag.\n"
            + "Parameters: INDEX t/[TAG]\n"
            + "Example: " + COMMAND_WORD + " 2" + "t/SE" + "t/Rocks";

    public static final String MESSAGE_TAG_SUCCESS = "Tag success!";

    private final Index targetIndex;

    public TagCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
