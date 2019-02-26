package seedu.address.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Javadoc comment
 */
public class UniqueCardFolderList {

    private final ObservableList<CardFolder> internalList = FXCollections.observableArrayList();
    private final ObservableList<CardFolder> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Javadoc comment
     * @param toCheck
     * @return
     */
    public boolean contains(CardFolder toCheck) {
        requireNonNull(toCheck);
        // TODO: Currently checking based on folder cards, should it be based on folder name?
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Javadoc comment
     * @param toAdd
     */
    public void add(CardFolder toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCardFolderException();
        }
    }
}
