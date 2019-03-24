package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.management.OpenLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link OpenLessonCommand} object.
 */
public class OpenLessonParser implements Parser<OpenLessonCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@link OpenLessonCommand} and returns an {@link OpenLessonCommand} object
     * for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OpenLessonCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new OpenLessonCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            OpenLessonCommand.MESSAGE_USAGE), e);
        }
    }
}
