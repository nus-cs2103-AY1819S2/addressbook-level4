package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MovePeopleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobListName;
import seedu.address.model.job.JobName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class MovePeopleCommandParser implements Parser<MovePeopleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonToJobCommand
     * and returns an AddPersonToJobCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MovePeopleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_JOBNAME);

        JobListName to;
        JobListName from;
        ArrayList<Index> indexes = new ArrayList<>();
        JobName toAdd;
        String fromString;
        String indexString;

        to = ParserUtil.parseJobListName(args.split("\\b\\s")[0].trim());

        if (to == JobListName.EMPTY) {
            throw new ParseException(MovePeopleCommand.MESSAGE_NO_DESTINATION
                   + String.format(MESSAGE_INVALID_COMMAND_FORMAT, MovePeopleCommand.MESSAGE_USAGE));
        }

        try {
            fromString = args.split("\\b\\s")[1].trim();
        } catch (Exception e) {
            throw new ParseException(MovePeopleCommand.MESSAGE_NO_SOURCE + "\n"
                    + MovePeopleCommand.MESSAGE_USAGE);
        }

        from = ParserUtil.parseJobListName(fromString);

        try {
            indexString = args.split("\\b\\s")[2].trim();
        } catch (Exception e) {
            throw new ParseException(MovePeopleCommand.MESSAGE_NO_INDEX + "\n"
                    + MovePeopleCommand.MESSAGE_USAGE);
        }
        ArrayList<String> numbers = new ArrayList<>(Arrays.asList(indexString.split("[,\\s]+")));
        for (int i = 0; i < numbers.size(); i++) {
            try {
                indexes.add(ParserUtil.parseIndex(numbers.get(i)));
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MovePeopleCommand.MESSAGE_BAD_INDEX), pe);
            }
        }

        try {
            toAdd = ParserUtil.parseJobName(argMultimap.getValue(PREFIX_JOBNAME).get());
        } catch (Exception e) {
            toAdd = null;
        }


        return new MovePeopleCommand(to, from, indexes, toAdd);
    }

}
