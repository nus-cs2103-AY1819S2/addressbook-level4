package seedu.hms.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.util.TimeRange;

public class ServiceTypeListCardTest extends GuiUnitTest {

    @Test
    public void equals() {
        ServiceType serviceType = new ServiceType(50, new TimeRange(10, 22), "SPA", 10.0);
        ServiceTypeListCard serviceTypeCard = new ServiceTypeListCard(serviceType, 0);

        // same object -> returns true
        assertTrue(serviceTypeCard.equals(serviceTypeCard));

        // null -> returns false
        assertFalse(serviceTypeCard == null);

        // different types -> returns false
        assertFalse(serviceTypeCard.equals(0));

        // different serviceType, same index -> returns false
        ServiceType differentServiceType = new ServiceType(100, new TimeRange(10, 20), "GYM",
            20.0);
        assertFalse(serviceType.equals(new ServiceTypeListCard(differentServiceType, 0)));

        // same serviceType, different index -> returns false
        assertFalse(serviceTypeCard.equals(new ServiceTypeListCard(serviceType, 1)));
    }
}
