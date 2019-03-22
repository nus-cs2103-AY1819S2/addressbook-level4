package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.travel.model.Model;
import seedu.travel.model.place.Place;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Place> PREDICATE_MATCHING_NO_PLACES = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Place> toDisplay) {
        Optional<Predicate<Place>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPlaceList(predicate.orElse(PREDICATE_MATCHING_NO_PLACES));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Place... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Place} equals to {@code other}.
     */
    private static Predicate<Place> getPredicateMatching(Place other) {
        return place -> place.equals(other);
    }
}
