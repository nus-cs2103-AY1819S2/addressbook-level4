package seedu.travel.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.travel.model.chart.CountryChart;

/**
 * Unmodifiable view of the country chart book
 */
public interface ReadOnlyCountryChart extends Observable {

    /**
     * Returns an unmodifiable view of the country list.
     */
    ObservableList<CountryChart> getCountryList();
}
