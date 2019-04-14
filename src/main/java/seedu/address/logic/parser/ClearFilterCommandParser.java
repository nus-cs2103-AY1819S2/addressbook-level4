package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobListName;

/**
 * Parses input arguments and creates a new ClearFilterCommand object
 */
public class ClearFilterCommandParser implements Parser<ClearFilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearFilterCommand
     * and returns an ClearFilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearFilterCommand parse(String args) throws ParseException {
        JobListName listName;
        String trimedString = args.trim();
        try {
            listName = ParserUtil.parseJobListName(trimedString);
            return new ClearFilterCommand(listName);
        } catch (ParseException pe) {
            throw new ParseException(String.format(pe.getMessage(),
                ClearFilterCommand.MESSAGE_USAGE_ALLJOB_SCREEN + ClearFilterCommand.MESSAGE_USAGE_DETAIL_SCREEN), pe);
        }
    }

}
