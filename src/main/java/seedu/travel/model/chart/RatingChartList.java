package seedu.travel.model.chart;

import static java.util.Objects.requireNonNull;
import static seedu.travel.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.travel.model.chart.exceptions.RatingChartNotFoundException;
import seedu.travel.model.place.Place;

/**
 * Supports a minimal set of list operations for the rating data.
 *
 */
public class RatingChartList implements Iterable<RatingChart> {

    private final ObservableList<RatingChart> internalList = FXCollections.observableArrayList();
    private final ObservableList<RatingChart> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a rating to the list.
     */
    public void add(RatingChart toAdd) {
        requireNonNull(toAdd);
        boolean isAdded = false;
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).getChartRating().equals(toAdd.getChartRating())) {
                internalList.get(i).setTotal(internalList.get(i).getTotal() + 1);
                isAdded = true;
                break;
            }
        }
        if (!isAdded) {
            internalList.add(toAdd);
        }
    }

    /**
     * Removes the equivalent ratings from the list.
     * The rating must exist in the list.
     */
    public void remove(RatingChart toRemove) {
        requireNonNull(toRemove);
        boolean isRemoved = false;
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).getChartRating().equals(toRemove.getChartRating())) {
                internalList.get(i).setTotal(internalList.get(i).getTotal() - 1);
                isRemoved = true;
                break;
            }
        }
        if (!isRemoved) {
            throw new RatingChartNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code ratings}.
     */
    public void setRatings(List<Place> places) {
        requireAllNonNull(places);
        internalList.removeAll();
        boolean isAdded;
        for (int i = 0; i < places.size(); i++) {
            Place place = places.get(i);
            isAdded = false;
            for (int j = 0; j < internalList.size(); j++) {
                RatingChart rating = internalList.get(j);
                if (place.getRating().equals(rating.getChartRating())) {
                    rating.setTotal(internalList.get(j).getTotal() + 1);
                    isAdded = true;
                    break;
                }
            }
            if (!isAdded) {
                internalList.add(new RatingChart(place.getRating(), 1));
            }
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<RatingChart> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<RatingChart> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RatingChartList // instanceof handles nulls
                && internalList.equals(((RatingChartList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
