package seedu.hms.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.hms.testutil.TypicalCustomers.getTypicalBookings;
import static seedu.hms.testutil.TypicalIndexes.INDEX_SECOND_BOOKING;
import static seedu.hms.ui.testutil.GuiTestAssert.assertBookingCardDisplaysBooking;
import static seedu.hms.ui.testutil.GuiTestAssert.assertBookingCardEquals;

import org.junit.Test;

import guitests.guihandles.BookingCardHandle;
import guitests.guihandles.BookingListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.hms.model.booking.Booking;
import seedu.hms.testutil.BookingBuilder;

public class BookingListPanelTest extends GuiUnitTest {

    private static final ObservableList<Booking> TYPICAL_BOOKINGS =
            FXCollections.observableList(getTypicalBookings());


    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500; //was 2500 initly changed to 300 to pass

    private final SimpleObjectProperty<Booking> selectedBooking = new SimpleObjectProperty<>();
    private BookingListPanelHandle bookingListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_BOOKINGS);

        for (int i = 0; i < TYPICAL_BOOKINGS.size(); i++) {
            bookingListPanelHandle.navigateToCard(TYPICAL_BOOKINGS.get(i));
            Booking expectedBooking = TYPICAL_BOOKINGS.get(i);
            BookingCardHandle actualCard = bookingListPanelHandle.getBookingCardHandle(i);

            assertBookingCardDisplaysBooking(expectedBooking, actualCard);
            assertEquals((i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selectionModelSelectedBookingChangedSelectionChanges() {
        initUi(TYPICAL_BOOKINGS);
        Booking secondBooking = TYPICAL_BOOKINGS.get(INDEX_SECOND_BOOKING.getZeroBased());
        guiRobot.interact(() -> selectedBooking.set(secondBooking));
        guiRobot.pauseForHuman();
        BookingCardHandle expectedBooking =
                bookingListPanelHandle.getBookingCardHandle(INDEX_SECOND_BOOKING.getZeroBased());
        BookingCardHandle selectedBooking = bookingListPanelHandle.getHandleToSelectedCard();
        assertBookingCardEquals(expectedBooking, selectedBooking);
    }

    /**
     * Verifies that creating and deleting large number of bookings in {@code BookingListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Booking> backingList = createBackingList(1000000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of booking cards exceeded time limit");
    }

    /**
     * Returns a list of bookings containing {@code bookingCount} bookings that is used to populate the
     * {@code BookingListPanel}.
     */
    private ObservableList<Booking> createBackingList(int bookingCount) {
        ObservableList<Booking> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < bookingCount; i++) {
            Booking booking = new BookingBuilder().build();
            backingList.add(booking);
        }
        return backingList;
    }

    /**
     * Initializes {@code bookingListPanelHandle} with a {@code BookingListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code BookingListPanel}.
     */
    private void initUi(ObservableList<Booking> backingList) {
        BookingListPanel bookingListPanel =
                new BookingListPanel(backingList, selectedBooking, selectedBooking::set);
        uiPartRule.setUiPart(bookingListPanel);

        bookingListPanelHandle = new BookingListPanelHandle(getChildNode(bookingListPanel.getRoot(),
                BookingListPanelHandle.BOOKING_LIST_VIEW_ID));
    }
}
