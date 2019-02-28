package seedu.address.logic.commands;

import java.util.Objects;

/**
 * Represents a command result that pre-fills the command box and repositions the cursor.
 */
public class PrefillCommandBoxCommandResult extends CommandResult {

    private final String prefilledText;

    public PrefillCommandBoxCommandResult(String feedbackToUser, String prefilledText) {
        super(feedbackToUser);
        this.prefilledText = prefilledText;
    }

    public String getPrefilledText() {
        return prefilledText;
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        PrefillCommandBoxCommandResult otherCommandResult = (PrefillCommandBoxCommandResult) other;
        return prefilledText.equals(otherCommandResult.prefilledText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), prefilledText);
    }
}
