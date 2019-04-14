package seedu.equipment.logic.parser;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.commands.SelectClientCommand;
import seedu.equipment.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectClientCommand object
 */
public class SelectClientCommandParser implements Parser<SelectClientCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectClientCommand
     * and returns an SelectClientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectClientCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectClientCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SelectClientCommand.MESSAGE_USAGE), pe);
        }
    }
}
