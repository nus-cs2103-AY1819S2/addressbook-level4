package seedu.hms.testutil;

import static seedu.hms.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.model.util.TimeRange;

/**
 * A utility class containing a list of {@code Customer} objects to be used in tests.
 */
public class TypicalCustomers {

    public static final Customer ALICE = new CustomerBuilder().withName("Alice Pauline")
        .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
        .withPhone("94351253").withIdNum("1231212A").withDateOfBirth("30/12/1999")
        .withTags("friends").build();
    public static final Customer BENSON = new CustomerBuilder().withName("Benson Meier")
        .withAddress("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432").withIdNum("1233512A").withDateOfBirth("30/12/2000")
        .withTags("owesMoney", "friends").build();
    public static final Customer CARL = new CustomerBuilder().withName("Carl Kurz").withPhone("95352563")
        .withEmail("heinz@example.com").withAddress("wall street").withIdNum("10453512A").withDateOfBirth("30/12/2001")
        .build();
    public static final Customer ELLE = new CustomerBuilder().withName("Elle Meyer").withPhone("9482224")
        .withDateOfBirth("30/12/2003").withEmail("werner@example.com").withAddress("michegan ave")
        .withIdNum("12330012A").build();
    public static final Customer FIONA = new CustomerBuilder().withName("Fiona Kunz").withPhone("9482427")
        .withEmail("lydia@example.com").withAddress("little tokyo").withIdNum("1009512A").withDateOfBirth("30/12/2004")
        .build();
    public static final Customer VIP_CUSTOMER =
        new CustomerBuilder().withName("I am VIP").withPhone("94824425").withDateOfBirth("30/12/2006")
            .withEmail("vip@example.com").withAddress("VIP street").withIdNum("2536A163")
            .withTags("VIP").build();
    public static final Customer VIP_CUSTOMER2 =
        new CustomerBuilder().withName("I am also VIP").withPhone("34824425").withDateOfBirth("30/12/2007")
            .withEmail("vip2@example.com").withAddress("VVIP street").withIdNum("5352A525")
            .withTags("vip").build();
    // Manually added
    public static final Customer HOON = new CustomerBuilder().withName("Hoon Meier").withPhone("8482424")
        .withDateOfBirth("30/12/2008").withEmail("stefan@example.com").withIdNum("A4563631Q")
        .withAddress("little india").build();
    public static final Customer IDA = new CustomerBuilder().withName("Ida Mueller").withPhone("8482131")
        .withDateOfBirth("30/12/2009").withEmail("hans@example.com").withIdNum("A1113342T")
        .withAddress("chicago ave").build();
    // Manually added - Customer's details found in {@code CommandTestUtil}
    public static final Customer AMY =
        new seedu.hms.testutil.CustomerBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withIdNum(VALID_ID_AMY).withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND).withDateOfBirth(VALID_DATE_OF_BIRTH_AMY).build();
    public static final Customer BOB =
        new seedu.hms.testutil.CustomerBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withIdNum(VALID_ID_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    //Bookings
    public static final ServiceType GYM = new ServiceType(50, new TimeRange(8, 22), "Gym", 7.0);
    public static final ServiceType GAMES = new ServiceType(20, new TimeRange(10, 22), "Games Room", 5.0);

    public static final Booking ALICE_GYM =
        new BookingBuilder().withService(GYM).withTiming(14, 15).withPayer(ALICE).build();
    public static final Booking ALICE_GAMES_WITH_CARL =
        new BookingBuilder().withService(GAMES).withTiming(12, 13).withPayer(ALICE).withOtherUsers(CARL).build();

    //Reservations
    public static final RoomType SINGLE = new RoomType(100, "Single Room", 500.0);
    public static final RoomType DOUBLE = new RoomType(75, "Double Room", 750.0);

    public static final Reservation ALICE_SINGLE_ROOM =
        new ReservationBuilder().withRoom(SINGLE).withDates("18/10/2019", "20/10/2019").withPayer(ALICE)
            .build();
    public static final Reservation BENSON_DOUBLE_ROOM =
        new ReservationBuilder().withRoom(DOUBLE).withDates("18/10/2019", "20/10/2010").withPayer(BENSON)
            .build();

    public static final Customer DANIEL =
        new CustomerBuilder().withName("Daniel Meier").withPhone("87652533").withDateOfBirth("30/12/2002")
            .withEmail("cornelia@example.com").withAddress("10th street").withIdNum("1200512A")
            .withTags("friends").build();
    public static final Customer GEORGE = new CustomerBuilder().withName("George Best").withPhone("9482442")
        .withEmail("anna@example.com").withAddress("4th street").withIdNum("1233912A").withDateOfBirth("30/12/2005")
        .build();

    private TypicalCustomers() {
    } // prevents instantiation

    /**
     * Returns an {@code HotelManagementSystem} with all the typical customers.
     */
    public static HotelManagementSystem getTypicalHotelManagementSystem() {
        HotelManagementSystem ab = new HotelManagementSystem();
        for (Customer customer : getTypicalCustomers()) {
            ab.addCustomer(customer);
        }
        for (ServiceType st : getTypicalServiceTypes()) {
            ab.addServiceType(st);
        }
        for (RoomType rt : getTypicalRoomTypes()) {
            ab.addRoomType(rt);
        }
        for (Booking booking : getTypicalBookings()) {
            ab.addBooking(booking);
        }
        for (Reservation reservation : getTypicalReservations()) {
            ab.addReservation(reservation);
        }

        return ab;
    }

    public static List<Customer> getTypicalCustomers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Booking> getTypicalBookings() {
        return new ArrayList<>(Arrays.asList(ALICE_GYM, ALICE_GAMES_WITH_CARL));
    }

    public static List<RoomType> getTypicalRoomTypes() {
        return new ArrayList<>(Arrays.asList(SINGLE, DOUBLE));
    }

    public static List<ServiceType> getTypicalServiceTypes() {
        return new ArrayList<>(Arrays.asList(GYM, GAMES));
    }

    public static List<Reservation> getTypicalReservations() {
        return new ArrayList<>(Arrays.asList(ALICE_SINGLE_ROOM, BENSON_DOUBLE_ROOM));
    }
}
