package seedu.hms.model.booking;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.hms.model.customer.Customer;
import seedu.hms.model.util.TimeRange;

/**
 * Represents a service booking in the hotel system.
 */
public class Booking {

    private final Set<Customer> allUsers = new HashSet<>();
    private ServiceType service;
    private TimeRange timing;
    private Customer payer;
    private Optional<List<Customer>> otherUsers;
    private Optional<String> comment;

    public Booking(ServiceType service, TimeRange timing, Customer payer, Optional<List<Customer>> otherUsers,
                   Optional<String> comment) {
        this.service = service;
        this.timing = timing;
        this.payer = payer;
        this.otherUsers = otherUsers;
        this.comment = comment;
        this.allUsers.add(payer);
        if (otherUsers.isPresent()) {
            this.allUsers.addAll(otherUsers.get());
        }
    }

    public ServiceType getService() {
        return service;
    }

    public TimeRange getTiming() {
        return timing;
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
        String otherUsersToString = "";
        if (otherUsers.isPresent()) {
            for (Customer u : otherUsers.get()) {
                otherUsersToString += u.getName();
            }
        }
        return "Booking for "
            + service.getName()
            + " from " + timing
            + " by " + payer.getName()
            + " with " + otherUsersToString
            + ". Comment - " + comment.orElse("N/A");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Booking)) {
            return false;
        }

        Booking ob = (Booking) other;
        System.out.println(getOtherUsers());
        System.out.println(ob.getOtherUsers());
        return ob.getService().equals(getService())
                && ob.getTiming().equals(getTiming())
                && ob.getPayer().equals(getPayer())
                && ob.getOtherUsers().equals(getOtherUsers());
    }
}
