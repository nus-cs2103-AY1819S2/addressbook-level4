package seedu.address.logic.parser.request;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHWORKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REQUEST;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.request.AssignRequestCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignRequestCommand object
 */
public class AssignRequestCommandParser implements Parser<AssignRequestCommand> {
    @Override
    public AssignRequestCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput,
            PREFIX_HEALTHWORKER, PREFIX_REQUEST);

        if (!ParserUtil.arePrefixesPresent(argumentMultimap, PREFIX_HEALTHWORKER, PREFIX_REQUEST)
            || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignRequestCommand.MESSAGE_USAGE));
        }

        Index healthWorkerId =
            ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_HEALTHWORKER).get());
        Set<Index> requestIds =
            ParserUtil.parseIndexes(argumentMultimap.getAllValues(PREFIX_REQUEST));

        return new AssignRequestCommand(healthWorkerId, requestIds);
    }
}
