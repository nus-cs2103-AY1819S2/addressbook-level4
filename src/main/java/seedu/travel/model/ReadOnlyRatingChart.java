package seedu.travel.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.travel.model.chart.RatingChart;

/**
 * Unmodifiable view of the rating chart book
 */
public interface ReadOnlyRatingChart extends Observable {

    /**
     * Returns an unmodifiable view of the rating list.
     */
    ObservableList<RatingChart> getRatingList();
}
