package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * List all images inside assets folder.
 */

public class ListImgCommand extends Command {
    public static final String COMMAND_WORD = "listimg";

    public static final String MESSAGE_SUCCESS = "Listed all images in assets folder.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.listAllImages();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
