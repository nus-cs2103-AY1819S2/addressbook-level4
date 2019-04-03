package seedu.travel.model;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.travel.commons.util.InvalidationListenerManager;
import seedu.travel.model.chart.CountryChart;
import seedu.travel.model.chart.CountryChartList;
import seedu.travel.model.chart.RatingChart;
import seedu.travel.model.chart.RatingChartList;
import seedu.travel.model.chart.YearChart;
import seedu.travel.model.chart.YearChartList;
import seedu.travel.model.place.Place;

/**
 * Wraps all data at the chart-book level
 */
public class ChartBook implements ReadOnlyCountryChart, ReadOnlyRatingChart, ReadOnlyYearChart {

    private final CountryChartList countries;
    private final RatingChartList ratings;
    private final YearChartList years;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    {
        countries = new CountryChartList();
        ratings = new RatingChartList();
        years = new YearChartList();
    }

    public ChartBook() {}

    public ChartBook(ObservableList<Place> placeList) {
        this();
        requireNonNull(placeList);
        setCountries(placeList);
        setRatings(placeList);
        setYears(placeList);
    }

    //// list overwrite operations

    public void setCountries(ObservableList<Place> placeList) {
        this.countries.setCountries(placeList);
        indicateModified();
    }

    public void setRatings(ObservableList<Place> placeList) {
        this.ratings.setRatings(placeList);
        indicateModified();
    }

    public void setYears(ObservableList<Place> placeList) {
        this.years.setYears(placeList);
        indicateModified();
    }

    //// chart book level operations

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the travel book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return countries.asUnmodifiableObservableList().size() + " countries, "
                + ratings.asUnmodifiableObservableList().size() + " ratings, "
                + years.asUnmodifiableObservableList().size() + " years";
    }

    @Override
    public ObservableList<CountryChart> getCountryList() {
        return countries.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<RatingChart> getRatingList() {
        return ratings.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<YearChart> getYearList() {
        return years.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChartBook && countries.equals(((ChartBook) other).countries))
                || (other instanceof ChartBook && ratings.equals(((ChartBook) other).ratings))
                || (other instanceof ChartBook && years.equals(((ChartBook) other).years));
    }

    @Override
    public int hashCode() {
        return Objects.hash(countries, ratings, years);
    }
}

