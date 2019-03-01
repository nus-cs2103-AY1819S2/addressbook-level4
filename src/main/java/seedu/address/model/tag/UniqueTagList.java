package seedu.address.model.tag;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.model.person.Person;


/**
 * A list keeping track of all Tags, and for each tag, keeps track of every person that has the tag.
 */

public class UniqueTagList {
    private final ObservableMap<Tag, ObservableList<Person>> tagAndPersonList = FXCollections.observableHashMap();
    private final ObservableMap<Tag, ObservableList<Person>> unmodifiableTagAndPersonList =
            FXCollections.unmodifiableObservableMap(tagAndPersonList);

    /**
     * Adds person to the list of every tag that this person is tagged in
     */
    public void addPerson(Person toAdd) {
        Set<Tag> personTagList = toAdd.getTags();
        for (Tag i : personTagList) {
            if (tagAndPersonList.containsKey(i)) {
                tagAndPersonList.get(i).add(toAdd);
            } else {
                ObservableList<Person> newList = FXCollections.observableArrayList();
                newList.add(toAdd);
                tagAndPersonList.put(i, newList);
            }
        }
    }

    /**
     * Find person in every tag that contains the person and remove the person
     */
    public void removePerson(Person toRemove) {
        Set<Tag> personTagList = toRemove.getTags();
        for (Tag i : personTagList) {
            if (tagAndPersonList.containsKey(i)) {
                tagAndPersonList.get(i).remove(toRemove);
            } else {
                continue;
            }
        }
    }

    /**
     * Removes the entire tag from tag list
     */
    public void removeEntireTag(Tag tag) {
        tagAndPersonList.remove(tag);
    }


    public ObservableList<Person> getListOfPerson(Tag tag) {
        return tagAndPersonList.get(tag);
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableMap<Tag, ObservableList<Person>> asUnmodifiableObservableMap() {
        return unmodifiableTagAndPersonList;
    }
}


