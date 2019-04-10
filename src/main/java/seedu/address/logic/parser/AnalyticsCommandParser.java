package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.GenerateAnalyticsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobListName;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class AnalyticsCommandParser implements Parser<GenerateAnalyticsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenerateAnalyticsCommand
     * and returns an GenerateAnalyticsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateAnalyticsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new GenerateAnalyticsCommand();
        } else {
            JobListName listName;
            try {
                listName = ParserUtil.parseJobListName(argMultimap.getPreamble());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        GenerateAnalyticsCommand.MESSAGE_USAGE), pe);
            }
            return new GenerateAnalyticsCommand(listName);
        }
    }
}
