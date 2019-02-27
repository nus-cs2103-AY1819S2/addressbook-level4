package seedu.address.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of folders that enforces uniqueness between its elements. To be completed.
 */
public class UniqueCardFolderList {

    private final ObservableList<CardFolder> internalList = FXCollections.observableArrayList();
    private final ObservableList<CardFolder> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent folder as the given argument.
     */
    public boolean contains(CardFolder toCheck) {
        requireNonNull(toCheck);
        // TODO: Currently checking based on folder cards, should it be based on folder name?
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a folder to the list.
     * The folder must not already exist in the list.
     */
    public void add(CardFolder toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCardFolderException();
        }
    }
}
