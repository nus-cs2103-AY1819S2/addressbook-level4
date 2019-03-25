package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.request.ListRequestCommand;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CommandMode;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the different modes for the list command and returns the respective list command.
 */
public class ListCommandParser implements Parser<ListCommand> {

    @Override
    public ListCommand parse(String userInput) throws ParseException {
        CommandMode commandMode = ArgumentTokenizer.checkMode(userInput);

        if (commandMode == CommandMode.HEALTH_WORKER) {
            return new ListHealthWorkerCommand();
        } else if (commandMode == CommandMode.REQUEST) {
            return new ListRequestCommand();
        }

        throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_UAGE));
    }
}
