package seedu.address.model.moduletaken;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.SemLimit;

/**
 * A list of size 10 containing the semester limits set by the user from Y1S1 to Y5S2
 *
 * Supports a minimal set of list operations.
 */
public class SemesterLimitList implements Iterable<SemLimit> {

    private final ObservableList<SemLimit> internalList = FXCollections.observableArrayList();
    private final ObservableList<SemLimit> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Replaces the original semLimit in the list with {@code editedSemLimit}.
     */
    public void setSemesterLimit(int index, SemLimit editedSemesterLimit) {
        requireAllNonNull(index, editedSemesterLimit);

        internalList.set(index, editedSemesterLimit);
    }

    public void setSemesterLimits(SemesterLimitList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code semLimit}.
     */
    public void setSemesterLimits(List<SemLimit> semesterLimits) {
        requireAllNonNull(semesterLimits);
        internalList.setAll(semesterLimits);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<SemLimit> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<SemLimit> iterator() {
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
