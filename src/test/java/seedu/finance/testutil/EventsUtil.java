package seedu.finance.testutil;

import guitests.GuiRobot;
import seedu.finance.commons.core.EventsCenter;
import seedu.finance.commons.events.BaseEvent;

/**
 * Helper methods related to events.
 */
public class EventsUtil {
    /**
     * Posts {@code event} to all registered subscribers. This method will return successfully after the {@code event}
     * has been posted to all subscribers.
     */
    public static void postNow(BaseEvent event) {
        new GuiRobot().interact(() -> EventsCenter.getInstance().post(event));
    }

}
