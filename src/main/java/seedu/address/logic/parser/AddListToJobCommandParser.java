package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;

import seedu.address.logic.commands.AddListToJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobListName;
import seedu.address.model.job.JobName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddListToJobCommandParser implements Parser<AddListToJobCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonToJobCommand
     * and returns an AddPersonToJobCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddListToJobCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_JOBNAME);

        JobListName toListName;
        JobListName fromListName = JobListName.EMPTY;
        JobName toAdd;

        toListName = ParserUtil.parseJobListName(args.split("\\b\\s")[0].trim());

        if (toListName == JobListName.EMPTY) {
            throw new ParseException(AddListToJobCommand.MESSAGE_NO_DESTINATION
                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddListToJobCommand.MESSAGE_USAGE));
        }

        try {
            toAdd = ParserUtil.parseJobName(argMultimap.getValue(PREFIX_JOBNAME).get());
        } catch (Exception noJob) {
            toAdd = null;
        }

        if ((toAdd == null && args.split("\\b\\s").length > 1) || args.split("\\b\\s").length > 2) {
            fromListName = ParserUtil.parseJobListName(args.split("\\b\\s")[1].trim());
        }

        return new AddListToJobCommand(toAdd, toListName, fromListName);
    }

}
