package seedu.address.logic.parser.doctor;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.doctor.SelectDoctorCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectPatientCommand object
 */
public class SelectDoctorCommandParser implements Parser<SelectDoctorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectDoctorCommand
     * and returns an SelectDoctorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectDoctorCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectDoctorCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDoctorCommand.MESSAGE_USAGE), pe);
        }
    }
}
