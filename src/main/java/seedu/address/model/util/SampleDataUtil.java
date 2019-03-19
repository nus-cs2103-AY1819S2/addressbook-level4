package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Specialisation;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code docX} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Doctor[] getSampleDoctors() {
        return new Doctor[] {
            new Doctor(new Name("Alex Yeoh"), new Phone("87438807"), new Gender("m"), new Age("21"),
                    getSpecSet("acupuncture")),
            new Doctor(new Name("Bernice Yu"), new Phone("99272758"), new Gender("F"), new Age("32"),
                    getSpecSet("acupuncture, general")),
            new Doctor(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Gender("f"), new Age("41"),
                    getSpecSet("massage")),
            new Doctor(new Name("David Li"), new Phone("91031282"), new Gender("M"), new Age("43"),
                    getSpecSet("massage, acupuncture")),
            new Doctor(new Name("Ivan Teo"), new Phone("92492021"), new Gender("M"), new Age("37"),
                    getSpecSet("acupuncture")),
            new Doctor(new Name("Roy Balakrishnan"), new Phone("92624417"), new Gender("m"), new Age("28"),
                    getSpecSet("general"))
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

    public static Set<Specialisation> getSpecSet(String... strings) {
        return Arrays.stream(strings)
                .map(Specialisation::new)
                .collect(Collectors.toSet());
    }

}
