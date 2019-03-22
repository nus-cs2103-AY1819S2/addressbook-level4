package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.ConditionTag;
import seedu.address.model.tag.Conditions;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    // TODO: Fix implementation for HealthHub in MainApp for loading AddressBook class
    public static Person[] getSamplePersons() {
        return new Person[] {};
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) { // not sure if this should be Patient or Person
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) { //TODO: waiting for conditions set
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns Conditions from a set of tags passed in.
     */
    public static Conditions getConditionsFromTagSet(Set<Tag> tags) {
        HashSet<ConditionTag> set = new HashSet<>();
        tags.forEach(tag -> {
            set.add(new ConditionTag(tag.tagName));
        });
        return new Conditions(set);
    }

}
