package seedu.finance.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.finance.model.record.exceptions.DuplicateRecordException;
import seedu.finance.model.record.exceptions.RecordNotFoundException;

/**
 * A list of records that enforces uniqueness between its elements and does not allow nulls.
 * A record is considered unique by comparing using {@code Record#isSameRecord(Record)}. As such, adding and updating of
 * records uses Record#isSameRecord(Record) for equality so as to ensure that the record being added or updated is
 * unique in terms of identity in the UniqueRecordList. However, the removal of a record uses Record#equals(Object) so
 * as to ensure that the record with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Record#isSameRecord(Record)
 */
public class UniqueRecordList implements Iterable<Record> {

    private final ObservableList<Record> internalList = FXCollections.observableArrayList();
    private final ObservableList<Record> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent record as the given argument.
     */
    public boolean contains(Record toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRecord);
    }

    /**
     * Adds a record to the list.
     * The record must not already exist in the list.
     */
    public void add(Record toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRecordException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the record {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in the list.
     * The record identity of {@code editedRecord} must not be the same as another existing record in the list.
     */
    public void setRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RecordNotFoundException();
        }

        if (!target.isSameRecord(editedRecord) && contains(editedRecord)) {
            throw new DuplicateRecordException();
        }

        internalList.set(index, editedRecord);
    }

    /**
     * Removes the equivalent record from the list.
     * The record must exist in the list.
     */
    public void remove(Record toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RecordNotFoundException();
        }
    }

    public void setRecords(UniqueRecordList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code people}.
     * {@code people} must not contain duplicate people.
     */
    public void setRecords(List<Record> people) {
        requireAllNonNull(people);
        if (!recordsAreUnique(people)) {
            throw new DuplicateRecordException();
        }

        internalList.setAll(people);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Record> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Reverses the list.
     */
    public void reverseList() {
        FXCollections.reverse(internalList);

    }

    /**
     * Sorts the list according to comparator supplied
     */
    public void sortList(Comparator<Record> comparator) {
        FXCollections.sort(internalList, comparator);
    }

    @Override
    public Iterator<Record> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRecordList // instanceof handles nulls
                        && internalList.equals(((UniqueRecordList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code people} contains only unique people.
     */
    private boolean recordsAreUnique(List<Record> people) {
        for (int i = 0; i < people.size() - 1; i++) {
            for (int j = i + 1; j < people.size(); j++) {
                if (people.get(i).isSameRecord(people.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
