package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EmptyCardFolderException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;

/**
 * Test a card folder identified using it's displayed index from the card folder list.
 */
public class TestCommand extends Command {

    public static final String COMMAND_WORD = "test";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Enters a test session using the current card folder.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ENTER_TEST_FOLDER_SUCCESS = "In Test Session";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isInFolder()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        }

        if (model.checkIfInsideTestSession()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_INSIDE_TEST_SESSION);
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TestCommand); // instanceof handles nulls
    }
}
