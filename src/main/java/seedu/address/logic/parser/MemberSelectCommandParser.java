package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MemberSelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MemberSelectCommand object
 */
public class MemberSelectCommandParser implements Parser<MemberSelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MemberSelectCommand
     * and returns an MemberSelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MemberSelectCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MemberSelectCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemberSelectCommand.MESSAGE_USAGE), pe);
        }
    }
}
