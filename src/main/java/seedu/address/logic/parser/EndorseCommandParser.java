package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EndorseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class EndorseCommandParser implements Parser<EndorseCommand>{

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public EndorseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        String endorseName = null;
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EndorseCommand.MESSAGE_USAGE), pe);
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            endorseName = argMultimap.getValue(PREFIX_NAME).get();
        }

        return new EndorseCommand(index, endorseName);
    }
}
