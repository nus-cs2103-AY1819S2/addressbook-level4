package seedu.address.logic.parser;

import java.util.Calendar;
import java.util.List;

import seedu.address.logic.commands.SetBlockOutDatesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class SetBlockOutDatesCommandParser implements Parser<SetBlockOutDatesCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SetMaxInterviewsADayCommand
     * and returns an SetMaxInterviewsADayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetBlockOutDatesCommand parse(String args) throws ParseException {
        try {
            List<Calendar> value = ParserUtil.parseBlockOutDates(args);
            return new SetBlockOutDatesCommand(value);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetBlockOutDatesCommand.MESSAGE_USAGE), pe);
        }
    }
}
