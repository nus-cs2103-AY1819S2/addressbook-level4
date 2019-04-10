package seedu.knowitall.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import javafx.util.Pair;
import seedu.knowitall.commons.core.index.Index;
import seedu.knowitall.logic.commands.EditFolderCommand;
import seedu.knowitall.logic.parser.exceptions.ParseException;
import seedu.knowitall.model.ReadOnlyCardFolder;

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
        parsedObjects = ParserUtil.parseIndexAndFolderName(args);

        if (!parsedObjects.getValue().chars().allMatch(c -> Character.isLetter(c) || Character.isDigit(c))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReadOnlyCardFolder.MESSAGE_CONSTRAINTS));
        }

        return new EditFolderCommand(parsedObjects.getKey(), parsedObjects.getValue());
    }
}
