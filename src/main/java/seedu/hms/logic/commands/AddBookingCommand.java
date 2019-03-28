package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CUSTOMERS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.BookingModel;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.exceptions.ServiceFullException;
import seedu.hms.model.booking.exceptions.ServiceUnavailableException;

/**
 * Adds a booking to the hms book.
 */
public class AddBookingCommand extends BookingCommand {

    public static final String COMMAND_ALIAS = "ab";
    public static final String COMMAND_WORD = "add-booking";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a booking to the hotel management system. "
        + "Parameters: "
        + PREFIX_SERVICE + "SERVICE NAME "
        + PREFIX_TIMING + "TIMING(HH - HH in 24 hour format) "
        + PREFIX_PAYER + "PAYER INDEX "
        + "[" + PREFIX_CUSTOMERS + "CUSTOMER INDEX]... "
        + "[" + PREFIX_COMMENT + "COMMENT]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_SERVICE + "GYM "
        + PREFIX_TIMING + "08 - 13 "
        + PREFIX_PAYER + "2 "
        + PREFIX_CUSTOMERS + "1 "
        + PREFIX_COMMENT + "Please turn on the AC during the booked hours.\n";

    public static final String MESSAGE_SUCCESS = "New booking added: %1$s";
    public static final String MESSAGE_SERVICE_FULL = "The service has been booked fully during your requested hours";
    public static final String MESSAGE_SERVICE_UNAVAILABLE = "The service is not available during your requested hours";

    private final Booking toAdd;

    /**
     * Creates an AddBookingCommand to add the specified {@code Booking}
     */
    public AddBookingCommand(Booking booking) {
        requireNonNull(booking);
        toAdd = booking;
    }

    @Override
    public CommandResult execute(BookingModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            model.addBooking(toAdd);
        } catch (ServiceUnavailableException e) {
            return new CommandResult(MESSAGE_SERVICE_UNAVAILABLE);
        } catch (ServiceFullException e) {
            return new CommandResult(MESSAGE_SERVICE_FULL);
        }
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddBookingCommand // instanceof handles nulls
            && toAdd.equals(((AddBookingCommand) other).toAdd));
    }
}
