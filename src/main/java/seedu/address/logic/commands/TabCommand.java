/* @@author Carrein */
package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * Switches between tabs in the informationPanel.
 */
public class TabCommand extends Command {
    public static final String COMMAND_WORD = "tab";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches between tabs in the information panel.";

    public static final String MESSAGE_TAB_SUCCESS = "Tab switched.";

    private final Album album = Album.getInstance();

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history) {
        album.switchTab();
        return new CommandResult(MESSAGE_TAB_SUCCESS);
    }
}
