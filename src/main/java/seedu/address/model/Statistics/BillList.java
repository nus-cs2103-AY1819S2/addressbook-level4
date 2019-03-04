package seedu.address.model.Statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of bills
 *
 * Supports a minimal set of list operations.
 */
public class BillList implements Iterable<Bill>{

    private final ObservableList<Bill> internalList = FXCollections.observableArrayList();
    private final ObservableList<Bill> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    
    /**  
     * Adds a bill to the list.
     */
    public void add(Bill toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the contents of this list with {@code billList}.
     */
    public void setBillList(List<Bill> billList) {
        requireAllNonNull(billList);
        internalList.setAll(billList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Bill> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Bill> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
