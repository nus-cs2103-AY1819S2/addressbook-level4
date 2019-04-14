package seedu.address.logic.parser.medicalhistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WRITEUP;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.medicalhistory.EditMedHistCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditMedHistCommand object
 */
public class EditMedHistCommandParser implements Parser<EditMedHistCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditMedHistCommand
     * and returns an EditMedHistCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditMedHistCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_WRITEUP);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditMedHistCommand.MESSAGE_USAGE), pe);
        }

        EditMedHistCommand.EditMedHistDescriptor editMedHistDescriptor = new EditMedHistCommand.EditMedHistDescriptor();
        if (argMultimap.getValue(PREFIX_WRITEUP).isPresent()) {
            editMedHistDescriptor.setWriteUp(ParserUtil.parseWriteUp(argMultimap.getValue(PREFIX_WRITEUP).get()));
        }

        if (!editMedHistDescriptor.isWriteUpEdited()) {
            throw new ParseException(EditMedHistCommand.MESSAGE_NOT_EDITED);
        }

        return new EditMedHistCommand(index, editMedHistDescriptor);
    }
}
