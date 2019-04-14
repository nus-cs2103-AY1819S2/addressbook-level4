package seedu.address.logic.parser.medicalhistory;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.medicalhistory.SelectMedHistCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectMedHistCommand object
 */
public class SelectMedHistCommandParser implements Parser<SelectMedHistCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectMedHistCommand
     * and returns an SelectMedHistCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectMedHistCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectMedHistCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectMedHistCommand.MESSAGE_USAGE), pe);
        }
    }
}
