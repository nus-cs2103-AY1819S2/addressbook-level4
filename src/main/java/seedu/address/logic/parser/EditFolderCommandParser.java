package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditFolderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditFolderCommand object
 */
public class EditFolderCommandParser implements Parser<EditFolderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditFolderCommand
     * and returns an EditFolderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditFolderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Pair<Index, String> parsedObjects;
        try {
            parsedObjects = ParserUtil.parseIndexAndFolderName(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditFolderCommand.MESSAGE_USAGE), pe);
        }

        return new EditFolderCommand(parsedObjects.getKey(), parsedObjects.getValue());
    }
}
