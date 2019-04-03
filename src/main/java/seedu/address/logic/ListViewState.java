package seedu.address.logic;

import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

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

    ReadOnlyProperty<T> getSelectedItemProperty();
}
