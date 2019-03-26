package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.CreateJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class CreateJobCommandParser implements Parser<CreateJobCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateJobCommand
     * and returns an CreateJobCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateJobCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_JOBNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_JOBNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateJobCommand.MESSAGE_USAGE));
        }

        JobName name = ParserUtil.parseJobName(argMultimap.getValue(PREFIX_JOBNAME).get());
        Job job = new Job(name);

        return new CreateJobCommand(job);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
