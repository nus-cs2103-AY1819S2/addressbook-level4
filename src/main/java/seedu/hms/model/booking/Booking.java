package seedu.hms.model.booking;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.hms.model.booking.serviceType.ServiceType;
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
        otherUsers.ifPresent(l -> this.allUsers.addAll(l));
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
     * Returns an immutable user set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Customer> getAllusers() {
        return Collections.unmodifiableSet(allUsers);
    }

    public Optional<String> getComment() {
        return comment;
    }

    /**
     * Returns the number of customers in a booking
     */
    public int numOfCustomers() {
        return allUsers.size();
    }

    public boolean isCustomerInOtherUsers(Customer key) {
        return this.otherUsers.filter(list -> list.indexOf(key) != -1).isPresent();
    }

    /**
     * Removes a customer from the other users
     * @param key is the customer to be removed from other users.
     */
    public void removeCustomerFromOtherUsers(Customer key) {
        this.otherUsers.ifPresent(list -> list.remove(key));
    }

    @Override
    public String toString() {
        String usersToString = "";
        for (Customer u : this.allUsers) {
            usersToString += u.getName() + ", ";
        }
        return "Booking for "
            + service.getName()
            + " from " + timing
            + " for " + usersToString.substring(0, usersToString.length() - 2)
            + " paid by " + payer.getName()
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
        return ob.getService().equals(getService())
                && ob.getTiming().equals(getTiming())
                && ob.getPayer().equals(getPayer())
                && ob.getOtherUsers().equals(getOtherUsers());
    }
}
