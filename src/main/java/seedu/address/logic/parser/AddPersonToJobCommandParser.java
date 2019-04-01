package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddPersonToJobCommand;
import seedu.address.logic.commands.CreateJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobName;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddPersonToJobCommandParser implements Parser<AddPersonToJobCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonToJobCommand
     * and returns an AddPersonToJobCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPersonToJobCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_JOBNAME, PREFIX_NRIC);

        if (!arePrefixesPresent(argMultimap, PREFIX_JOBNAME, PREFIX_NRIC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateJobCommand.MESSAGE_USAGE));
        }

        JobName job = ParserUtil.parseJobName(argMultimap.getValue(PREFIX_JOBNAME).get());
        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());

        return new AddPersonToJobCommand(job, nric);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
