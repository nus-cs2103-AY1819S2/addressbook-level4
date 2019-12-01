package seedu.address.logic.parser.doctor;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.doctor.DeleteDoctorCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteDoctorCommand object
 */
public class DeleteDoctorCommandParser implements Parser<DeleteDoctorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteDoctorCommand
     * and returns an DeleteDoctorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteDoctorCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteDoctorCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDoctorCommand.MESSAGE_USAGE), pe);
        }
    }

}
