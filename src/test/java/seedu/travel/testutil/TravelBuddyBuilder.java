package seedu.travel.testutil;

import seedu.travel.model.TravelBuddy;
import seedu.travel.model.place.Place;

/**
 * A utility class to help with building TravelBuddy objects.
 * Example usage: <br>
 *     {@code TravelBuddy ab = new TravelBuddyBuilder().withPlace("John", "Doe").build();}
 */
public class TravelBuddyBuilder {

    private TravelBuddy travelBuddy;

    public TravelBuddyBuilder() {
        travelBuddy = new TravelBuddy();
    }

    public TravelBuddyBuilder(TravelBuddy travelBuddy) {
        this.travelBuddy = travelBuddy;
    }

    /**
     * Adds a new {@code Place} to the {@code TravelBuddy} that we are building.
     */
    public TravelBuddyBuilder withPlace(Place place) {
        travelBuddy.addPlace(place);
        return this;
    }

    public TravelBuddy build() {
        return travelBuddy;
    }
}
