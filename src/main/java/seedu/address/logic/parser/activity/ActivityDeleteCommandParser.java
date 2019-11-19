package seedu.address.logic.parser.activity;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.ActivityDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author minernchan

/**
 * Parses input arguments and creates a new ActivityDeleteCommandParser object
 */
public class ActivityDeleteCommandParser implements Parser<ActivityDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ActivityDeleteCommand
     * and returns an ActivityDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ActivityDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ActivityDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ActivityDeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
