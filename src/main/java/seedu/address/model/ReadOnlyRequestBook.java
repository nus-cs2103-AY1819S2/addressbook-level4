package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.request.Request;

/**
 * Unmodifiable view of a request book.
 */
public interface ReadOnlyRequestBook extends Observable {
    /**
     * @return an unmodifiable view of the request list. This list will not contain any duplicate requests.
     */
    ObservableList<Request> getRequestList();
}
