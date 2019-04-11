package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

/**
 * Selects a card identified using its displayed index.
 */
public abstract class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_SELECT_SUCCESS = "Selected: %1$s";

    protected final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }
}
