package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.knowitall.logic.commands.AddFolderCommand;
import seedu.knowitall.logic.parser.exceptions.ParseException;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.ReadOnlyCardFolder;


/**
 * Parses input arguments and creates a new AddFolderCommand object
 */
public class AddFolderCommandParser implements Parser<AddFolderCommand> {

    /**
     * Parses the given {@code String} in the context of the AddFolderCommand
     * and returns an AddFolderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFolderCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFolderCommand.MESSAGE_USAGE));
        }

        if (!CardFolder.isValidFolderName(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReadOnlyCardFolder.MESSAGE_CONSTRAINTS));
        }

        return new AddFolderCommand(trimmedArgs);
    }
}
