package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.ListItem;

/**
 * An interface that represents a ViewState that shows a list UI.
 */

public interface ListViewState<T extends ListItem> extends ViewState {
    void updateFilteredList(Predicate<T> predicate);

    ObservableList<T> getFilteredList();

    /**
     * Sets the selected Item in the filtered list.
     */
    void setSelectedItem(T item);

    /**
     * Returns the selected Item in the filtered list.
     * null if no card is selected.
     */
    T getSelectedItem();
}
