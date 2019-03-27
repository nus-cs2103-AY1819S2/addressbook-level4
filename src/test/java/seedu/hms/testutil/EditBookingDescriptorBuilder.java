package seedu.hms.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import seedu.hms.logic.commands.EditBookingCommand;
import seedu.hms.logic.commands.EditBookingCommand.EditBookingDescriptor;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.util.TimeRange;

/**
 * A utility class to help with building EditBookingDescriptor objects.
 */
public class EditBookingDescriptorBuilder {

    private EditBookingCommand.EditBookingDescriptor descriptor;

    public EditBookingDescriptorBuilder() {
        descriptor = new EditBookingDescriptor();
    }

    public EditBookingDescriptorBuilder(EditBookingDescriptor descriptor) {
        this.descriptor = new EditBookingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBookingDescriptor} with fields containing {@code Booking}'s details
     */
    public EditBookingDescriptorBuilder(Booking booking) {
        descriptor = new EditBookingDescriptor();
        descriptor.setServiceType(booking.getService());
        descriptor.setTiming(booking.getTiming());
        descriptor.setPayer(booking.getPayer());
        descriptor.setOtherUsers(booking.getOtherUsers());
        descriptor.setComment(booking.getComment());
    }

    /**
     * Sets the {@code Service} of the {@code EditBookingDescriptor} that we are building.
     */
    public EditBookingDescriptorBuilder withService(ServiceType st) {
        descriptor.setServiceType(st);
        return this;
    }

    /**
     * Sets the {@code Timing} of the {@code EditBookingDescriptor} that we are building.
     */
    public EditBookingDescriptorBuilder withTiming(int start, int end) {
        descriptor.setTiming(new TimeRange(start, end));
        return this;
    }

    /**
     * Sets the {@code payer} of the {@code Booking} that we are building.
     */
    public EditBookingDescriptorBuilder withPayer(Customer payer) {
        descriptor.setPayer(payer);
        return this;
    }

    /**
     * Clears the {@code otherUsers} of the {@code Booking} that we are building.
     */
    public EditBookingDescriptorBuilder withOtherUsers() {
        descriptor.setOtherUsers(Optional.of(new ArrayList<>()));
        return this;
    }

    /**
     * Sets the {@code otherUsers} of the {@code Booking} that we are building.
     */
    public EditBookingDescriptorBuilder withOtherUsers(Customer... otherUsers) {
        descriptor.setOtherUsers(Optional.of(Arrays.asList(otherUsers)));
        return this;
    }

    /**
     * Clears the {@code comment} of the {@code Booking} that we are building.
     */
    public EditBookingDescriptorBuilder withComment() {
        descriptor.setComment(Optional.empty());
        return this;
    }

    /**
     * Sets the {@code comment} of the {@code Booking} that we are building.
     */
    public EditBookingDescriptorBuilder withComment(String comment) {
        descriptor.setComment(Optional.of(comment));
        return this;
    }

    public EditBookingDescriptor build() {
        return descriptor;
    }
}
