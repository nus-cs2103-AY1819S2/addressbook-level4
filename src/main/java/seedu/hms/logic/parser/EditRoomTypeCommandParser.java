package seedu.hms.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_RATE;

import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.EditRoomTypeCommand;
import seedu.hms.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditReservationCommand object
 */
public class EditRoomTypeCommandParser implements Parser<EditRoomTypeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditReservationCommand
     * and returns an EditReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRoomTypeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CAPACITY, PREFIX_RATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRoomTypeCommand.MESSAGE_USAGE),
                pe);
        }

        EditRoomTypeCommand.EditRoomTypeDescriptor editRoomTypeDescriptor =
            new EditRoomTypeCommand.EditRoomTypeDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRoomTypeDescriptor.setName(ParserUtil.parseType(argMultimap.getValue(PREFIX_NAME).get()).toString());
        }
        if (argMultimap.getValue(PREFIX_CAPACITY).isPresent()) {
            editRoomTypeDescriptor.setNumberOfRooms(ParserUtil.parseCapacity(argMultimap.getValue(PREFIX_CAPACITY)
                .get()));
        }
        if (argMultimap.getValue(PREFIX_RATE).isPresent()) {
            editRoomTypeDescriptor.setRatePerDay(ParserUtil.parseRate(argMultimap.getValue(PREFIX_RATE).get()));
        }

        if (!editRoomTypeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRoomTypeCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRoomTypeCommand(index, editRoomTypeDescriptor);
    }
}
