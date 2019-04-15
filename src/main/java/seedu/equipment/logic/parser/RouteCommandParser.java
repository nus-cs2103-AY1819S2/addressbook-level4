package seedu.equipment.logic.parser;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.logic.commands.RouteCommand;
import seedu.equipment.logic.parser.exceptions.ParseException;
import seedu.equipment.model.equipment.Address;

/**
 * Parses input arguments and creates a new RouteCommand object
 */
public class RouteCommandParser implements Parser<RouteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RoutetCommand
     * and returns an RouteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RouteCommand parse(String args) throws ParseException {
        try {
            Address address = ParserUtil.parseAddress(args);
            return new RouteCommand(address);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RouteCommand.MESSAGE_USAGE), pe);
        }
    }
}
