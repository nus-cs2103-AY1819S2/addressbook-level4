package seedu.address.model.request;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @@author daviddl9
 */
class RequestDateTest {

    @Test
    void isValidDate() {
        assertTrue(RequestDate.isValidDate("30-12-2019 10:00:00"));
        assertFalse(RequestDate.isValidDate("This Friday"));
        assertFalse(RequestDate.isValidDate("30/12/2019 10:00:00"));
        assertFalse(RequestDate.isValidDate("30-12-2019 10:00"));
        assertFalse(RequestDate.isValidDate("30.12.2019 10:00:00"));
        assertFalse(RequestDate.isValidDate("30.12.2019 10'o'clock"));
        assertFalse(RequestDate.isValidDate("30.12.2019 1000"));
    }
}
