package seedu.address.logic.parser.activity;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ActivityDeleteMemberCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MatricNumber;

/**
 * Parses input arguments and creates a new ActivityAddMemberCommand object
 */
public class ActivityDeleteMemberCommandParser implements Parser<ActivityDeleteMemberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ActivityAddMemberCommand
     * and returns an ActivityAddMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ActivityDeleteMemberCommand parse(String args) throws ParseException {
        String[] splitArgs = args.trim().split("\\s+");
        if (splitArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ActivityDeleteMemberCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(splitArgs[0]);
        MatricNumber matricNumber = ParserUtil.parseMatricNumber(splitArgs[1]);
        return new ActivityDeleteMemberCommand(index, matricNumber);
    }
}
