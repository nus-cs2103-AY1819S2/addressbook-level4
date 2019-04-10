package seedu.hms.testutil;

import static seedu.hms.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CUSTOMERS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import seedu.hms.logic.commands.AddBookingCommand;
import seedu.hms.logic.commands.EditBookingCommand;
import seedu.hms.model.booking.Booking;

/**
 * A utility class for Bookings.
 */
public class BookingUtil {

    /**
     * Returns an add command string for adding the {@code booking}.
     */
    public static String getAddCommand(Booking booking) {
        return AddBookingCommand.COMMAND_WORD + " " + getBookingDetails(booking);
    }

    /**
     * Returns the part of command string for the given {@code booking}'s details.
     */
    public static String getBookingDetails(Booking booking) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_SERVICE + booking.getService().getName() + " ");
        sb.append(PREFIX_PAYER + "1" + " ");
        sb.append(PREFIX_TIMING + booking.getTiming().toString() + " ");
        sb.append(PREFIX_COMMENT + booking.getComment().toString() + " ");
        booking.getOtherUsers().stream().forEach(
            s -> sb.append(PREFIX_CUSTOMERS + s.get(0).toString()));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCustomerDescriptor}'s details.
     */
    public static String getEditBookingDescriptorDetails(EditBookingCommand.EditBookingDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getPayer().ifPresent(customer -> sb.append(PREFIX_PAYER).append(customer.getName()).append(" "));
        descriptor.getTiming().ifPresent(timing -> sb.append(PREFIX_TIMING).append(timing.toString()).append(" "));
        descriptor.getComment().ifPresent(comment -> sb.append(PREFIX_COMMENT).append(comment).append(" "));
        descriptor.getServiceType().ifPresent(service -> sb.append(PREFIX_SERVICE)
            .append(service.getName()).append(" "));
        return sb.toString();
    }
}
