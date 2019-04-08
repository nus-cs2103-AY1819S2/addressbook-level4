package seedu.finance.commons.events;

//import java.util.ArrayList;

/**
 * An event requesting to view the Graph page.
 */
public class ShowSummaryRequestEvent extends BaseEvent {

    /*Assuming we are using Arraylist to store records
    // KIV may have to edit accordingly once Budget class is implemented
    private final ArrayList<Integer> recordsList;

    public ShowSummaryRequestEvent(ArrayList<Integer> recordsList) {

        this.recordsList = recordsList;
    }*/

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    //public ArrayList<Integer> getRecordsList() { return recordsList;  }
}

