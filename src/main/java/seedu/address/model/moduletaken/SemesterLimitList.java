package seedu.address.model.moduletaken;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.limits.SemesterLimit;

/**
 * A list of size 10 containing the semester limits set by the user from Y1S1 to Y5S2
 *
 * Supports a minimal set of list operations.
 */
public class SemesterLimitList implements Iterable<SemesterLimit> {

    private final ObservableList<SemesterLimit> internalList = FXCollections.observableArrayList();
    private final ObservableList<SemesterLimit> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a SemesterLimit to the list.
     */
    public void add(SemesterLimit toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the original semLimit in the list with {@code editedSemLimit}.
     *
     * @param index the index of the semester to set the limit.
     * @param editedSemesterLimit the full SemesterLimit to replace the original semLimits of the given semester
     */
    public void setSemesterLimit(int index, SemesterLimit editedSemesterLimit) {
        requireAllNonNull(index, editedSemesterLimit);

        internalList.set(index, editedSemesterLimit);
    }

    public void setSemesterLimits(SemesterLimitList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code semLimit}.
     *
     * @param semesterLimits the list of SemLimits to replace with
     */
    public void setSemesterLimits(List<SemesterLimit> semesterLimits) {
        requireAllNonNull(semesterLimits);
        internalList.setAll(semesterLimits);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<SemesterLimit> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<SemesterLimit> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SemesterLimitList // instanceof handles nulls
                        && internalList.equals(((SemesterLimitList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
