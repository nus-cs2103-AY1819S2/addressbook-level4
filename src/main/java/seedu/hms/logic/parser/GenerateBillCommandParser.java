package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.GenerateBillCommand;
import seedu.hms.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCustomerCommand object
 */
public class GenerateBillCommandParser implements Parser<GenerateBillCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCustomerCommand
     * and returns an DeleteCustomerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateBillCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new GenerateBillCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenerateBillCommand.MESSAGE_USAGE), pe);
        }
    }
}
