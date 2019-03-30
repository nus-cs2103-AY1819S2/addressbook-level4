package seedu.address.logic.commands;

import static seedu.address.logic.commands.EndCommand.MESSAGE_END_TEST_SESSION_SUCCESS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;

/**
 * Displays to the user the next question in the folder during the test session.
 * This command is valid only when user is in a test session.
 */
public class NextCommand extends Command {

    public static final String COMMAND_WORD = "next";

    public static final String MESSAGE_NEXT_QUESTION_SUCCESS = "Displaying the next question in test session";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.checkIfInsideTestSession()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN);
        }
        if (!model.checkIfCardAlreadyAnswered()) {
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
