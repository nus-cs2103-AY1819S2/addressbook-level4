package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.logic.AnswerCommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    public enum TYPE {
        SHOW_HELP, /** Help information should be shown to the user. */
        IS_EXIT, /** The application should exit. */
        ENTERED_FOLDER, /** The side panel should be updated as folder was entered. */
        EXITED_FOLDER, /** The side panel should be updated as folder was exited. */
        TEST_SESSION_CARD, /** The application should enter a test session. */
        END_TEST_SESSION,  /** The current test session should end. */
        ANSWER_CORRECT,
        ANSWER_WRONG,
        NONE // use for "nothing to do"
    }

    private Card testSessionCard;

    public TYPE type;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, TYPE type) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.type = type;
    }

    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, TYPE.NONE);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public void setTestSessionCard(Card card) {
        testSessionCard = card;
    }

    public Card getTestSessionCard() {
        return testSessionCard;
    }

    public TYPE getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && type == otherCommandResult.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, type);
    }

}
