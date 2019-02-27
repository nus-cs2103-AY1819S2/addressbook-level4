package seedu.address.logic.commands;

/**
 * Represents a command result that pre-fills the command box and repositions the cursor.
 */
public class PrefillCommandBoxCommandResult extends CommandResult {

    private final String prefilledText;
    private final int cursorPos;

    public PrefillCommandBoxCommandResult(String prefilledText, int cursorPos) {
        super("");
        this.prefilledText = prefilledText;
        this.cursorPos = cursorPos;
    }

    public String getPrefilledText() {
        return prefilledText;
    }

    public int getCursorPos() {
        return cursorPos;
    }
}
