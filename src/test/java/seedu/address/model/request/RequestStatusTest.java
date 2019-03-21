package seedu.address.model.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RequestStatusTest {

    @Test
    void isOngoingStatus() {
        RequestStatus status = new RequestStatus("PENDING");
        assertFalse(status.isOngoingStatus());

        status = new RequestStatus("COMPLETED");
        assertFalse(status.isOngoingStatus());

        status = new RequestStatus("ONGOING");
        assertTrue(status.isOngoingStatus());
    }

    @Test
    void isCompletedStatus() {
        RequestStatus status = new RequestStatus("PENDING");
        assertFalse(status.isCompletedStatus());

        status = new RequestStatus("COMPLETED");
        assertTrue(status.isCompletedStatus());

        status = new RequestStatus("ONGOING");
        assertFalse(status.isCompletedStatus());
    }

    @Test
    void isValidStatus() {
        assertTrue(RequestStatus.isValidStatus("PENDING"));
        assertTrue(RequestStatus.isValidStatus("COMPLETED"));
        assertTrue(RequestStatus.isValidStatus("ONGOING"));
        assertFalse(RequestStatus.isValidStatus("O"));
        assertFalse(RequestStatus.isValidStatus("P"));
        assertFalse(RequestStatus.isValidStatus("C"));
        assertFalse(RequestStatus.isValidStatus("ongoing"));
        assertFalse(RequestStatus.isValidStatus("pending"));
        assertFalse(RequestStatus.isValidStatus("completed"));
        assertFalse(RequestStatus.isValidStatus("completed"));
    }

    @Test
    void equals() {
        RequestStatus pendingStatus = new RequestStatus("PENDING");
        RequestStatus pendingStatusCopy = new RequestStatus("PENDING");
        assertEquals(pendingStatus, pendingStatusCopy);

        RequestStatus ongoingStatus = new RequestStatus("ONGOING");
        assertNotEquals(pendingStatus, ongoingStatus);

        RequestStatus completedStatus = new RequestStatus("COMPLETED");
        assertNotEquals(pendingStatus, completedStatus);

        assertNotEquals(pendingStatus, "PENDING");
        assertNotEquals(completedStatus, "COMPLETED");
        assertNotEquals(ongoingStatus, "ONGOING");

        assertNotEquals(pendingStatus, null);
        assertNotEquals(completedStatus, null);
        assertNotEquals(ongoingStatus, null);
    }
}