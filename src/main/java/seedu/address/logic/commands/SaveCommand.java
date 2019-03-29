package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * Saves edited image into assets folder
 */

public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    public static final String MESSAGE_SUCCESS = "Image saved as: ";

    private final String toName;

    /**
     * Creates an SaveCommand to add the specified {@code name}
     */
    public SaveCommand(String name) {
        this.toName = name;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Album album, Model model, CommandHistory history) {
        requireNonNull(currentEdit);
        String name = currentEdit.saveToAssets(toName);
        return new CommandResult(String.format(MESSAGE_SUCCESS + name));
    }
}
