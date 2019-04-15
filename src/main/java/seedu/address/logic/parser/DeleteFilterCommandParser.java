package seedu.address.logic.parser;

import static seedu.address.logic.commands.DeleteFilterCommand.MESSAGE_LACK_FILTERNAME;
import static seedu.address.logic.commands.DeleteFilterCommand.MESSAGE_USAGE_ALLJOB_SCREEN;
import static seedu.address.logic.commands.DeleteFilterCommand.MESSAGE_USAGE_DETAIL_SCREEN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTERNAME;

import seedu.address.logic.commands.DeleteFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobListName;

/**
 * Parses input arguments and creates a new DeleteFilterCommand object
 */
public class DeleteFilterCommandParser implements Parser<DeleteFilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteFilterCommand
     * and returns an DeleteFilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_FILTERNAME);
        JobListName listName;
        String commandName;
        if (argMultimap.getValue(PREFIX_FILTERNAME).isPresent()) {
            commandName = argMultimap.getValue(PREFIX_FILTERNAME).get().trim();
        } else {
            throw new ParseException(String.format(MESSAGE_LACK_FILTERNAME,
                MESSAGE_USAGE_ALLJOB_SCREEN + MESSAGE_USAGE_DETAIL_SCREEN));
        }
        String preambleString = argMultimap.getPreamble();
        String listNameString = preambleString.trim();
        try {
            listName = ParserUtil.parseJobListName(listNameString);
            return new DeleteFilterCommand(listName, commandName);
        } catch (ParseException pe) {
            throw new ParseException(String.format(pe.getMessage(),
                MESSAGE_USAGE_ALLJOB_SCREEN + MESSAGE_USAGE_DETAIL_SCREEN), pe);
        }
    }

}
