package seedu.knowitall.logic.parser;

import seedu.knowitall.logic.commands.ImportCommand;
import seedu.knowitall.logic.parser.exceptions.ParseException;
import seedu.knowitall.storage.csvmanager.CsvFile;


/**
 * Parser input for import command arguments.
 */
public class ImportCommandParser implements Parser<ImportCommand> {


    @Override
    public ImportCommand parse(String userInput) throws ParseException {
        String filename = userInput.trim();
        CsvFile csvFile = ParserUtil.parseFileName(filename);
        return new ImportCommand(csvFile);
    }
}
