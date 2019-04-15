package seedu.address.logic.parser.prescription;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.prescription.SelectPrescriptionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectPrescriptionCommand object
 */
public class SelectPrescriptionCommandParser implements Parser<SelectPrescriptionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectPrescriptionCommand
     * and returns an SelectPrescriptionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectPrescriptionCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectPrescriptionCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectPrescriptionCommand.MESSAGE_USAGE), pe);
        }
    }
}
