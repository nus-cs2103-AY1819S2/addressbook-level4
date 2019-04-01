package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LISTNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddPersonToJobCommand;
import seedu.address.logic.commands.CreateJobCommand;
import seedu.address.logic.commands.DisplayJobListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobName;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class DisplayJobListCommandParser implements Parser<DisplayJobListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayJobListCommand
     * and returns an DisplayJobListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayJobListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_JOBNAME, PREFIX_LISTNUMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_JOBNAME, PREFIX_LISTNUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayJobListCommand.MESSAGE_USAGE));
        }

        JobName job = ParserUtil.parseJobName(argMultimap.getValue(PREFIX_JOBNAME).get());
        int listNumber = Integer.parseInt(argMultimap.getValue(PREFIX_LISTNUMBER).get());

        return new DisplayJobListCommand(job, listNumber);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
