package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Major;
import seedu.address.model.person.MatricNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.YearOfStudy;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new MatricNumber("A0111111B"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Gender("Male"), new YearOfStudy("Year 1"), new Major("Chemistry"),
                    getTagSet("swimming")),
            new Person(new Name("Bernice Yu"), new MatricNumber("A0222222C"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Gender("Female"), new YearOfStudy("Year 2"), new Major("Computer Science"),
                    getTagSet("running")),
            new Person(new Name("Charlotte Oliveiro"), new MatricNumber("A0333333D"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Gender("Female"), new YearOfStudy("Year 3"), new Major("Mathematics"),
                    getTagSet("running")),
            new Person(new Name("David Li"), new MatricNumber("A0444444Z"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Gender("Male"), new YearOfStudy("Year 3"), new Major("Bioengineering"),
                    getTagSet("soccer")),
            new Person(new Name("Irfan Ibrahim"), new MatricNumber("A0555555K"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Gender("Male"), new YearOfStudy("Year 2"), new Major("Physics"),
                    getTagSet("basketball")),
            new Person(new Name("Roy Balakrishnan"), new MatricNumber("A0777777L"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"), new Gender("Male"),
                    new YearOfStudy("Year 2"), new Major("Physics"), getTagSet("colleagues"))
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
