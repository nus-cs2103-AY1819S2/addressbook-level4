package seedu.finance.commons.events;

/**
 * An event requesting to view the Graph page.
 */
public class ShowSummaryRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}

