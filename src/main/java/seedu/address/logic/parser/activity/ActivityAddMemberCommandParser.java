package seedu.address.logic.parser.activity;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ActivityAddMemberCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MatricNumber;

/**
 * Parses input arguments and creates a new ActivityAddMemberCommand object
 */
public class ActivityAddMemberCommandParser implements Parser<ActivityAddMemberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ActivityAddMemberCommand
     * and returns an ActivityAddMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ActivityAddMemberCommand parse(String args) throws ParseException {
        String[] splitArgs = args.split("\\s+");

        try {
            Index index = ParserUtil.parseIndex(splitArgs[0]);
            MatricNumber matricNumber = new MatricNumber(splitArgs[1]);
            return new ActivityAddMemberCommand(index, matricNumber);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ActivityAddMemberCommand.MESSAGE_USAGE), pe);
        }
    }
}
