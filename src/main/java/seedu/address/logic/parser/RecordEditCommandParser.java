package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RecordEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.description.Description;

/**
 * Parses input arguments and creates a new RecordEditCommand object
 */
public class RecordEditCommandParser implements Parser<RecordEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RecordEditCommand
     * and returns an RecordEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecordEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESC);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RecordEditCommand.MESSAGE_USAGE), pe);
        }

        Description editDescription;
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            editDescription = ParserUtil.parseDesc(argMultimap.getValue(PREFIX_DESC).get());
        } else {
            throw new ParseException(RecordEditCommand.MESSAGE_NOT_EDITED);
        }

        return new RecordEditCommand(index, editDescription);
    }

}
