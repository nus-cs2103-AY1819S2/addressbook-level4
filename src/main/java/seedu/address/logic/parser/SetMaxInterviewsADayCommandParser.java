package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SetMaxInterviewsADayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetMaxInterviewsADayCommand object
 */
public class SetMaxInterviewsADayCommandParser implements Parser<SetMaxInterviewsADayCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetMaxInterviewsADayCommand parse(String args) throws ParseException {
        try {
            int value = ParserUtil.parseMaxInterviewsADay(args);
            return new SetMaxInterviewsADayCommand(value);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetMaxInterviewsADayCommand.MESSAGE_USAGE), pe);
        }
    }

}
