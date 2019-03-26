package seedu.hms.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CUSTOMERS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATES;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ROOM;

import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.EditReservationCommand;
import seedu.hms.logic.commands.EditReservationCommand.EditReservationDescriptor;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;

/**
 * Parses input arguments and creates a new EditReservationCommand object
 */
public class EditReservationCommandParser implements Parser<EditReservationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditReservationCommand
     * and returns an EditReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditReservationCommand parse(String args, CustomerModel customerModel) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_ROOM, PREFIX_DATES, PREFIX_PAYER,
                PREFIX_CUSTOMERS, PREFIX_COMMENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditReservationCommand.MESSAGE_USAGE),
                pe);
        }

        EditReservationDescriptor editReservationDescriptor = new EditReservationDescriptor();
        if (argMultimap.getValue(PREFIX_ROOM).isPresent()) {
            editReservationDescriptor.setRoomType(ParserUtil.parseRoom(argMultimap.getValue(PREFIX_ROOM).get()));
        }
        if (argMultimap.getValue(PREFIX_DATES).isPresent()) {
            editReservationDescriptor.setDates(ParserUtil.parseDates(argMultimap.getValue(PREFIX_DATES).get()));
        }
        if (argMultimap.getValue(PREFIX_PAYER).isPresent()) {
            editReservationDescriptor.setPayer(ParserUtil.parseCustomer(argMultimap.getValue(PREFIX_PAYER).get(),
                customerModel.getFilteredCustomerList()));
        }
        if (argMultimap.getValue(PREFIX_CUSTOMERS).isPresent()) {
            editReservationDescriptor.setOtherUsers(ParserUtil.parseCustomers
                (argMultimap.getAllValues(PREFIX_CUSTOMERS), customerModel.getFilteredCustomerList()));
        }
        if (argMultimap.getValue(PREFIX_COMMENT).isPresent()) {
            editReservationDescriptor.setComment(argMultimap.getValue(PREFIX_COMMENT));
        }

        if (!editReservationDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditReservationCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReservationCommand(index, editReservationDescriptor);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditReservationCommand
     * and returns an EditReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditReservationCommand parse(String args) throws ParseException {
        return parse(args, new CustomerManager());
    }
}
