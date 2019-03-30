package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.knowitall.commons.core.index.Index;
import seedu.knowitall.logic.commands.DeleteFolderCommand;
import seedu.knowitall.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteFolderCommand object
 */
public class DeleteFolderCommandParser implements Parser<DeleteFolderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteFolderCommand
     * and returns an DeleteFolderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFolderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteFolderCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFolderCommand.MESSAGE_USAGE), pe);
        }
    }

}
