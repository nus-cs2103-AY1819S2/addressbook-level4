package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.knowitall.commons.core.Messages.MESSAGE_NO_NEGATIVE_INDEX;

import java.util.List;

import seedu.knowitall.commons.exceptions.IllegalValueException;
import seedu.knowitall.logic.commands.ExportCommand;
import seedu.knowitall.logic.parser.exceptions.ParseException;


/**
 * Parses input for export command arguments and creates a new export command object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String userInput) throws ParseException {
        try {
            List<Integer> folderIndexes = ParserUtil.parseFolderIndex(userInput);
            return new ExportCommand(folderIndexes);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE), e);
        } catch (IllegalValueException e) {
            throw new ParseException(MESSAGE_NO_NEGATIVE_INDEX);
        }
    }
}
