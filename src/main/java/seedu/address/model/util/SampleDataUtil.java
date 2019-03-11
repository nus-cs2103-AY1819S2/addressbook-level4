package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Amount;
import seedu.address.model.person.Date;
import seedu.address.model.person.Description;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Description EMPTY_DESCRIPTION = new Description("");
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Weekly groceries purchase"), new Amount("100"), new Date("12/02/2018"),
                EMPTY_DESCRIPTION, getTagSet("Groceries")),
            new Person(new Name("H&M Clothes"), new Amount("100"), new Date("12/02/2018"),
                EMPTY_DESCRIPTION, getTagSet("Shopping")),
            new Person(new Name("Chicken Rice lunch"), new Amount("100"), new Date("12/02/2018"),
                EMPTY_DESCRIPTION, getTagSet("Food")),
            new Person(new Name("Haircut"), new Amount("100"), new Date("12/02/2018"),
                EMPTY_DESCRIPTION, getTagSet("Misc")),
            new Person(new Name("Bus Ride"), new Amount("100"), new Date("12/02/2018"),
                EMPTY_DESCRIPTION, getTagSet("Transport")),
            new Person(new Name("Laundry"), new Amount("100"), new Date("12/02/2018"),
                EMPTY_DESCRIPTION, getTagSet("Misc"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
