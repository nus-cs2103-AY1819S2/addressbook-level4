package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.AnswerCommandResultType;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.card.Answer;

/**
 * Finds and lists all cards in card folder whose question contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class AnswerCommand extends Command {

    public static final String COMMAND_WORD = "ans";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": User inputs an answer for the"
            + " currently displayed card. This command is valid only in an active test session"
            + " and a card is currently being displayed.\n"
            + "Parameters: ANSWER \n"
            + "Example: " + COMMAND_WORD + " Mitochondrion";

    public static final String MESSAGE_ANSWER_SUCCESS = "Answer sent successfully";

    private final Answer attemptedAnswer;

    public AnswerCommand(Answer attemptedAnswer) {
        this.attemptedAnswer = attemptedAnswer;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        boolean isAttemptCorrect = model.markAttemptedAnswer(attemptedAnswer);

        if (isAttemptCorrect) {
            return new CommandResult(MESSAGE_ANSWER_SUCCESS, false, false, null, false, AnswerCommandResultType.ANSWER_CORRECT);
        } else {
            return new CommandResult(MESSAGE_ANSWER_SUCCESS, false, false, null, false, AnswerCommandResultType.ANSWER_WRONG);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnswerCommand // instanceof handles nulls
                && attemptedAnswer.equals(((AnswerCommand) other).attemptedAnswer)); // state check
    }
}
