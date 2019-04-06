package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortCommand.Order;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        try {
            // Check if any String is present
            if (args.isEmpty()) {
                return new SortCommand(new Order("DES"));
            }

            // Check that there is a valid Order
            Order order = ParserUtil.parseOrder(args);
            return new SortCommand(order);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
        }
    }
}
