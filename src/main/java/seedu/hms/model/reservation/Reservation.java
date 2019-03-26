package seedu.hms.model.reservation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.hms.model.booking.RoomType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.util.DateRange;

/**
 * Represents a room reservation in the hotel system.
 */
public class Reservation {

    private final Set<Customer> allUsers = new HashSet<>();
    private RoomType room;
    private DateRange dates;
    private Customer payer;
    private Optional<List<Customer>> otherUsers;
    private Optional<String> comment;

    public Reservation(RoomType room, DateRange dates, Customer payer, Optional<List<Customer>> otherUsers,
                   Optional<String> comment) {
        this.room = room;
        this.dates = dates;
        this.payer = payer;
        this.otherUsers = otherUsers;
        this.comment = comment;
        this.allUsers.add(payer);
        if (otherUsers.isPresent()) {
            this.allUsers.addAll(otherUsers.get());
        }
    }

    public RoomType getRoom() {
        return room;
    }

    public DateRange getDates() {
        return dates;
    }

    public Customer getPayer() {
        return payer;
    }

    public Optional<List<Customer>> getOtherUsers() {
        return otherUsers;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Customer> getAllusers() {
        return Collections.unmodifiableSet(allUsers);
    }

    public Optional<String> getComment() {
        return comment;
    }

    public int numOfCustomers() {
        return 1 + otherUsers.map(Collection::size).orElse(0);
    }

    @Override
    public String toString() {
        return "Reservation for "
            + room.getName()
            + " from " + dates
            + " by " + payer.getName()
            + ". Comment - " + comment.orElse("N/A");
    }
}
