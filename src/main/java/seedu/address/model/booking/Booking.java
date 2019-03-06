package seedu.address.model.booking;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.customer.Customer;
import seedu.address.model.util.TimeRange;

/**
 * Represents a service booking in the hotel system.
 */
public class Booking {

    private ServiceType service;
    private TimeRange timing;
    private Customer payer;
    private Optional<List<Customer>> otherUsers;
    private Optional<String> comment;

    private final Set<Customer> allUsers = new HashSet<>();

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
        return "Booking for "
            + service
            + " from " + timing
            + " by " + payer
            + ". Comment - " + comment;
    }
}
