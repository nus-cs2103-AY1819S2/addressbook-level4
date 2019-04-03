package seedu.travel.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.travel.model.chart.YearChart;

/**
 * Unmodifiable view of the year chart book
 */
public interface ReadOnlyYearChart extends Observable {

    /**
     * Returns an unmodifiable view of the year list.
     */
    ObservableList<YearChart> getYearList();
}
