package seedu.knowitall.logic.commands;

import static seedu.knowitall.logic.commands.EndCommand.MESSAGE_END_TEST_SESSION_SUCCESS;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.Model.State;
import seedu.knowitall.model.card.Card;

/**
 * Displays to the user the next question in the folder during the test session.
 * This command is valid only when user is in a test session.
 */
public class NextCommand extends Command {

    public static final String COMMAND_WORD = "next";

    public static final String MESSAGE_NEXT_QUESTION_SUCCESS = "Displaying the next question in test session";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.getState() != State.IN_TEST) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN);
        }
        if (!model.isCardAlreadyAnswered()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NEXT_COMMAND);
        }

        boolean successfullyFoundNextCard = model.testNextCard();

        if (!successfullyFoundNextCard) {
            model.endTestSession();
            return new CommandResult(MESSAGE_END_TEST_SESSION_SUCCESS,
                    CommandResult.Type.END_TEST_SESSION);
        }

        Card cardToTest = model.getCurrentTestedCard();
        CommandResult commandResult = new CommandResult(MESSAGE_NEXT_QUESTION_SUCCESS,
                CommandResult.Type.SHOW_NEXT_CARD);
        commandResult.setTestSessionCard(cardToTest);
        return commandResult;
    }

}
