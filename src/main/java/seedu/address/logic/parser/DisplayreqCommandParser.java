package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNFULFILLED;

import seedu.address.logic.commands.DisplayreqCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.moduletaken.IsUnfulfilledPredicate;

/**
 * Parses input arguments and creates a new DisplaymodCommand object
 */
public class DisplayreqCommandParser implements Parser<DisplayreqCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayreqCommand
     * and returns an DisplayreqCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DisplayreqCommand parse(String args) throws ParseException {
        String trimmedString = args.trim();
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(trimmedString, PREFIX_UNFULFILLED);
        if (!argumentMultimap.getValue(PREFIX_UNFULFILLED).isPresent()
                || argumentMultimap.getValue(PREFIX_UNFULFILLED).get().equalsIgnoreCase("false")) {
            return new DisplayreqCommand(new IsUnfulfilledPredicate(true, false));
        } else if (argumentMultimap.getValue(PREFIX_UNFULFILLED).get().equalsIgnoreCase("true")) {
            return new DisplayreqCommand(new IsUnfulfilledPredicate(false, true));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DisplayreqCommand.MESSAGE_USAGE));
        }
    }
}
