package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_NEGATIVE_INDEX;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input for export command arguments and creates a new export command object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String userInput) throws ParseException {
        /*
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                userInput, PREFIX_FILENAME, PREFIX_FOLDERNAME);
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_FOLDERNAME, PREFIX_FILENAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        Set<CardFolderExport> folderNames = ParserUtil.parseFolders(argMultimap.getAllValues(PREFIX_FOLDERNAME));
        CsvFile filename = ParserUtil.parseFileName(argMultimap.getValue(PREFIX_FILENAME).get());
        */

        // check if array elements contain all numbers
        try {
            List<Integer> folderIndexes = ParserUtil.parseFolderIndex(userInput);
            return new ExportCommand(folderIndexes);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE), e);
        } catch (IllegalValueException e) {
            throw new ParseException(MESSAGE_NO_NEGATIVE_INDEX);
        }




        // return new ExportCommand(folderNames, filename);
    }
}
