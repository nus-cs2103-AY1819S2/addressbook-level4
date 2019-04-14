package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MemberExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MemberExportCommand object
 */
public class MemberExportCommandParser implements Parser<MemberExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MemberExportCommand
     * and returns an MemberExportCommand object for execution.
     * @throws seedu.address.logic.parser.exceptions.ParseException
     * if the user input does not conform the expected format
     */
    public MemberExportCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MemberExportCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemberExportCommand.MESSAGE_USAGE), pe);
        }
    }
}
