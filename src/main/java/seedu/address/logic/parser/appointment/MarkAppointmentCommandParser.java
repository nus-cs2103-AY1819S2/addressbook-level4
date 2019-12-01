package seedu.address.logic.parser.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.appointment.MarkAppointmentCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkAppointmentCommand object
 */
public class MarkAppointmentCommandParser implements Parser<MarkAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkAppointmentCommand
     * and returns an MarkAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_APPT_STATUS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, MarkAppointmentCommand.MESSAGE_USAGE), pe);
        }

        MarkAppointmentCommand.ChangedAppointmentDescriptor changedAppointmentDescriptor =
                new MarkAppointmentCommand.ChangedAppointmentDescriptor();
        if (argMultimap.getValue(PREFIX_APPT_STATUS).isPresent()) {
            changedAppointmentDescriptor.setStatus(ParserUtil.parseAppointmentStatus(argMultimap
                    .getValue(PREFIX_APPT_STATUS).get().toUpperCase()));
        }

        if (!changedAppointmentDescriptor.isStatusChanged()) {
            throw new ParseException(MarkAppointmentCommand.MESSAGE_STATUS_MISSING);
        }

        return new MarkAppointmentCommand(index, changedAppointmentDescriptor);
    }
}
