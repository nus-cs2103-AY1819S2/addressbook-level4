package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CUSTOMERS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;
import static seedu.hms.model.Model.PREDICATE_SHOW_ALL_BOOKINGS;

import java.util.List;
import java.util.Optional;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.commons.util.CollectionUtil;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.BookingModel;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.ServiceType;
import seedu.hms.model.booking.exceptions.ServiceFullException;
import seedu.hms.model.booking.exceptions.ServiceUnavailableException;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.util.TimeRange;

/**
 * Edits a booking in the hotel management system
 */
public class EditBookingCommand extends BookingCommand {

    public static final String COMMAND_ALIAS = "eb";
    public static final String COMMAND_WORD = "edit-booking";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a booking to the hotel management system. "
        + "Parameters: BOOKING INDEX (to edit) "
        + "[" + PREFIX_SERVICE + "SERVICE NAME "
        + "[" + PREFIX_TIMING + "TIMING(HH - HH in 24 hour format)] "
        + "[" + PREFIX_PAYER + "PAYER INDEX]"
        + "[" + PREFIX_CUSTOMERS + "CUSTOMER INDEX(s)]... "
        + "[" + PREFIX_COMMENT + "COMMENT]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_SERVICE + "GYM "
        + PREFIX_PAYER + "2 "
        + PREFIX_CUSTOMERS + "1 "
        + PREFIX_COMMENT + "Edited second booking to add bookings and change payer and service\n";

    public static final String MESSAGE_EDIT_BOOKING_SUCCESS = "Booking edited: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_SERVICE_FULL = "The service has been booked fully during your requested hours";
    public static final String MESSAGE_SERVICE_UNAVAILABLE = "The service is not available during your requested hours";

    private final EditBookingDescriptor editBookingDescriptor;
    private final Index index;

    /**
     * Creates an EditBookingCommand to edit the booking at specified index
     */
    public EditBookingCommand(Index index, EditBookingDescriptor editBookingDescriptor) {
        requireNonNull(editBookingDescriptor);
        requireNonNull(index);

        this.editBookingDescriptor = editBookingDescriptor;
        this.index = index;
    }

    /**
     * Creates and returns a {@code Booking} with the details of {@code bookingToEdit}
     * edited with {@code editBookingDescriptor}.
     */
    private static Booking createEditedBooking(Booking bookingToEdit,
                                               EditBookingCommand.EditBookingDescriptor editBookingDescriptor) {
        assert bookingToEdit != null;

        ServiceType updatedServiceType = editBookingDescriptor.getServiceType().orElse(bookingToEdit.getService());
        TimeRange updatedTimeRange = editBookingDescriptor.getTiming().orElse(bookingToEdit.getTiming());
        Customer updatedPayer = editBookingDescriptor.getPayer().orElse(bookingToEdit.getPayer());
        Optional<List<Customer>> updatedOtherUsers =
            editBookingDescriptor.getOtherUsers().orElse(bookingToEdit.getOtherUsers());
        Optional<String> updatedComment = editBookingDescriptor.getComment().orElse(bookingToEdit.getComment());

        return new Booking(updatedServiceType, updatedTimeRange, updatedPayer, updatedOtherUsers, updatedComment);
    }

    @Override
    public CommandResult execute(BookingModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Booking> lastShownList = model.getFilteredBookingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
        }

        Booking bookingToEdit = lastShownList.get(index.getZeroBased());
        Booking editedBooking = createEditedBooking(bookingToEdit, editBookingDescriptor);

        try {
            model.setBooking(index.getZeroBased(), editedBooking);
        } catch (ServiceUnavailableException e) {
            return new CommandResult(MESSAGE_SERVICE_UNAVAILABLE);
        } catch (ServiceFullException e) {
            return new CommandResult(MESSAGE_SERVICE_FULL);
        }
        model.updateFilteredBookingList(PREDICATE_SHOW_ALL_BOOKINGS);
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_EDIT_BOOKING_SUCCESS, editedBooking));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBookingCommand)) {
            return false;
        }

        // state check
        EditBookingCommand e = (EditBookingCommand) other;
        return index.equals(e.index)
            && editBookingDescriptor.equals(e.editBookingDescriptor);
    }

    /**
     * Stores the details to edit the booking with. Each non-empty field value will replace the
     * corresponding field value of the booking.
     */
    public static class EditBookingDescriptor {
        private ServiceType serviceType;
        private TimeRange timing;
        private Customer payer;
        private Optional<List<Customer>> otherUsers;
        private Optional<String> comment;

        public EditBookingDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBookingDescriptor(EditBookingCommand.EditBookingDescriptor toCopy) {
            setServiceType(toCopy.serviceType);
            setTiming(toCopy.timing);
            setPayer(toCopy.payer);
            setOtherUsers(toCopy.otherUsers);
            setComment(toCopy.comment);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(serviceType, timing, payer, otherUsers, comment);
        }

        public Optional<ServiceType> getServiceType() {
            return Optional.ofNullable(serviceType);
        }

        public void setServiceType(ServiceType serviceType) {
            this.serviceType = serviceType;
        }

        public Optional<TimeRange> getTiming() {
            return Optional.ofNullable(timing);
        }

        public void setTiming(TimeRange timing) {
            this.timing = timing;
        }

        public Optional<Customer> getPayer() {
            return Optional.ofNullable(payer);
        }

        public void setPayer(Customer payer) {
            this.payer = payer;
        }

        public Optional<Optional<List<Customer>>> getOtherUsers() {
            return Optional.ofNullable(otherUsers);
        }

        public void setOtherUsers(Optional<List<Customer>> otherUsers) {
            this.otherUsers = otherUsers;
        }

        public Optional<Optional<String>> getComment() {
            return Optional.ofNullable(comment);
        }

        public void setComment(Optional<String> comment) {
            this.comment = comment;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBookingCommand.EditBookingDescriptor)) {
                return false;
            }

            // state check
            EditBookingCommand.EditBookingDescriptor e = (EditBookingCommand.EditBookingDescriptor) other;

            return getServiceType().equals(e.getServiceType())
                && getTiming().equals(e.getTiming())
                && getPayer().equals(e.getPayer())
                && getOtherUsers().equals(e.getOtherUsers())
                && getComment().equals(e.getComment());
        }
    }
}
