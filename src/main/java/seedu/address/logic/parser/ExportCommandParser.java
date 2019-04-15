package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;


public class ExportCommandParser implements Parser<ExportCommand>{

    public ExportCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        String[] keywords = trimmedArgs.split("\\s+");


            String path = keywords[0];
            String name = keywords[1];
            return new ExportCommand(path, name);

    }
}
