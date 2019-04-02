package seedu.address.logic;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

/**
 * An interface that represents a ViewState that shows a list UI.
 */

public interface ListViewState<T extends ListItem> extends ViewState {
    void updateFilteredList(Predicate<T> predicate);

    ObservableList<T> getFilteredList();
}
