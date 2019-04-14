package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.knowitall.logic.commands.ImportCommand;
import seedu.knowitall.logic.parser.exceptions.ParseException;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.storage.csvmanager.CsvFile;


/**
 * Parser input for import command arguments.
 */
public class ImportCommandParser implements Parser<ImportCommand> {



    @Override
    public ImportCommand parse(String userInput) throws ParseException {
        String filename = userInput.trim();

        String[] filenameWithoutExt = filename.split(CsvFile.FILE_EXT_REGEX);

        // Filename imported will be the name of the card folder in application
        if (!CardFolder.isValidFolderName(filenameWithoutExt[0])) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CsvFile.FILENAME_CONSTRAINTS));
        }
        CsvFile csvFile = ParserUtil.parseFileName(filename);
        return new ImportCommand(csvFile);
    }
}
