package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.ListItem;

/**
 * A ListViewState is a ViewState with a user interface containing a list that fulfills the following properties:
 * 1. The list should be homogeneous, holding items of type {@code T}
 * 2. The items in the list can be arbitrarily concealed and revealed.
 * 3. Up to one item in the list can be selected by the user.
 */

public interface ListViewState<T extends ListItem> extends ViewState {
    /**
     * Updates the filtered list by keeping elements that satisfy {@code predicate}.
     */
    void updateFilteredList(Predicate<T> predicate);

    /**
     * Returns the FilteredList as an ObservableList
     */
    ObservableList<T> getFilteredList();

    /**
     * Sets the selected item in the filtered list.
     */
    void setSelectedItem(T item);

    /**
     * Returns the selected item in the filtered list.
     * null if no item is selected.
     */
    T getSelectedItem();
}
