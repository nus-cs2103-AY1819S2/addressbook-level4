package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.options.ListOption;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a new SortCommand object
 */

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        try {
            String[] inputs = args.trim().split(" ");
            ListOption sortOption = ParserUtil.parseListValue(inputs[0].trim());
            if (inputs.length > 1) {
                return new ListCommand(sortOption, inputs[1].trim());
            } else {
                return new ListCommand(sortOption, null);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), pe);
        }
    }
}
