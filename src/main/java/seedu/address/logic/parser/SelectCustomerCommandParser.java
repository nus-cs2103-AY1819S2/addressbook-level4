package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectCustomerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCustomerCommand object
 */
public class SelectCustomerCommandParser implements Parser<SelectCustomerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCustomerCommand
     * and returns an SelectCustomerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCustomerCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectCustomerCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCustomerCommand.MESSAGE_USAGE), pe);
        }
    }
}
