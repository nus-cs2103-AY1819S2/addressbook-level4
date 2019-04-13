package seedu.hms.model.reservation.roomType;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.hms.model.reservation.roomType.exceptions.DuplicateRoomTypeException;
import seedu.hms.model.reservation.roomType.exceptions.RoomTypeNotFoundException;

/**
 * A list of RoomTypes
 * <p>
 * Supports a minimal set of list operations.
 */
public class RoomTypeList implements Iterable<RoomType> {

    private final ObservableList<RoomType> internalList = FXCollections.observableArrayList();
    private final ObservableList<RoomType> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent customer as the given argument.
     */
    public boolean contains(RoomType toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a RoomType to the list.
     */
    public void add(RoomType toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRoomTypeException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the RoomType {@code target} in the list with {@code editedRoomType}.
     * {@code target} must exist in the list.
     * The RoomType identity of {@code editedRoomType} must not be the same as another existing RoomType in the list.
     */
    public void setRoomType(int roomTypeIndex, RoomType editedRoomType) {
        requireNonNull(editedRoomType);
        if (roomTypeIndex < 0 || roomTypeIndex > internalList.size()) {
            throw new RoomTypeNotFoundException();
        }
        internalList.set(roomTypeIndex, editedRoomType);
    }

    public void setRoomTypes(RoomTypeList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Bookings}.
     * {@code Bookings} must not contain duplicate Bookings.
     */
    public void setRoomTypes(List<RoomType> roomTypes) {
        requireAllNonNull(roomTypes);
        internalList.setAll(roomTypes);
    }

    /**
     * Removes the equivalent RoomType from the list.
     * The RoomType must exist in the list.
     */
    public void remove(int toRemove) {
        requireNonNull(toRemove);
        if (toRemove < 0 || toRemove > internalList.size()) {
            throw new RoomTypeNotFoundException();
        }
        internalList.remove(toRemove);
    }

    /**
     * Removes the equivalent RoomType from the list.
     * The RoomType must exist in the list.
     */
    public void remove(RoomType toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RoomTypeNotFoundException();
        }
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
