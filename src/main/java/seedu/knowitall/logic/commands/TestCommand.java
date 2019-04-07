package seedu.knowitall.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.EmptyCardFolderException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.Model.State;
import seedu.knowitall.model.card.Card;

/**
 * Enters a test session using the current card folder.
 */
public class TestCommand extends Command {

    public static final String COMMAND_WORD = "test";

    public static final String MESSAGE_ENTER_TEST_FOLDER_SUCCESS = "In Test Session";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getState() != State.IN_FOLDER) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        }

        try {
            model.testCardFolder();
            Card cardToTest = model.getCurrentTestedCard();
            CommandResult commandResult = new CommandResult(MESSAGE_ENTER_TEST_FOLDER_SUCCESS,
                    CommandResult.Type.START_TEST_SESSION);
            commandResult.setTestSessionCard(cardToTest);
            return commandResult;
        } catch (EmptyCardFolderException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_ON_EMPTY_FOLDER);
        }
    }

}
