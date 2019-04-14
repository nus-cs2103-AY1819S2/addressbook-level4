package seedu.hms.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.hms.model.reservation.roomType.RoomType;

public class RoomTypeListCardTest extends GuiUnitTest {

    @Test
    public void equals() {
        RoomType roomType = new RoomType(50, "SINGLE ROOM", 10.0);
        RoomTypeListCard roomTypeCard = new RoomTypeListCard(roomType, 0);

        // same object -> returns true
        assertTrue(roomTypeCard.equals(roomTypeCard));

        // null -> returns false
        assertFalse(roomTypeCard == null);

        // different types -> returns false
        assertFalse(roomTypeCard.equals(0));

        // different roomType, same index -> returns false
        RoomType differentRoomType = new RoomType(100, "DOUBLE ROOM",
            20.0);
        assertFalse(roomType.equals(new RoomTypeListCard(differentRoomType, 0)));

        // same roomType, different index -> returns false
        assertFalse(roomTypeCard.equals(new RoomTypeListCard(roomType, 1)));
    }
}
