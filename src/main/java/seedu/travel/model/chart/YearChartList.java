package seedu.travel.model.chart;

import static java.util.Objects.requireNonNull;
import static seedu.travel.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.travel.model.chart.exceptions.YearChartNotFoundException;
import seedu.travel.model.place.Place;

/**
 * Supports a minimal set of list operations for the rating data.
 *
 */
public class YearChartList implements Iterable<YearChart> {

    private final ObservableList<YearChart> internalList = FXCollections.observableArrayList();
    private final ObservableList<YearChart> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a rating to the list.
     */
    public void add(YearChart toAdd) {
        requireNonNull(toAdd);
        boolean isAdded = false;
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).getChartYear().equals(toAdd.getChartYear())) {
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
    public void remove(YearChart toRemove) {
        requireNonNull(toRemove);
        boolean isRemoved = false;
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).getChartYear().equals(toRemove.getChartYear())) {
                internalList.get(i).setTotal(internalList.get(i).getTotal() - 1);
                isRemoved = true;
                break;
            }
        }
        if (!isRemoved) {
            throw new YearChartNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code years}.
     */
    public void setYears(List<Place> places) {
        requireAllNonNull(places);
        internalList.removeAll();
        boolean isAdded;
        for (int i = 0; i < places.size(); i++) {
            Place place = places.get(i);
            isAdded = false;
            for (int j = 0; j < internalList.size(); j++) {
                YearChart country = internalList.get(j);
                if (place.getDateVisited().getYear().equals(country.getChartYear())) {
                    country.setTotal(internalList.get(j).getTotal() + 1);
                    isAdded = true;
                    break;
                }
            }
            if (!isAdded) {
                internalList.add(new YearChart(place.getDateVisited().getYear(), 1));
            }
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<YearChart> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<YearChart> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof YearChartList // instanceof handles nulls
                && internalList.equals(((YearChartList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
