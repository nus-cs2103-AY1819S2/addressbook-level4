package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROCEDURE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RecordEditCommand;
import seedu.address.logic.commands.RecordEditCommand.EditRecordDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

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
                ArgumentTokenizer.tokenize(args, PREFIX_DESC, PREFIX_PROCEDURE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RecordEditCommand.MESSAGE_USAGE), pe);
        }

        EditRecordDescriptor editRecordDescriptor = new EditRecordDescriptor();
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            editRecordDescriptor.setDescription(ParserUtil.parseDesc(argMultimap.getValue(PREFIX_DESC).get()));
        }
        if (argMultimap.getValue(PREFIX_PROCEDURE).isPresent()) {
            editRecordDescriptor.setProcedure(ParserUtil.parseProcedure(argMultimap.getValue(PREFIX_PROCEDURE).get()));
        }

        if (!editRecordDescriptor.isAnyFieldEditted()) {
            throw new ParseException(RecordEditCommand.MESSAGE_NOT_EDITED);
        }

        return new RecordEditCommand(index, editRecordDescriptor);
    }


}
