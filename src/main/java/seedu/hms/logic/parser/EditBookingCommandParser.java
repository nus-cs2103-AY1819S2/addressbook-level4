package seedu.hms.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CUSTOMERS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.EditBookingCommand;
import seedu.hms.logic.commands.EditBookingCommand.EditBookingDescriptor;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BookingManager;
import seedu.hms.model.BookingModel;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;

/**
 * Parses input arguments and creates a new EditBookingCommand object
 */
public class EditBookingCommandParser implements Parser<EditBookingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBookingCommand
     * and returns an EditBookingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBookingCommand parse(String args, CustomerModel customerModel, BookingModel bookingModel)
        throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_SERVICE, PREFIX_TIMING, PREFIX_PAYER,
                PREFIX_CUSTOMERS, PREFIX_COMMENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBookingCommand.MESSAGE_USAGE),
                pe);
        }

        EditBookingDescriptor editBookingDescriptor = new EditBookingDescriptor();
        if (argMultimap.getValue(PREFIX_SERVICE).isPresent()) {
            editBookingDescriptor.setServiceType(ParserUtil.parseService(argMultimap.getValue(PREFIX_SERVICE).get(),
                bookingModel));
        }
        if (argMultimap.getValue(PREFIX_TIMING).isPresent()) {
            editBookingDescriptor.setTiming(ParserUtil.parseTiming(argMultimap.getValue(PREFIX_TIMING).get()));
        }
        if (argMultimap.getValue(PREFIX_PAYER).isPresent()) {
            editBookingDescriptor.setPayer(ParserUtil.parseCustomer(argMultimap.getValue(PREFIX_PAYER).get(),
                customerModel.getFilteredCustomerList()));
        }
        if (argMultimap.getValue(PREFIX_CUSTOMERS).isPresent()) {
            editBookingDescriptor.setOtherUsers(ParserUtil.parseCustomers
                (argMultimap.getAllValues(PREFIX_CUSTOMERS), customerModel.getFilteredCustomerList()));
        }
        if (argMultimap.getValue(PREFIX_COMMENT).isPresent()) {
            editBookingDescriptor.setComment(argMultimap.getValue(PREFIX_COMMENT));
        }

        if (!editBookingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditBookingCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBookingCommand(index, editBookingDescriptor);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditBookingCommand
     * and returns an EditBookingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBookingCommand parse(String args) throws ParseException {
        return parse(args, new CustomerManager(), new BookingManager());
    }
}
