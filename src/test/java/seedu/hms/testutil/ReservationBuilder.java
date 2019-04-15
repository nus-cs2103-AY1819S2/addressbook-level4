package seedu.hms.testutil;

import static seedu.hms.testutil.TypicalCustomers.AMY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.model.util.DateRange;

/**
 * builds a reservation
 */
public class ReservationBuilder {
    public static final String DEFAULT_ROOM_TYPE = "SINGLE ROOM";
    public static final String DEFAULT_DATES = "14/05/2019 - 17/05/2019";
    public static final String DEFAULT_PAYER = "0";
    public static final String DEFAULT_OTHER_USERS = "1";
    public static final String DEFAULT_COMMENT = "dummycomment";

    private final Set<Customer> allUsers = new HashSet<>();
    private RoomType room;
    private DateRange dateRange;
    private Customer payer;
    private Optional<List<Customer>> otherUsers;
    private Optional<String> comment;

    public ReservationBuilder() {
        this.room = new RoomType(100, "Single Room", 500.0);
        this.dateRange = new DateRange(dateBuilder("14/10/2018"), dateBuilder("17/10/2018"));
        this.payer = AMY;
        this.otherUsers = Optional.of(new ArrayList<>());
        this.comment = Optional.of(DEFAULT_COMMENT);
        this.allUsers.add(payer);
        if (otherUsers.isPresent()) {
            this.allUsers.addAll(otherUsers.get());
        }
    }

    /**
     * Initializes the ReservationBuilder with the data of {@code reservationToCopy}.
     */
    public ReservationBuilder(Reservation reservationToCopy) {
        this.room = reservationToCopy.getRoom();
        this.dateRange = reservationToCopy.getDates();
        this.payer = reservationToCopy.getPayer();
        this.otherUsers = reservationToCopy.getOtherUsers();
        this.comment = reservationToCopy.getComment();
        this.allUsers.add(payer);
        if (otherUsers.isPresent()) {
            this.allUsers.addAll(otherUsers.get());
        }
    }

    /**
     * Sets the {@code room} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withRoom(RoomType roomType) {
        this.room = roomType;
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withDates(String start, String end) {
        Calendar startDate = dateBuilder(start);
        Calendar endDate = dateBuilder(end);
        this.dateRange = new DateRange(startDate, endDate);
        return this;
    }

    /**
     * Sets the {@code payer} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withPayer(Customer payer) {
        this.payer = payer;
        return this;
    }

    /**
     * Clears the {@code otherUsers} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withOtherUsers() {
        this.otherUsers = Optional.of(new ArrayList<>());
        this.allUsers.clear();
        this.allUsers.add(this.payer);
        return this;
    }

    /**
     * Sets the {@code otherUsers} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withOtherUsers(Customer... otherUsers) {
        this.otherUsers = Optional.of(Arrays.asList(otherUsers));
        this.allUsers.clear();
        this.allUsers.add(this.payer);
        if (this.otherUsers.isPresent()) {
            this.allUsers.addAll(this.otherUsers.get());
        }
        return this;
    }

    /**
     * Clears the {@code comment} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withComment() {
        this.comment = Optional.empty();
        return this;
    }

    /**
     * Sets the {@code comment} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withComment(String comment) {
        this.comment = Optional.of(comment);
        return this;
    }

    /**
     * Converts {@code date} of the {@code Reservation} from String type to Calendar type
     */
    private Calendar dateBuilder(String date) {
        Calendar dateCalendar = Calendar.getInstance();
        String[] sd = date.split("/");
        dateCalendar.set(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]), Integer.parseInt(sd[0]));
        return dateCalendar;
    }

    /**
     * Build the reservation.
     *
     * @return a reservation
     */
    public Reservation build() {
        return new Reservation(room, dateRange, payer, otherUsers, comment);
    }
}
