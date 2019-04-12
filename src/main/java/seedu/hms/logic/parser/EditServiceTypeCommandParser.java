package seedu.hms.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.EditServiceTypeCommand;
import seedu.hms.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditBookingCommand object
 */
public class EditServiceTypeCommandParser implements Parser<EditServiceTypeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBookingCommand
     * and returns an EditBookingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditServiceTypeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TIMING, PREFIX_CAPACITY, PREFIX_RATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditServiceTypeCommand.MESSAGE_USAGE), pe);
        }

        EditServiceTypeCommand.EditServiceTypeDescriptor editServiceTypeDescriptor =
            new EditServiceTypeCommand.EditServiceTypeDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editServiceTypeDescriptor.setName(ParserUtil.parseType(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_TIMING).isPresent()) {
            editServiceTypeDescriptor.setTiming(ParserUtil.parseTiming(argMultimap.getValue(PREFIX_TIMING).get()));
        }
        if (argMultimap.getValue(PREFIX_CAPACITY).isPresent()) {
            editServiceTypeDescriptor.setCapacity(ParserUtil.parseCapacity(argMultimap.getValue(PREFIX_CAPACITY)
                .get()));
        }
        if (argMultimap.getValue(PREFIX_RATE).isPresent()) {
            editServiceTypeDescriptor.setRatePerHour(ParserUtil.parseRate(argMultimap.getValue(PREFIX_RATE).get()));
        }

        if (!editServiceTypeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditServiceTypeCommand.MESSAGE_NOT_EDITED);
        }

        return new EditServiceTypeCommand(index, editServiceTypeDescriptor);
    }
}
