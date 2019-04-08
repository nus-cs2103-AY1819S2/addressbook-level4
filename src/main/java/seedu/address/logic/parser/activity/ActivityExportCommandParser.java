package seedu.address.logic.parser.activity;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ActivityExportCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ActivityExportCommand object
 */
public class ActivityExportCommandParser implements Parser<ActivityExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ActivityExportCommand
     * and returns an ActivityExportCommand object for execution.
     * @throws seedu.address.logic.parser.exceptions.ParseException
     * if the user input does not conform the expected format
     */
    public ActivityExportCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ActivityExportCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ActivityExportCommand.MESSAGE_USAGE), pe);
        }
    }
}
