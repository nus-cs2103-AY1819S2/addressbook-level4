package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;

import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.request.Request;
import seedu.address.model.request.UniqueRequestList;

/**
 * Wraps all data at the request-book level
 * Duplicates are not allowed (by .isSameRequest comparison)
 */
public class RequestBook implements ReadOnlyRequestBook {

    private final UniqueRequestList requests = new UniqueRequestList();
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    public RequestBook() {}

    /**
     * Creates a RequestBook using the Requests in the {@code toBeCopied}
     */
    public RequestBook(ReadOnlyRequestBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the request list with {@code requests}.
     * {@code requests} must not contain duplicate requests.
     */
    public void setRequests(List<Request> requests) {
        this.requests.setRequests(requests);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code RequestBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRequestBook newData) {
        requireNonNull(newData);
        setRequests(newData.getRequestList());
    }

    //// request-level operations

    /**
     * Returns true if a request with the same identity as {@code req} exists in the request book.
     */
    public boolean hasRequest(Request req) {
        requireNonNull(req);
        return requests.contains(req);
    }

    /**
     * Returns true if this book has the same request identities of the requests in {@code other}
     */
    public boolean areRequestsSame(RequestBook other) {
        return this.requests.areRequestsSame(other.requests);
    }

    /**
     * Adds a request to the request book.
     * The request must not already exist in the request book.
     */
    public void addRequest(Request r) {
        requests.add(r);
        indicateModified();
    }

    /**
     * Replaces the given request {@code target} in the list with {@code editedRequest}.
     * {@code target} must exist in the request book.
     * The request identity of {@code editedRequest} must not be the same as another existing
     * request in the request book.
     */
    public void setRequest(Request target, Request editedRequest) {
        requireNonNull(editedRequest);

        requests.setRequest(target, editedRequest);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code RequestBook}.
     * {@code key} must exist in the request book.
     */
    public void removeRequest(Request key) {
        requests.remove(key);
        indicateModified();
    }

    /**
     * @return an unmodifiable view of the request list. This list will not contain any duplicate requests.
     */
    @Override
    public ObservableList<Request> getRequestList() {
        return requests.asUnmodifiableObservableList();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the request book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return requests.asUnmodifiableObservableList().toString();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RequestBook // instanceof handles nulls
                && requests.equals(((RequestBook) other).requests));
    }

    @Override
    public int hashCode() {
        return requests.hashCode();
    }
}
