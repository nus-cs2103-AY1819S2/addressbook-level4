package systemtests;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.ListItem;
import seedu.address.logic.ListViewState;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<ListItem> PREDICATE_MATCHING_NO_CARDS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(ListViewState<ListItem> viewState, List<ListItem> toDisplay) {
        Optional<Predicate<ListItem>> predicate = toDisplay.stream().map(ModelHelper::getPredicateMatching)
                                                           .reduce(Predicate::or);
        viewState.updateFilteredList(predicate.orElse(PREDICATE_MATCHING_NO_CARDS));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ListItem} equals to {@code other}.
     *
     * @param other
     */
    private static Predicate<ListItem> getPredicateMatching(ListItem other) {
        return item -> item.equals(other);
    }
}
