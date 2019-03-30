package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.management.DeleteCardCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link DeleteCardCommand} object.
 */
public class DeleteCardParser implements Parser<DeleteCardCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@link DeleteCardCommand} and returns an {@link DeleteCardCommand} object
     * for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCardCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCardCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            DeleteCardCommand.MESSAGE_USAGE), e);
        }
    }
}
