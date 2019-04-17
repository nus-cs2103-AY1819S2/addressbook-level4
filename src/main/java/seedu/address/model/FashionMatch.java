package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.UniqueApparelList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameApparel comparison)
 */
public class FashionMatch implements ReadOnlyFashionMatch {

    private final UniqueApparelList apparels;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        apparels = new UniqueApparelList();
    }

    public FashionMatch() {}

    /**
     * Creates an FashionMatch using the Persons in the {@code toBeCopied}
     */
    public FashionMatch(ReadOnlyFashionMatch toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the apparel list with {@code apparels}.
     * {@code apparels} must not contain duplicate apparels.
     */
    public void setApparels(List<Apparel> apparels) {
        this.apparels.setApparels(apparels);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code FashionMatch} with {@code newData}.
     */
    public void resetData(ReadOnlyFashionMatch newData) {
        requireNonNull(newData);

        setApparels(newData.getApparelList());
    }

    //// apparel-level operations

    /**
     * Returns true if a apparel with the same identity as {@code apparel} exists in the address book.
     */
    public boolean hasApparel(Apparel apparel) {
        requireNonNull(apparel);
        return apparels.contains(apparel);
    }

    /**
     * Adds a apparel to the address book.
     * The apparel must not already exist in the address book.
     */
    public void addApparel(Apparel p) {
        apparels.add(p);
        indicateModified();
    }

    /**
     * Replaces the given apparel {@code target} in the list with {@code editedApparel}.
     * {@code target} must exist in the address book.
     * The apparel identity of {@code editedApparel} must not be
     * the same as another existing apparel in the address book.
     */
    public void setApparel(Apparel target, Apparel editedApparel) {
        requireNonNull(editedApparel);

        apparels.setApparel(target, editedApparel);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code FashionMatch}.
     * {@code key} must exist in the address book.
     */
    public void removeApparel(Apparel key) {
        apparels.remove(key);
        indicateModified();
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
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return apparels.asUnmodifiableObservableList().size() + " apparels";
        // TODO: refine later
    }

    @Override
    public ObservableList<Apparel> getApparelList() {
        return apparels.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FashionMatch // instanceof handles nulls
                && apparels.equals(((FashionMatch) other).apparels));
    }

    @Override
    public int hashCode() {
        return apparels.hashCode();
    }
}
