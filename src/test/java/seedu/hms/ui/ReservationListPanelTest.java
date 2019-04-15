package seedu.hms.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.hms.testutil.TypicalCustomers.getTypicalReservations;
import static seedu.hms.testutil.TypicalIndexes.INDEX_SECOND_RESERVATION;
import static seedu.hms.ui.testutil.GuiTestAssert.assertReservationCardDisplaysReservation;
import static seedu.hms.ui.testutil.GuiTestAssert.assertReservationCardEquals;

import org.junit.Test;

import guitests.guihandles.ReservationCardHandle;
import guitests.guihandles.ReservationListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.testutil.ReservationBuilder;

public class ReservationListPanelTest extends GuiUnitTest {

    private static final ObservableList<Reservation> TYPICAL_RESERVATIONS =
            FXCollections.observableList(getTypicalReservations());


    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500; //was 2500 initly changed to 300 to pass

    private final SimpleObjectProperty<Reservation> selectedReservation = new SimpleObjectProperty<>();
    private ReservationListPanelHandle reservationListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_RESERVATIONS);

        for (int i = 0; i < TYPICAL_RESERVATIONS.size(); i++) {
            reservationListPanelHandle.navigateToCard(TYPICAL_RESERVATIONS.get(i));
            Reservation expectedReservation = TYPICAL_RESERVATIONS.get(i);
            ReservationCardHandle actualCard = reservationListPanelHandle.getReservationCardHandle(i);

            assertReservationCardDisplaysReservation(expectedReservation, actualCard);
            assertEquals((i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selectionModelSelectedReservationChangedSelectionChanges() {
        initUi(TYPICAL_RESERVATIONS);
        Reservation secondReservation = TYPICAL_RESERVATIONS.get(INDEX_SECOND_RESERVATION.getZeroBased());
        guiRobot.interact(() -> selectedReservation.set(secondReservation));
        guiRobot.pauseForHuman();
        ReservationCardHandle expectedReservation =
                reservationListPanelHandle.getReservationCardHandle(INDEX_SECOND_RESERVATION.getZeroBased());
        ReservationCardHandle selectedReservation = reservationListPanelHandle.getHandleToSelectedCard();
        assertReservationCardEquals(expectedReservation, selectedReservation);
    }

    /**
     * Verifies that creating and deleting large number of reservations in {@code ReservationListPanel} requires lesser
     * than {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Reservation> backingList = createBackingList(100000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of reservation cards exceeded time limit");
    }

    /**
     * Returns a list of reservations containing {@code reservationCount} reservations that is used to populate the
     * {@code ReservationListPanel}.
     */
    private ObservableList<Reservation> createBackingList(int reservationCount) {
        ObservableList<Reservation> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < reservationCount; i++) {
            Reservation reservation = new ReservationBuilder().build();
            backingList.add(reservation);
        }
        return backingList;
    }

    /**
     * Initializes {@code reservationListPanelHandle} with a {@code ReservationListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code ReservationListPanel}.
     */
    private void initUi(ObservableList<Reservation> backingList) {
        ReservationListPanel reservationListPanel =
                new ReservationListPanel(backingList, selectedReservation, selectedReservation::set);
        uiPartRule.setUiPart(reservationListPanel);

        reservationListPanelHandle = new ReservationListPanelHandle(getChildNode(reservationListPanel.getRoot(),
                ReservationListPanelHandle.RESERVATION_LIST_VIEW_ID));
    }
}
