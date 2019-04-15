package seedu.knowitall.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.knowitall.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.Model.State;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;

/**
 * Allows user to input an answer for the currently displayed card, compares it with the
 * correct answer in that card and tell the user if it is correct or wrong.
 * Answer matching is case insensitive.
 */
public class AnswerCommand extends Command {

    public static final String COMMAND_WORD = "ans";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": User inputs an answer for the"
            + " currently displayed card.\n"
            + "Parameters: ANSWER \n"
            + "Example: " + COMMAND_WORD + " Mitochondrion";

    public static final String MESSAGE_ANSWER_SUCCESS = "Answer sent successfully";
    public static final String MESSAGE_OPTION_NUMBER_EXPECTED = "MCQ question expects option number as answer";

    private final Answer attemptedAnswer;

    public AnswerCommand(Answer attemptedAnswer) {
        this.attemptedAnswer = attemptedAnswer;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getState() != State.IN_TEST) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN);
        }
        if (model.isCardAlreadyAnswered()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ANSWER_COMMAND);
        }
        Card cardToMark = model.getCurrentTestedCard();

        boolean isAttemptCorrect;
        switch (cardToMark.getCardType()) {
        case MCQ:
            try {
                int answerIndex = Integer.parseInt(attemptedAnswer.fullAnswer);
                if (answerIndex < 1 || answerIndex > cardToMark.getOptions().size() + 1) {
                    throw new CommandException(MESSAGE_OPTION_NUMBER_EXPECTED);
                }
                isAttemptCorrect = model.markAttemptedMcqAnswer(answerIndex);
            } catch (NumberFormatException e) {
                throw new CommandException(MESSAGE_OPTION_NUMBER_EXPECTED);
            }
            break;
        case SINGLE_ANSWER:
            isAttemptCorrect = model.markAttemptedAnswer(attemptedAnswer);
            break;
        default:
            isAttemptCorrect = false;
        }
        model.setCardAsAnswered();

        Card scoredCard = model.createScoredCard(cardToMark, isAttemptCorrect);
        model.setCard(cardToMark, scoredCard);
        model.updateFilteredCard(PREDICATE_SHOW_ALL_CARDS);

        if (isAttemptCorrect) {
            return new CommandResult(MESSAGE_ANSWER_SUCCESS, CommandResult.Type.ANSWER_CORRECT);
        } else {
            return new CommandResult(MESSAGE_ANSWER_SUCCESS, CommandResult.Type.ANSWER_WRONG);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnswerCommand // instanceof handles nulls
                && attemptedAnswer.equals(((AnswerCommand) other).attemptedAnswer)); // state check
    }
}
