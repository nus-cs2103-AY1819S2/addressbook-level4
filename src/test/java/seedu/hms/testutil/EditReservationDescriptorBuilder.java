package seedu.hms.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;

import seedu.hms.logic.commands.EditReservationCommand;
import seedu.hms.logic.commands.EditReservationCommand.EditReservationDescriptor;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.model.util.DateRange;

/**
 * A utility class to help with building EditReservationDescriptor objects.
 */
public class EditReservationDescriptorBuilder {

    private EditReservationCommand.EditReservationDescriptor descriptor;

    public EditReservationDescriptorBuilder() {
        descriptor = new EditReservationDescriptor();
    }

    public EditReservationDescriptorBuilder(EditReservationDescriptor descriptor) {
        this.descriptor = new EditReservationDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditReservationDescriptor} with fields containing {@code Reservation}'s details
     */
    public EditReservationDescriptorBuilder(Reservation reservation) {
        descriptor = new EditReservationDescriptor();
        descriptor.setRoomType(reservation.getRoom());
        descriptor.setDates(reservation.getDates());
        descriptor.setPayer(reservation.getPayer());
        descriptor.setOtherUsers(reservation.getOtherUsers());
        descriptor.setComment(reservation.getComment());
    }

    /**
     * Sets the {@code Room} of the {@code EditReservationDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withRoom(RoomType st) {
        descriptor.setRoomType(st);
        return this;
    }

    /**
     * Sets the {@code Timing} of the {@code EditReservationDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withDates(String start, String end) {
        String[] sd = start.split("/");
        String[] ed = end.split("/");
        Calendar startDate = Calendar.getInstance();
        startDate.set(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]), Integer.parseInt(sd[0]));
        Calendar endDate = Calendar.getInstance();
        endDate.set(Integer.parseInt(ed[2]), Integer.parseInt(ed[1]), Integer.parseInt(ed[0]));

        descriptor.setDates(new DateRange(startDate, endDate));

        return this;
    }

    /**
     * Sets the {@code payer} of the {@code Reservation} that we are building.
     */
    public EditReservationDescriptorBuilder withPayer(Customer payer) {
        descriptor.setPayer(payer);
        return this;
    }

    /**
     * Clears the {@code otherUsers} of the {@code Reservation} that we are building.
     */
    public EditReservationDescriptorBuilder withOtherUsers() {
        descriptor.setOtherUsers(Optional.of(new ArrayList<>()));
        return this;
    }

    /**
     * Sets the {@code otherUsers} of the {@code Reservation} that we are building.
     */
    public EditReservationDescriptorBuilder withOtherUsers(Customer... otherUsers) {
        descriptor.setOtherUsers(Optional.of(Arrays.asList(otherUsers)));
        return this;
    }

    /**
     * Clears the {@code comment} of the {@code Reservation} that we are building.
     */
    public EditReservationDescriptorBuilder withComment() {
        descriptor.setComment(Optional.empty());
        return this;
    }

    /**
     * Sets the {@code comment} of the {@code Reservation} that we are building.
     */
    public EditReservationDescriptorBuilder withComment(String comment) {
        descriptor.setComment(Optional.of(comment));
        return this;
    }

    public EditReservationDescriptor build() {
        return descriptor;
    }
}
