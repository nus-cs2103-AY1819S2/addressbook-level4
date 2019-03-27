package seedu.hms.testutil;

import static seedu.hms.testutil.TypicalCustomers.AMY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.util.TimeRange;

/**
 * builds a booking
 */
public class BookingBuilder {
    public static final String DEFAULT_SERVICE = "GYM";
    public static final String DEFAULT_TIMING = "14-16";
    public static final String DEFAULT_PAYER = "0";
    public static final String DEFAULT_OTHER_USERS = "1";
    public static final String DEFAULT_COMMENT = "dummycomment";

    private final Set<Customer> allUsers = new HashSet<>();
    private ServiceType service;
    private TimeRange timing;
    private Customer payer;
    private Optional<List<Customer>> otherUsers;
    private Optional<String> comment;

    public BookingBuilder() {
        this.service = ServiceType.GYM;
        this.timing = new TimeRange(14, 16);
        this.payer = AMY;
        this.otherUsers = Optional.of(new ArrayList<>());
        this.comment = Optional.of(DEFAULT_COMMENT);
        this.allUsers.add(payer);
        if (otherUsers.isPresent()) {
            this.allUsers.addAll(otherUsers.get());
        }
    }

    /**
     * Initializes the BookingBuilder with the data of {@code bookingToCopy}.
     */
    public BookingBuilder(Booking bookingToCopy) {
        this.service = bookingToCopy.getService();
        this.timing = bookingToCopy.getTiming();
        this.payer = bookingToCopy.getPayer();
        this.otherUsers = bookingToCopy.getOtherUsers();
        this.comment = bookingToCopy.getComment();
        this.allUsers.add(payer);
        if (otherUsers.isPresent()) {
            this.allUsers.addAll(otherUsers.get());
        }
    }

    /**
     * Sets the {@code service} of the {@code Booking} that we are building.
     */
    public BookingBuilder withService(ServiceType serviceType) {
        this.service = serviceType;
        return this;
    }

    /**
     * Sets the {@code timing} of the {@code Booking} that we are building.
     */
    public BookingBuilder withTiming(int start, int end) {
        this.timing = new TimeRange(start, end);
        return this;
    }

    /**
     * Sets the {@code payer} of the {@code Booking} that we are building.
     */
    public BookingBuilder withPayer(Customer payer) {
        this.payer = payer;
        return this;
    }

    /**
     * Clears the {@code otherUsers} of the {@code Booking} that we are building.
     */
    public BookingBuilder withOtherUsers() {
        this.otherUsers = Optional.of(new ArrayList<>());
        this.allUsers.clear();
        this.allUsers.add(this.payer);
        return this;
    }

    /**
     * Sets the {@code otherUsers} of the {@code Booking} that we are building.
     */
    public BookingBuilder withOtherUsers(Customer... otherUsers) {
        this.otherUsers = Optional.of(Arrays.asList(otherUsers));
        this.allUsers.clear();
        this.allUsers.add(this.payer);
        if (this.otherUsers.isPresent()) {
            this.allUsers.addAll(this.otherUsers.get());
        }
        return this;
    }

    /**
     * Clears the {@code comment} of the {@code Booking} that we are building.
     */
    public BookingBuilder withComment() {
        this.comment = Optional.empty();
        return this;
    }

    /**
     * Sets the {@code comment} of the {@code Booking} that we are building.
     */
    public BookingBuilder withComment(String comment) {
        this.comment = Optional.of(comment);
        return this;
    }

    /**
     * Build the booking.
     * @return a booking
     */
    public Booking build() {
        return new Booking(service, timing, payer, otherUsers, comment);
    }
}
