package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIMIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;

import java.util.Optional;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortCommand.Limit;
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
        // Check if any Order or Limit is present
        if (!args.isEmpty()) {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LIMIT, PREFIX_ORDER);

            Limit limit;
            Order order;

            // Check for presence of Order
            if (argMultimap.getValue(PREFIX_ORDER).isPresent()) {
                order = ParserUtil.parseOrder(argMultimap.getValue(PREFIX_ORDER).get());
            } else {
                order = null;
            }

            // Check for presence of Limit
            if (argMultimap.getValue(PREFIX_LIMIT).isPresent()) {
                limit = ParserUtil.parseLimit(argMultimap.getValue(PREFIX_LIMIT).get());
            } else {
                limit = null;
            }

            // Ensure validity of values parsed
            if (order == null && limit == null) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            } else if (order == null) {
                // Default descending sort of restaurants triggered
                order = new Order("DES");
            }

            return new SortCommand(order, Optional.ofNullable(limit));
        } else {
            // Return default Sort
            return new SortCommand(new Order("DES"), Optional.empty());
        }
    }
}
