package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;

import java.util.stream.Stream;

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
        JobListName fromListName;
        JobName toAdd;
        String preambleString = argMultimap.getPreamble();
        String toListNameString = preambleString.split("\\s+")[0].trim();
        try {
            toListName = ParserUtil.parseJobListName(toListNameString);
        } catch (ParseException pe) {
            throw new ParseException(String.format(AddListToJobCommand.MESSAGE_NO_DESTINATION,
                    AddListToJobCommand.MESSAGE_USAGE), pe);
        }
        try {
            String fromListNameString = preambleString.split("\\s+")[1].trim();
            fromListName = ParserUtil.parseJobListName(fromListNameString);
            toAdd = ParserUtil.parseJobName(argMultimap.getValue(PREFIX_JOBNAME).get());
        } catch (Exception e) {
            fromListName = JobListName.STUB;
            toAdd = ParserUtil.parseJobName(argMultimap.getValue(PREFIX_JOBNAME).get());
        }

        return new AddListToJobCommand(toAdd, toListName, fromListName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
