package seedu.hms.model.reservation;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of ServiceTypes
 * <p>
 * Supports a minimal set of list operations.
 */
public class RoomTypeList implements Iterable<RoomType> {

    private final ObservableList<RoomType> internalList = FXCollections.observableArrayList();
    private final ObservableList<RoomType> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public RoomTypeList() {
        this.setRoomTypes();
    }

    public void setRoomTypes() {
        internalList.setAll(RoomType.values());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<RoomType> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoomTypeList // instanceof handles nulls
                && internalList.equals(((RoomTypeList) other).internalList));
    }

    @Override
    public Iterator<RoomType> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
