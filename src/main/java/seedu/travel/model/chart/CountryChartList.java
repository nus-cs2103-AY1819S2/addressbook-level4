package seedu.travel.model.chart;

import static java.util.Objects.requireNonNull;
import static seedu.travel.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.travel.model.chart.exceptions.CountryChartNotFoundException;
import seedu.travel.model.place.Place;

/**
 * Supports a minimal set of list operations for the country data.
 *
 */
public class CountryChartList implements Iterable<CountryChart> {

    public static final String MESSAGE_COUNTRY_CHART_NOT_FOUND = "The country chart does not exist.";

    private final ObservableList<CountryChart> internalList = FXCollections.observableArrayList();
    private final ObservableList<CountryChart> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a country to the list.
     */
    public void add(CountryChart toAdd) {
        requireNonNull(toAdd);
        boolean isAdded = false;
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).getChartCountryCode().equals(toAdd.getChartCountryCode())) {
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
     * Removes the equivalent countries from the list.
     * The country must exist in the list.
     */
    public void remove(CountryChart toRemove) {
        requireNonNull(toRemove);
        boolean isRemoved = false;
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).getChartCountryCode().equals(toRemove.getChartCountryCode())) {
                internalList.get(i).setTotal(internalList.get(i).getTotal() - 1);
                isRemoved = true;
                break;
            }
        }
        if (!isRemoved) {
            throw new CountryChartNotFoundException(MESSAGE_COUNTRY_CHART_NOT_FOUND);
        }
    }

    /**
     * Replaces the contents of this list with {@code countries}.
     */
    public void setCountries(List<Place> places) {
        requireAllNonNull(places);
        internalList.removeAll();
        boolean isAdded;
        for (int i = 0; i < places.size(); i++) {
            Place place = places.get(i);
            isAdded = false;
            for (int j = 0; j < internalList.size(); j++) {
                CountryChart country = internalList.get(j);
                if (place.getCountryCode().equals(country.getChartCountryCode())) {
                    country.setTotal(internalList.get(j).getTotal() + 1);
                    isAdded = true;
                    break;
                }
            }
            if (!isAdded) {
                internalList.add(new CountryChart(place.getCountryCode(), 1));
            }
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CountryChart> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<CountryChart> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CountryChartList // instanceof handles nulls
                && internalList.equals(((CountryChartList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
