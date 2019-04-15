package seedu.address.logic.parser.prescription;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.prescription.EditPrescriptionCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditMedHistCommand object
 */
public class EditPrescriptionCommandParser implements Parser<EditPrescriptionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditPrescriptionCommand
     * and returns an EditPrescriptionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPrescriptionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditPrescriptionCommand.MESSAGE_USAGE), pe);
        }

        EditPrescriptionCommand.EditPrescriptionDescriptor editPrescriptionDescriptor =
                new EditPrescriptionCommand.EditPrescriptionDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editPrescriptionDescriptor.setDescription(ParserUtil
                    .parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (!editPrescriptionDescriptor.isDescriptionEdited()) {
            throw new ParseException(EditPrescriptionCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPrescriptionCommand(index, editPrescriptionDescriptor);
    }
}
