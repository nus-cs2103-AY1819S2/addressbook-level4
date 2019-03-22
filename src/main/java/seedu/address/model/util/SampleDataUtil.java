package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Condition;

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
     * Returns a Condition set containing the list of strings given.
     */
    public static Set<Condition> getConditionSet(String... strings) {
        return Arrays.stream(strings)
                .map(Condition::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns Conditions from a set of conditions passed in.
     */
    public static Set<Condition> getConditionsFromConditionSet(Set<Condition> conditions) {
        HashSet<Condition> set = new HashSet<>();
        conditions.forEach(condition -> {
            set.add(condition);
        });
        return set;
    }

}
