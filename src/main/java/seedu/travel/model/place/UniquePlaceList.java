package seedu.travel.model.place;

import static java.util.Objects.requireNonNull;
import static seedu.travel.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.travel.model.place.exceptions.DuplicatePlaceException;
import seedu.travel.model.place.exceptions.PlaceNotFoundException;

/**
 * A list of places that enforces uniqueness between its elements and does not allow nulls.
 * A place is considered unique by comparing using {@code Place#isSamePlace(Place)}. As such, adding and updating of
 * places uses Place#isSamePlace(Place) for equality so as to ensure that the place being added or updated is
 * unique in terms of identity in the UniquePlaceList. However, the removal of a place uses Place#equals(Object) so
 * as to ensure that the place with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Place#isSamePlace(Place)
 */
public class UniquePlaceList implements Iterable<Place> {

    private final ObservableList<Place> internalList = FXCollections.observableArrayList();
    private final ObservableList<Place> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent place as the given argument.
     */
    public boolean contains(Place toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePlace);
    }

    /**
     * Adds a place to the list.
     * The place must not already exist in the list.
     */
    public void add(Place toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePlaceException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the place {@code target} in the list with {@code editedPlace}.
     * {@code target} must exist in the list.
     * The place identity of {@code editedPlace} must not be the same as another existing place in the list.
     */
    public void setPlace(Place target, Place editedPlace) {
        requireAllNonNull(target, editedPlace);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PlaceNotFoundException();
        }

        if (!target.isSamePlace(editedPlace) && contains(editedPlace)) {
            throw new DuplicatePlaceException();
        }

        internalList.set(index, editedPlace);
    }

    /**
     * Removes the equivalent place from the list.
     * The place must exist in the list.
     */
    public void remove(Place toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PlaceNotFoundException();
        }
    }

    public void setPlaces(UniquePlaceList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code places}.
     * {@code places} must not contain duplicate places.
     */
    public void setPlaces(List<Place> places) {
        requireAllNonNull(places);
        if (!placesAreUnique(places)) {
            throw new DuplicatePlaceException();
        }

        internalList.setAll(places);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Place> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Place> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePlaceList // instanceof handles nulls
                        && internalList.equals(((UniquePlaceList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code places} contains only unique places.
     */
    private boolean placesAreUnique(List<Place> places) {
        for (int i = 0; i < places.size() - 1; i++) {
            for (int j = i + 1; j < places.size(); j++) {
                if (places.get(i).isSamePlace(places.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
